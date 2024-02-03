package com.github.karlity.amiibofinder.ui.amiibofilter

import com.github.karlity.amiibofinder.R
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.core.models.Type

data class AmiiboFilterState(
    val characterList: FilterCriteriaResponseList? = null,
    val gameSeriesList: FilterCriteriaResponseList? = null,
    val typeList: List<Type> = enumValues<Type>().toList(),
    val filterCriteriaResponseList: FilterCriteriaResponseList? = null,
    val filterCriteria: AmiiboFilterCritera? = null,
    val loadingState: LoadingState = LoadingState.IDLE,
)

enum class AmiiboFilterCritera(val stringRes: Int) {
    CHARACTER(R.string.character),
    GAME(R.string.game_series),
    TYPE(R.string.type),
}

enum class LoadingState {
    IDLE,
    LOADING,
    ERROR,
    FINISHED,
}
