package com.github.karlity.amiibofinder.remote.datasource.services

import com.github.karlity.amiibofinder.core.AmiiboErrors
import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.AmiiboSingle
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.data.remote.AmiiboService
import com.github.karlity.amiibofinder.remote.datasource.services.api.AmiiboApi
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import retrofit2.Response
import java.io.IOException

@Single(binds = [AmiiboService::class])
@Named(Qualifiers.AMIIBO_SERVICE)
class AmiiboServiceImpl(private val amiiboApi: AmiiboApi) : AmiiboService {
    override suspend fun getAmiiboByAmiiboId(amiiboId: String): Result<AmiiboSingle> = amiiboApi.getAmiiboByAmiiboId(amiiboId).toResult()

    override suspend fun getAmiibosByTypeId(typeId: String): Result<AmiiboList> = amiiboApi.getAmiibosByTypeId(typeId).toResult()

    override suspend fun getAmiibosByGameSeriesName(gameSeriesName: String): Result<AmiiboList> =
        amiiboApi.getAmiibosByGameSeriesName(gameSeriesName).toResult()

    override suspend fun getAmiibosByCharacterName(characterName: String): Result<AmiiboList> =
        amiiboApi.getAmiibosByCharacterName(characterName).toResult()

    override suspend fun getGameSeriesList(): Result<FilterCriteriaResponseList> = amiiboApi.getAmiiboGameSeriesList().toResult()

    override suspend fun getCharacterList(): Result<FilterCriteriaResponseList> = amiiboApi.getAmiiboCharacterList().toResult()

    private fun <T> Response<T>.toResult(): Result<T> {
        return runCatching {
            val data = this.body()
            return if (data == null) {
                Result.failure(AmiiboErrors.NoResults)
            } else {
                Result.success(data)
            }
        }.onFailure {
            return when (it) {
                is IOException -> Result.failure(AmiiboErrors.NoInternet)
                else -> Result.failure(AmiiboErrors.ServerError("Server Error: ${it.message}"))
            }
        }
    }
}
