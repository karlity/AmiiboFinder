package com.github.karlity.amiibofinder.ui.amiibolist

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.ui.shared.LoadingState

data class AmiiboListState(
    val loadingState: LoadingState = LoadingState.IDLE,
    val characterName: String? = null,
    val gameSeriesName: String? = null,
    val typeId: String? = null,
    val amiiboList: AmiiboList? = null,
)
