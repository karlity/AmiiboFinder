package com.github.karlity.amiibofinder.ui.amiibodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.core.AmiiboErrors
import com.github.karlity.amiibofinder.domain.interactor.GetAmiiboByAmiiboId
import com.github.karlity.amiibofinder.navigation.amiiboIdKey
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class AmiiboDetailsViewModel(
    handle: SavedStateHandle,
    private val getAmiiboByAmiiboId: GetAmiiboByAmiiboId,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboDetailsState())
    val uiState: StateFlow<AmiiboDetailsState> = _uiState

    init {
        val amiiboId = handle.get<String>(amiiboIdKey)
        if (amiiboId.isNullOrBlank()) {
            setError()
        } else {
            fetchAmiiboDetails(amiiboId)
        }
    }

    fun fetchAmiiboDetails(amiiboId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(loadingState = LoadingState.LOADING, amiiboId = amiiboId)
            }
            getAmiiboByAmiiboId.invoke(amiiboId).onSuccess { amiibo ->
                _uiState.update {
                    it.copy(amiibo = amiibo.amiibo, loadingState = LoadingState.IDLE)
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
                Timber.e("Error Amiibo Details: $it")
            }
        }
    }

    fun setError() {
        _uiState.update {
            it.copy(loadingState = LoadingState.ERROR)
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(loadingState = if (it.amiibo == null) LoadingState.EMPTY else LoadingState.IDLE)
        }
    }
}
