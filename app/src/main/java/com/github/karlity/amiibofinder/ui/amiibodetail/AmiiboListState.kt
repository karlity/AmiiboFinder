package com.github.karlity.amiibofinder.ui.amiibodetail

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.ui.amiibofilter.LoadingState

data class AmiiboListState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val amiiboList: AmiiboList? = null,
)
