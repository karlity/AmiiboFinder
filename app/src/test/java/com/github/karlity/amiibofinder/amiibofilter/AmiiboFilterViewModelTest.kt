package com.github.karlity.amiibofinder.amiibofilter

import AmiiboErrors
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.core.rules.MainDispatcherRule
import com.github.karlity.amiibofinder.domain.interactor.GetCharacterList
import com.github.karlity.amiibofinder.domain.interactor.GetGameSeriesList
import com.github.karlity.amiibofinder.mocks.filterCriteriaList
import com.github.karlity.amiibofinder.ui.amiibofilter.AmiiboFilterCritera
import com.github.karlity.amiibofinder.ui.amiibofilter.AmiiboFilterViewModel
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
import kotlin.test.assertNull

@Suppress("ktlint:standard:max-line-length")
@ExperimentalCoroutinesApi
class AmiiboFilterViewModelTest : AutoCloseKoinTest(), KoinTest {
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
                    viewModel<AmiiboFilterViewModel> { AmiiboFilterViewModel(get(), get()) }
                    single<GetGameSeriesList> { mockk() }
                    single<GetCharacterList> { mockk() }
                },
            )
        }
    }

    @Test
    fun `Given a successful setFilterCriteria with AmiiboFilterCritera GAMES, then loadingState should be IDLE and gameSeriesList should not be null`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()
            val getGameSeriesList: GetGameSeriesList = get()
            val mockGameSeriesList = filterCriteriaList

            coEvery { getGameSeriesList() } returns Result.success(mockGameSeriesList)

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.GAME)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNotNull(it.gameSeriesList)
                }
            }
        }

    @Test
    fun `Given a successful setFilterCriteria with AmiiboFilterCritera CHARACTERS, then loadingState should be IDLE and characterList should not be null`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()
            val getCharacterList: GetCharacterList = get()
            val mockCharacterList = filterCriteriaList

            coEvery { getCharacterList() } returns Result.success(mockCharacterList)

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.CHARACTER)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNotNull(it.characterList)
                }
            }
        }

    @Test
    fun `Given a successful setFilterCriteria with AmiiboFilterCritera TYPE, then loadingState should be IDLE and filterCriteria should be TYPE`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.TYPE)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertEquals(AmiiboFilterCritera.TYPE, it.filterCriteria)
                }
            }
        }

    @Test
    fun `Given a NoInternet exception when fetching game series list, then loadingState should be NO_INTERNET`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()
            val getGameSeriesList: GetGameSeriesList = get()

            coEvery { getGameSeriesList() } returns Result.failure(AmiiboErrors.NoInternet)

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.GAME)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.NO_INTERNET, it.loadingState)
                }
            }
        }

    @Test
    fun `Given a successful setFilterCriteria followed by resetFilter, then loadingState should be IDLE and filter criteria should be null`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()
            val getCharacterList: GetCharacterList = get()

            coEvery { getCharacterList() } returns Result.success(filterCriteriaList)

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.CHARACTER)
                advanceUntilIdle()

                assertEquals(LoadingState.IDLE, viewModel.uiState.first().loadingState)
                assertEquals(
                    AmiiboFilterCritera.CHARACTER,
                    viewModel.uiState.first().filterCriteria,
                )

                viewModel.resetFilter()
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                    assertNull(it.filterCriteria)
                }
            }
        }

    @Test
    fun `Given a successful setFilterCriteria followed by dismissError, then loadingState should be IDLE`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()
            val getCharacterList: GetCharacterList = get()
            coEvery { getCharacterList() } returns Result.failure(AmiiboErrors.ServerError("oops"))

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.CHARACTER)
                advanceUntilIdle()

                assertEquals(LoadingState.ERROR, viewModel.uiState.first().loadingState)

                viewModel.dismissError()
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.IDLE, it.loadingState)
                }
            }
        }

    @Test
    fun `Given an empty response when fetching filtered response, then loadingState should be EMPTY`() =
        runTest {
            val viewModel = get<AmiiboFilterViewModel>()
            val getCharacterList: GetCharacterList = get()
            coEvery { getCharacterList() } returns Result.failure(AmiiboErrors.NoResults)

            viewModel.viewModelScope.launch {
                viewModel.setFilterCriteria(AmiiboFilterCritera.CHARACTER)
                advanceUntilIdle()
                viewModel.uiState.collect {
                    assertEquals(LoadingState.EMPTY, it.loadingState)
                }
            }
        }
}
