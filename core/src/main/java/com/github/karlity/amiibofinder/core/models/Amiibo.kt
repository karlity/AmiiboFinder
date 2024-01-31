package com.github.karlity.amiibofinder.core.models

data class Amiibo(
    val amiiboSeries: String,
    val character: String,
    val gameSeries: String,
    val head: String,
    val image: String,
    val name: String,
    val release: Release,
    val tail: String,
    val type: String,
)
