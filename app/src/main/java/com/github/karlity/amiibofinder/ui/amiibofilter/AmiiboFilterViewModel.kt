package com.github.karlity.amiibofinder.ui.amiibofilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.domain.interactor.GetCharacterList
import com.github.karlity.amiibofinder.domain.interactor.GetGameSeriesList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AmiiboFilterViewModel(
    private val getGameSeriesList: GetGameSeriesList,
    private val getCharacterList: GetCharacterList,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboFilterState())
    val uiState: StateFlow<AmiiboFilterState> =
        _uiState.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value,
        )

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
        }
    }

    private suspend fun getTypeList() {
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
}
