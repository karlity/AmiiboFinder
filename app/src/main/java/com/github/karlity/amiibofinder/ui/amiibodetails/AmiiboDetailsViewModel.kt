package com.github.karlity.amiibofinder.ui.amiibodetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.domain.interactor.GetAmiiboByAmiiboId
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AmiiboDetailsViewModel(private val getAmiiboByAmiiboId: GetAmiiboByAmiiboId) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboDetailsState())
    val uiState: StateFlow<AmiiboDetailsState> =
        _uiState.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value,
        )

    suspend fun fetchAmiiboDetails(amiiboId: String) {
        _uiState.update {
            it.copy(loadingState = LoadingState.LOADING)
        }
        getAmiiboByAmiiboId.invoke(amiiboId).onSuccess { amiibo ->
            _uiState.update {
                it.copy(amiibo = amiibo.amiibo, loadingState = LoadingState.IDLE)
            }
        }.onFailure {
            Log.wtf("wtf", "wtf ok $it")
        }
    }
}
