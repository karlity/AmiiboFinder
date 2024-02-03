package com.github.karlity.amiibofinder.domain.interactor

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.domain.repository.AmiiboRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class GetAmiibosByGameSeriesName(
    @Named(Qualifiers.AMIIBO_REPOSITORY) val amiiboRepository: AmiiboRepository,
) {
    suspend operator fun invoke(gameSeriesName: String): Result<AmiiboList> = amiiboRepository.getAmiibosByGameSeriesName(gameSeriesName)
}
