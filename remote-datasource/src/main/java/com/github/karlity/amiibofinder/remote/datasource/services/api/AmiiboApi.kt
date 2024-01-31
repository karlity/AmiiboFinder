package com.github.karlity.amiibofinder.remote.datasource.services.api

import com.github.karlity.amiibofinder.core.models.AmiiboList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AmiiboApi {
    @GET("/api/amiibo")
    suspend fun getAllAmiibos(): Response<AmiiboList>

    @GET("/api/amiibo")
    suspend fun getAmiibosByNameAndType(
        @Query("name") name: String?,
        @Query("type") type: String?,
    ): Response<AmiiboList>
}
