package com.github.karlity.amiibofinder.amiibolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.core.AmiiboErrors
import com.github.karlity.amiibofinder.core.rules.MainDispatcherRule
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByCharacterName
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByGameSeriesName
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByTypeId
import com.github.karlity.amiibofinder.mocks.exampleAmiiboList
import com.github.karlity.amiibofinder.ui.amiibolist.AmiiboListViewModel
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
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

@Suppress("ktlint:standard:max-line-length")
@ExperimentalCoroutinesApi
class AmiiboListViewModelTest : AutoCloseKoinTest(), KoinTest {
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
                    single<SavedStateHandle> { spyk() }
                    viewModel<AmiiboListViewModel> { AmiiboListViewModel(get(), get(), get(), get()) }
                    single<GetAmiibosByCharacterName> { mockk() }
                    single<GetAmiibosByGameSeriesName> { mockk() }
                    single<GetAmiibosByTypeId> { mockk() }
                },
            )
        }
    }

    @Test
    fun `Given a successful fetchAmiibos by typeId, then loadingState should be IDLE and amiiboList should not be null`() {
        val viewModel: AmiiboListViewModel = get<AmiiboListViewModel>()
        val typeId = "0x02"
        val mockAmiiboList = exampleAmiiboList
        val getAmiibosByTypeId = get<GetAmiibosByTypeId>()

        // Mock the result of getAmiibosByTypeId
        coEvery { getAmiibosByTypeId.invoke(typeId) } returns Result.success(mockAmiiboList)

        runTest {
            // Launch the viewModel.fetchAmiibos coroutine
            viewModel.viewModelScope.launch {
                viewModel.fetchAmiibos(typeId, null, null)

                advanceUntilIdle()
                // Use collect to observe the uiState flow
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNotNull(it.amiiboList)
                }
            }
        }
    }

    @Test
    fun `Given a successful fetchAmiibos by characterName, then loadingState should be IDLE and amiiboList should not be null`() =
        runTest {
            val viewModel = get<AmiiboListViewModel>()
            val characterName = "Mario"
            val mockAmiiboList = exampleAmiiboList
            val getAmiibosByCharacterName: GetAmiibosByCharacterName = get()

            coEvery { getAmiibosByCharacterName.invoke(characterName) } returns
                Result.success(
                    mockAmiiboList,
                )

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiibos(null, characterName, null)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNotNull(it.amiiboList)
                }
            }
        }

    @Test
    fun `Given a successful fetchAmiibos by gameSeriesName, then loadingState should be IDLE and amiiboList should not be null`() =
        runTest {
            val viewModel = get<AmiiboListViewModel>()
            val gameSeriesName = "Super Mario"
            val mockAmiiboList = exampleAmiiboList
            val getAmiibosByGameSeriesName: GetAmiibosByGameSeriesName = get()

            coEvery { getAmiibosByGameSeriesName.invoke(gameSeriesName) } returns
                Result.success(
                    mockAmiiboList,
                )

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiibos(null, null, gameSeriesName)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNotNull(it.amiiboList)
                    assertEquals(it.amiiboList, exampleAmiiboList)
                }
            }
        }

    @Test
    fun `Given incorrect values for fetchAmiibos, then loadingState should be ERROR`() =
        runTest {
            val viewModel = get<AmiiboListViewModel>()

            viewModel.viewModelScope.launch {
                viewModel.fetchAmiibos(null, null, null)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.ERROR, it.loadingState)
                }
            }
        }

    @Test
    fun `Given a NoInternet exception, then loadingState should be NO_INTERNET`() =
        runTest {
            val viewModel = get<AmiiboListViewModel>()
            val characterName = "Mario"
            val getAmiibosByCharacterName: GetAmiibosByCharacterName = get()

            coEvery { getAmiibosByCharacterName.invoke(characterName) } returns
                Result.failure(
                    AmiiboErrors.NoInternet,
                )
            viewModel.viewModelScope.launch {
                viewModel.fetchAmiibos(null, characterName, null)
                advanceUntilIdle()

                viewModel.uiState.collect {
                    assertEquals(LoadingState.NO_INTERNET, it.loadingState)
                }
            }
        }

    @Test
    fun `Given a successful fetchAmiibos followed by dismissError, then loadingState should be IDLE`() =
        runTest {
            val viewModel = get<AmiiboListViewModel>()
            val characterName = "Mario"
            val mockAmiiboList = exampleAmiiboList
            val getAmiibosByCharacterName: GetAmiibosByCharacterName = get()

            coEvery { getAmiibosByCharacterName.invoke(characterName) } returns
                Result.success(
                    mockAmiiboList,
                )
            viewModel.viewModelScope.launch {
                viewModel.fetchAmiibos(null, characterName, null)
                advanceUntilIdle()

                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                }
            }
            viewModel.viewModelScope.launch {
                viewModel.dismissError()
                advanceUntilIdle()

                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                }
            }
        }
}
