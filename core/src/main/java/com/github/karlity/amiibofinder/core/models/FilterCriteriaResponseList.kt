package com.github.karlity.amiibofinder.core.models

/**
 * This class serves as the response body for both Characters and Game Series
 * The API gives them to us in the same format
 *
 */
data class FilterCriteriaResponseList(
    val amiibo: List<FilterCriteriaResponse>,
)
