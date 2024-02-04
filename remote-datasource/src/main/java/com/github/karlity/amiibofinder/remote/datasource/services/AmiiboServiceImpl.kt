package com.github.karlity.amiibofinder.remote.datasource.services

import AmiiboErrors
import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.AmiiboSingle
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.data.remote.AmiiboService
import com.github.karlity.amiibofinder.remote.datasource.services.api.AmiiboApi
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.io.IOException

@Single(binds = [AmiiboService::class])
@Named(Qualifiers.AMIIBO_SERVICE)
class AmiiboServiceImpl(private val amiiboApi: AmiiboApi) : AmiiboService {
    override suspend fun getAmiiboByAmiiboId(amiiboId: String): Result<AmiiboSingle> {
        return runCatching {
            val response = amiiboApi.getAmiiboByAmiiboId(amiiboId)
            val data = response.body()
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

    override suspend fun getAmiibosByTypeId(typeId: String): Result<AmiiboList> {
        return runCatching {
            val response = amiiboApi.getAmiibosByTypeId(typeId)
            val data = response.body()
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

    override suspend fun getAmiibosByGameSeriesName(gameSeriesName: String): Result<AmiiboList> {
        return runCatching {
            val response = amiiboApi.getAmiibosByGameSeriesName(gameSeriesName)
            val data = response.body()
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

    override suspend fun getAmiibosByCharacterName(characterName: String): Result<AmiiboList> {
        return runCatching {
            val response = amiiboApi.getAmiibosByCharacterName(characterName)
            val data = response.body()
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

    override suspend fun getGameSeriesList(): Result<FilterCriteriaResponseList> {
        return runCatching {
            val response = amiiboApi.getAmiiboGameSeriesList()
            val data = response.body()
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

    override suspend fun getCharacterList(): Result<FilterCriteriaResponseList> {
        return runCatching {
            val response = amiiboApi.getAmiiboCharacterList()
            val data = response.body()
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
