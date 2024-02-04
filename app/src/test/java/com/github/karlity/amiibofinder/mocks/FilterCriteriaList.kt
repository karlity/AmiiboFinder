package com.github.karlity.amiibofinder.mocks

import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponse
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList

val filterCriteriaList =
    FilterCriteriaResponseList(
        amiibo =
            listOf(
                FilterCriteriaResponse(key = "1", name = "Super Mario"),
                FilterCriteriaResponse(key = "2", name = "The Legend of Zelda"),
                FilterCriteriaResponse(key = "3", name = "Pokémon"),
            ),
    )
