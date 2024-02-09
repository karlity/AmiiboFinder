package com.github.karlity.amiibofinder.ui.amiibofilter

import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.Companion.PRIVATE
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.core.AmiiboErrors
import com.github.karlity.amiibofinder.domain.interactor.GetCharacterList
import com.github.karlity.amiibofinder.domain.interactor.GetGameSeriesList
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class AmiiboFilterViewModel(
    private val handle: SavedStateHandle,
    private val getGameSeriesList: GetGameSeriesList,
    private val getCharacterList: GetCharacterList,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboFilterState())
    val uiState: StateFlow<AmiiboFilterState> = _uiState

    fun setFilterCriteria(filterCritera: AmiiboFilterCritera) {
        setSavedFilterCriteria(filterCritera)

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loadingState = LoadingState.LOADING,
                    filterCriteria = getSavedFilterCriteria(),
                )
            }
            when (filterCritera) {
                AmiiboFilterCritera.GAME -> {
                    fetchGameSeriesList()
                }

                AmiiboFilterCritera.TYPE -> {
                    getTypeList()
                }

                AmiiboFilterCritera.CHARACTER -> {
                    fetchCharacterList()
                }
            }
        }
    }

    private suspend fun fetchCharacterList() {
        getCharacterList().onSuccess { characterList ->
            _uiState.update {
                it.copy(
                    characterList = characterList,
                    gameSeriesList = null,
                    loadingState = LoadingState.IDLE,
                )
            }
        }.onFailure {
            val errorState =
                when (it) {
                    is AmiiboErrors.NoInternet -> LoadingState.NO_INTERNET
                    is AmiiboErrors.ServerError -> LoadingState.ERROR
                    is AmiiboErrors.NoResults -> LoadingState.EMPTY
                    else -> {
                        LoadingState.ERROR
                    }
                }
            _uiState.update { state ->
                state.copy(loadingState = errorState)
            }
            Timber.e("Error fetching character list: $it")
        }
    }

    private suspend fun fetchGameSeriesList() {
        getGameSeriesList().onSuccess { gameSeriesList ->
            _uiState.update {
                it.copy(
                    gameSeriesList = gameSeriesList,
                    characterList = null,
                    loadingState = LoadingState.IDLE,
                )
            }
        }.onFailure {
            val errorState =
                when (it) {
                    is AmiiboErrors.NoInternet -> LoadingState.NO_INTERNET
                    is AmiiboErrors.ServerError -> LoadingState.ERROR
                    is AmiiboErrors.NoResults -> LoadingState.EMPTY
                    else -> {
                        LoadingState.ERROR
                    }
                }
            _uiState.update { state ->
                state.copy(loadingState = errorState)
            }
            Timber.e("Error fetching game series list: $it")
        }
    }

    private fun getTypeList() {
        _uiState.update {
            it.copy(
                gameSeriesList = null,
                characterList = null,
                loadingState = LoadingState.IDLE,
            )
        }
    }

    fun resetFilter() {
        setSavedFilterCriteria(null)
        _uiState.update {
            it.copy(
                filterCriteria = null,
                gameSeriesList = null,
                characterList = null,
                loadingState = LoadingState.IDLE,
            )
        }
    }

    private fun getSavedFilterCriteria(): AmiiboFilterCritera? = handle.get<AmiiboFilterCritera>(FILTER_CRITERIA)

    private fun setSavedFilterCriteria(filterCritera: AmiiboFilterCritera?) {
        handle.set(FILTER_CRITERIA, filterCritera)
    }

    companion object {
        const val FILTER_CRITERIA = "FILTER_CRITERIA"
    }
}
