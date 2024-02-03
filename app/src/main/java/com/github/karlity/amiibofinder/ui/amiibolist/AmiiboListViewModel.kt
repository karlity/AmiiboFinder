package com.github.karlity.amiibofinder.ui.amiibolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByCharacterName
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByGameSeriesName
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByTypeId
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AmiiboListViewModel(
    private val getAmiibosByCharacterName: GetAmiibosByCharacterName,
    private val getAmiibosByGameSeriesName: GetAmiibosByGameSeriesName,
    private val getAmiibosByTypeId: GetAmiibosByTypeId,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboListState())
    val uiState: StateFlow<AmiiboListState> =
        _uiState.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value,
        )

    // The intention is that only one of these parameters gets passed through
    fun fetchAmiibos(
        typeId: String?,
        characterName: String?,
        gameSeriesName: String?,
    ) {
        _uiState.update {
            it.copy(loadingState = LoadingState.LOADING)
        }
        viewModelScope.launch {
            val result =
                when {
                    !typeId.isNullOrEmpty() -> {
                        getAmiibosByTypeId.invoke(typeId)
                    }
                    !characterName.isNullOrEmpty() -> {
                        getAmiibosByCharacterName.invoke(characterName)
                    }
                    !gameSeriesName.isNullOrEmpty() -> {
                        getAmiibosByGameSeriesName.invoke(gameSeriesName)
                    }
                    else -> {
                        Result.failure(Throwable("Incorrect values passed to AmiiboList"))
                    }
                }

            result.onSuccess { amiiboList ->
                _uiState.update {
                    it.copy(loadingState = LoadingState.IDLE, amiiboList = amiiboList)
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
