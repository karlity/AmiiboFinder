package com.github.karlity.amiibofinder.data.repository

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.AmiiboSingle
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.data.remote.AmiiboService
import com.github.karlity.amiibofinder.domain.repository.AmiiboRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single(binds = [AmiiboRepository::class])
@Named(Qualifiers.AMIIBO_REPOSITORY)
class AmiiboRepositoryImpl(
    @Named(Qualifiers.AMIIBO_SERVICE) val amiiboService: AmiiboService,
) : AmiiboRepository {
    override suspend fun getAmiiboByAmiiboId(amiiboId: String): Result<AmiiboSingle> {
        return amiiboService.getAmiiboByAmiiboId(amiiboId)
    }

    override suspend fun getAmiibosByTypeId(typeId: String): Result<AmiiboList> {
        return amiiboService.getAmiibosByTypeId(typeId)
    }

    override suspend fun getAmiibosByGameSeriesName(gameSeriesName: String): Result<AmiiboList> {
        return amiiboService.getAmiibosByGameSeriesName(gameSeriesName)
    }

    override suspend fun getAmiibosByCharacterName(characterName: String): Result<AmiiboList> {
        return amiiboService.getAmiibosByCharacterName(characterName)
    }

    override suspend fun getGameSeriesList(): Result<FilterCriteriaResponseList> {
        return amiiboService.getGameSeriesList()
    }

    override suspend fun getCharacterList(): Result<FilterCriteriaResponseList> {
        return amiiboService.getCharacterList()
    }
}
