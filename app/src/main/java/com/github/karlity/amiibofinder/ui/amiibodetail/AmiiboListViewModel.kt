package com.github.karlity.amiibofinder.ui.amiibodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AmiiboListViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(AmiiboListState())
    val uiState: StateFlow<AmiiboListState> =
        _uiState.stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            initialValue = _uiState.value,
        )
}
