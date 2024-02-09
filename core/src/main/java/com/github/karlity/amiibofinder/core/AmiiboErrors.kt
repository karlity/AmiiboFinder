package com.github.karlity.amiibofinder.core

sealed class AmiiboErrors : Throwable() {
    object NoResults : AmiiboErrors()

    object NoInternet : AmiiboErrors()

    data class ServerError(val errorMessage: String) : AmiiboErrors()
}
