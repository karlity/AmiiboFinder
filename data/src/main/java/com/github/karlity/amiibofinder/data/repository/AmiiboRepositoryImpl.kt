package com.github.karlity.amiibofinder.data.repository

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.core.models.Type
import com.github.karlity.amiibofinder.data.remote.AmiiboService
import com.github.karlity.amiibofinder.domain.repository.AmiiboRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single(binds = [AmiiboRepository::class])
@Named(Qualifiers.AMIIBO_REPOSITORY)
class AmiiboRepositoryImpl(
    @Named(Qualifiers.AMIIBO_SERVICE) val amiiboService: AmiiboService,
) : AmiiboRepository {
    override suspend fun getAllAmiibos(): Result<AmiiboList> {
        return amiiboService.getAllAmiibos()
    }

    override suspend fun getAmiibosByNameAndType(
        name: String?,
        type: Type?,
    ): Result<AmiiboList> {
        return amiiboService.getAmiibosByNameAndType(name = name, type = type)
    }

    override suspend fun getGameSeriesList(): Result<FilterCriteriaResponseList> {
        return amiiboService.getGameSeriesList()
    }

    override suspend fun getCharacterList(): Result<FilterCriteriaResponseList> {
        return amiiboService.getCharacterList()
    }
}
