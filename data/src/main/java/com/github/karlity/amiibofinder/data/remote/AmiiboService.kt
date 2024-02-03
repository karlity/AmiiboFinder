package com.github.karlity.amiibofinder.data.remote

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
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

    /**
     * Sends a request to fetch all game series that include Amiibos
     *
     * @return Result<FilterCriteriaResponseList> with a success case containing a list of all game series with
     * registered Amiibos associated with them
     */
    suspend fun getGameSeriesList(): Result<FilterCriteriaResponseList>

    /**
     * Sends a request to fetch all characters with Amiibos associated with them
     *
     * @return Result<FilterCriteriaResponseList> with a success case containing a list of characters with
     * registered Amiibos associated with them
     */
    suspend fun getCharacterList(): Result<FilterCriteriaResponseList>
}
