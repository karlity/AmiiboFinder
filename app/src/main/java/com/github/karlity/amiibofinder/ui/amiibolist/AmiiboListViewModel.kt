package com.github.karlity.amiibofinder.ui.amiibolist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.karlity.amiibofinder.core.AmiiboErrors
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByCharacterName
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByGameSeriesName
import com.github.karlity.amiibofinder.domain.interactor.GetAmiibosByTypeId
import com.github.karlity.amiibofinder.navigation.characterNameKey
import com.github.karlity.amiibofinder.navigation.gameSeriesNameKey
import com.github.karlity.amiibofinder.navigation.typeIdKey
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class AmiiboListViewModel(
    handle: SavedStateHandle,
    private val getAmiibosByCharacterName: GetAmiibosByCharacterName,
    private val getAmiibosByGameSeriesName: GetAmiibosByGameSeriesName,
    private val getAmiibosByTypeId: GetAmiibosByTypeId,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboListState())
    val uiState: StateFlow<AmiiboListState> = _uiState

    init {
        val characterName = handle.get<String>(characterNameKey)
        val gameSeriesName = handle.get<String>(gameSeriesNameKey)
        val typeId = handle.get<String>(typeIdKey)

        fetchAmiibos(typeId, characterName, gameSeriesName)
    }

    // The intention is that only one of these parameters gets passed through
    fun fetchAmiibos(
        typeId: String?,
        characterName: String?,
        gameSeriesName: String?,
    ) {
        _uiState.update {
            it.copy(
                loadingState = LoadingState.LOADING,
                characterName = characterName,
                gameSeriesName = gameSeriesName,
                typeId = typeId,
            )
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
                Timber.e("Error fetching Amiibos: $it")
            }
        }
    }

    fun dismissError() {
        _uiState.update {
            it.copy(loadingState = if (it.amiiboList == null) LoadingState.EMPTY else LoadingState.IDLE)
        }
    }
}
