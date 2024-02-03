package com.github.karlity.amiibofinder.domain.interactor

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.models.AmiiboSingle
import com.github.karlity.amiibofinder.domain.repository.AmiiboRepository
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class GetAmiiboByAmiiboId(
    @Named(Qualifiers.AMIIBO_REPOSITORY) private val amiiboRepository: AmiiboRepository,
) {
    suspend operator fun invoke(amiiboId: String): Result<AmiiboSingle> = amiiboRepository.getAmiiboByAmiiboId(amiiboId)
}
