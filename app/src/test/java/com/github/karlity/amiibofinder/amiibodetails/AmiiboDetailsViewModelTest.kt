package com.github.karlity.amiibofinder.amiibodetails

import AmiiboErrors
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.core.rules.MainDispatcherRule
import com.github.karlity.amiibofinder.domain.interactor.GetAmiiboByAmiiboId
import com.github.karlity.amiibofinder.mocks.exampleSingleAmiibo
import com.github.karlity.amiibofinder.ui.amiibodetails.AmiiboDetailsViewModel
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
class AmiiboDetailsViewModelTest : AutoCloseKoinTest(), KoinTest {
    @get:Rule
    val mockProvider =
        MockProviderRule.create { clazz ->
            mockk(relaxed = true)
        }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    viewModel<AmiiboDetailsViewModel> { AmiiboDetailsViewModel(get()) }
                    single<GetAmiiboByAmiiboId> { mockk() }
                },
            )
        }
    }

    @Test
    fun `Given a successful fetchAmiiboDetails, then loadingState should be IDLE and amiibo should not be null`() =
        runTest {
            val viewModel = get<AmiiboDetailsViewModel>()
            val amiiboId = "amiibo123"
            val mockAmiibo = exampleSingleAmiibo

            val getAmiiboByAmiiboId: GetAmiiboByAmiiboId = get()

            coEvery { getAmiiboByAmiiboId.invoke(amiiboId) } returns Result.success(mockAmiibo)

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiiboDetails(amiiboId)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNotNull(it.amiibo)
                }
            }
        }

    @Test
    fun `Given a NoInternet exception when fetching amiibo details, then loadingState should be NO_INTERNET`() =
        runTest {
            val viewModel = get<AmiiboDetailsViewModel>()
            val amiiboId = "amiibo123"
            val getAmiiboByAmiiboId: GetAmiiboByAmiiboId = get()

            coEvery { getAmiiboByAmiiboId.invoke(amiiboId) } returns Result.failure(AmiiboErrors.NoInternet)

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiiboDetails(amiiboId)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.NO_INTERNET, it.loadingState)
                }
            }
        }

    @Test
    fun `Given a generic failure when fetching amiibo details, then loadingState should be ERROR`() =
        runTest {
            val viewModel = get<AmiiboDetailsViewModel>()
            val amiiboId = "amiibo123"
            val getAmiiboByAmiiboId: GetAmiiboByAmiiboId = get()

            coEvery { getAmiiboByAmiiboId.invoke(amiiboId) } returns Result.failure(AmiiboErrors.ServerError("oops"))

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiiboDetails(amiiboId)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.ERROR, it.loadingState)
                }
            }
        }

    @Test
    fun `Given an empty response when fetching amiibo details, then loadingState should be EMPTY`() =
        runTest {
            val viewModel = get<AmiiboDetailsViewModel>()
            val amiiboId = "amiibo123"
            val getAmiiboByAmiiboId: GetAmiiboByAmiiboId = get()

            coEvery { getAmiiboByAmiiboId.invoke(amiiboId) } returns Result.failure(AmiiboErrors.NoResults)

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiiboDetails(amiiboId)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.EMPTY, it.loadingState)
                }
            }
        }

    @Test
    fun `Given a successful fetchAmiiboDetails followed by dismissError, then loadingState should be IDLE`() =
        runTest {
            val viewModel = get<AmiiboDetailsViewModel>()
            val amiiboId = "amiibo123"
            val mockAmiibo = exampleSingleAmiibo

            val getAmiiboByAmiiboId: GetAmiiboByAmiiboId = get()

            coEvery { getAmiiboByAmiiboId.invoke(amiiboId) } returns Result.success(mockAmiibo)

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiiboDetails(amiiboId)
                advanceUntilIdle()

                assertEquals(LoadingState.IDLE, viewModel.uiState.first().loadingState)

                viewModel.dismissError()
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                }
            }
        }
}
