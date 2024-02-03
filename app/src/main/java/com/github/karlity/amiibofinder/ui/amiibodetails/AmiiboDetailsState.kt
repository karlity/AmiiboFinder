package com.github.karlity.amiibofinder.ui.amiibodetails

import com.github.karlity.amiibofinder.core.models.Amiibo
import com.github.karlity.amiibofinder.ui.shared.LoadingState

data class AmiiboDetailsState(
    val amiibo: Amiibo? = null,
    val loadingState: LoadingState = LoadingState.IDLE,
)
