package com.github.karlity.amiibofinder.data.remote

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.Type

interface AmiiboService {
    /**
     * Sends a request to fetch all registered Amiibos
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos
     */
    suspend fun getAllAmiibos(): Result<AmiiboList>

    /**
     * Sends a request to fetch all registered Amiibos by a specified type and/or name
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos of
     * the specified type and/or name
     */
    suspend fun getAmiibosByNameAndType(
        name: String?,
        type: Type?,
    ): Result<AmiiboList>
}
