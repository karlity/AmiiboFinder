package com.github.karlity.amiibofinder.remote.datasource.services

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.Type
import com.github.karlity.amiibofinder.data.remote.AmiiboService
import com.github.karlity.amiibofinder.remote.datasource.services.api.AmiiboApi
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single(binds = [AmiiboService::class])
@Named(Qualifiers.AMIIBO_SERVICE)
class AmiiboServiceImpl(private val amiiboApi: AmiiboApi) : AmiiboService {
    override suspend fun getAllAmiibos(): Result<AmiiboList> {
        return runCatching {
            val response = amiiboApi.getAllAmiibos()
            val data = response.body()
            return if (data == null) {
                Result.failure(Throwable("null"))
            } else {
                Result.success(data)
            }
        }.onFailure {
            return Result.failure(it)
        }
    }

    override suspend fun getAmiibosByNameAndType(
        name: String?,
        type: Type?,
    ): Result<AmiiboList> {
        return runCatching {
            val response = amiiboApi.getAmiibosByNameAndType(name = name, type = type?.key)
            val data = response.body()
            return if (data == null) {
                Result.failure(Throwable("null"))
            } else {
                Result.success(data)
            }
        }.onFailure {
            return Result.failure(it)
        }
    }
}
