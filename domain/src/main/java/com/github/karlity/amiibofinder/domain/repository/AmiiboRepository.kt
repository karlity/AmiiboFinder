package com.github.karlity.amiibofinder.domain.repository

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.Type

interface AmiiboRepository {
    /**
     * Sends a request to fetch all registered Amiibos
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos
     */
    suspend fun getAllAmiibos(): Result<AmiiboList>

    /**
     * Sends a request to fetch all registered Amiibos by a specified name and/or type
     *
     * @param name = the name of the Amiibo being fetched
     * @param type = the type of the Amiibo being fetched
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos of
     * the specified name and/or type
     */
    suspend fun getAmiibosByNameAndType(
        name: String?,
        type: Type?,
    ): Result<AmiiboList>
}
