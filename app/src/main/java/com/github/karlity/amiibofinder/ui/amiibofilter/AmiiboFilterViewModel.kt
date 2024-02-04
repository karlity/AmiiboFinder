package com.github.karlity.amiibofinder.ui.amiibofilter

import AmiiboErrors
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.domain.interactor.GetCharacterList
import com.github.karlity.amiibofinder.domain.interactor.GetGameSeriesList
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AmiiboFilterViewModel(
    private val getGameSeriesList: GetGameSeriesList,
    private val getCharacterList: GetCharacterList,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboFilterState())
    val uiState: StateFlow<AmiiboFilterState> = _uiState

    fun setFilterCriteria(filterCritera: AmiiboFilterCritera) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    loadingState = LoadingState.LOADING,
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
                    filterCriteria = AmiiboFilterCritera.CHARACTER,
                    characterList = characterList,
                    gameSeriesList = null,
                    loadingState = LoadingState.IDLE,
                )
            }
        }.onFailure {
            val errorState = if (it is AmiiboErrors.NoInternet) LoadingState.NO_INTERNET else LoadingState.ERROR
            _uiState.update {
                it.copy(loadingState = errorState)
            }
        }
    }

    private suspend fun fetchGameSeriesList() {
        getGameSeriesList().onSuccess { gameSeriesList ->
            _uiState.update {
                it.copy(
                    filterCriteria = AmiiboFilterCritera.GAME,
                    gameSeriesList = gameSeriesList,
                    characterList = null,
                    loadingState = LoadingState.IDLE,
                )
            }
        }.onFailure {
            val errorState = if (it is AmiiboErrors.NoInternet) LoadingState.NO_INTERNET else LoadingState.ERROR
            _uiState.update {
                it.copy(loadingState = errorState)
            }
        }
    }

    private fun getTypeList() {
        _uiState.update {
            it.copy(
                filterCriteria = AmiiboFilterCritera.TYPE,
                gameSeriesList = null,
                characterList = null,
                loadingState = LoadingState.IDLE,
            )
        }
    }

    fun resetFilter() {
        _uiState.update {
            it.copy(
                filterCriteria = null,
                gameSeriesList = null,
                characterList = null,
                loadingState = LoadingState.IDLE,
            )
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(loadingState = LoadingState.IDLE)
        }
    }
}
