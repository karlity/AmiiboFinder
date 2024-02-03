package com.github.karlity.amiibofinder.domain.interactor

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.domain.repository.AmiiboRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class GetGameSeriesList(
    @Named(Qualifiers.AMIIBO_REPOSITORY) private val amiiboRepository: AmiiboRepository,
) {
    suspend operator fun invoke(): Result<FilterCriteriaResponseList> = amiiboRepository.getGameSeriesList()
}
