package com.github.karlity.amiibofinder.ui.amiibodetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.domain.interactor.GetAmiiboByAmiiboId
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AmiiboDetailsViewModel(private val getAmiiboByAmiiboId: GetAmiiboByAmiiboId) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboDetailsState())
    val uiState: StateFlow<AmiiboDetailsState> = _uiState

    fun fetchAmiiboDetails(amiiboId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loadingState = LoadingState.LOADING)
            }
            getAmiiboByAmiiboId.invoke(amiiboId).onSuccess { amiibo ->
                _uiState.update {
                    it.copy(amiibo = amiibo.amiibo, loadingState = LoadingState.IDLE)
                }
            }.onFailure {
                val errorState = if (it is AmiiboErrors.NoInternet) LoadingState.NO_INTERNET else LoadingState.ERROR
                _uiState.update { state ->
                    state.copy(loadingState = errorState)
                }
            }
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(loadingState = LoadingState.IDLE)
        }
    }
}
