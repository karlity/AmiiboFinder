package com.github.karlity.amiibofinder.domain.repository

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
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

    /**
     * Sends a request to fetch all game series that include Amiibos
     *
     * @return Result<GameSeriesList> with a success case containing a list of all game series with
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
