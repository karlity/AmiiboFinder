package com.github.karlity.amiibofinder.domain.repository

import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.core.models.AmiiboSingle
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList

interface AmiiboRepository {
    /**
     * Sends a request to fetch the registered Amiibo by its specified ID
     *
     * @param amiiboId - ID for the Amiibo to be fetched
     *
     * @return Result<AmiiboList> with a success case containing a single registered Amiibo
     *
     */
    suspend fun getAmiiboByAmiiboId(amiiboId: String): Result<AmiiboSingle>

    /**
     * Sends a request to fetch all registered Amiibos by a specified typeId
     *
     * @param typeId - the ID of a selected type specified by the api
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos of
     * the specified typeId
     */
    suspend fun getAmiibosByTypeId(typeId: String): Result<AmiiboList>

    /**
     * Sends a request to fetch all registered Amiibos by a specified name of a game series
     *
     * @param gameSeriesName - name of a selected game series
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos of
     * a specified name of a game series name
     */
    suspend fun getAmiibosByGameSeriesName(gameSeriesName: String): Result<AmiiboList>

    /**
     * Sends a request to fetch all registered Amiibos by a specified typeId
     *
     * @param characterName - the name of a selected character
     *
     * @return Result<AmiiboList> with a success case containing a list of all registered Amiibos of
     * the specified character name
     */
    suspend fun getAmiibosByCharacterName(characterName: String): Result<AmiiboList>

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
