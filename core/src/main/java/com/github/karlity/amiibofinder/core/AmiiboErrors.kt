package com.github.karlity.amiibofinder.core

sealed class AmiiboErrors : Throwable() {
    data object NoResults : AmiiboErrors()

    data object NoInternet : AmiiboErrors()

    data class ServerError(val errorMessage: String) : AmiiboErrors()
}
