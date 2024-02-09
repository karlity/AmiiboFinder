package com.github.karlity.amiibofinder.ui.amiibofilter.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.core.models.Type
import com.github.karlity.amiibofinder.ui.amiibofilter.AmiiboFilterCritera
import timber.log.Timber

@Composable
fun FilterList(
    modifier: Modifier = Modifier,
    filterCriteria: AmiiboFilterCritera?,
    gameSeriesList: FilterCriteriaResponseList?,
    characterList: FilterCriteriaResponseList?,
    onTypeSelect: (Type) -> Unit,
    onCharacterSelect: (name: String) -> Unit,
    onGameSeriesSelect: (key: String) -> Unit,
    typeList: List<Type>,
) {
    LazyColumn(modifier) {
        when (filterCriteria) {
            AmiiboFilterCritera.TYPE -> {
                typeList.map {
                    item {
                        FilterTypeItem(type = it) {
                            onTypeSelect(it)
                        }
                    }
                }
            }

            AmiiboFilterCritera.GAME -> {
                gameSeriesList?.amiibo?.sortedBy { it.name }?.distinctBy { it.name }?.map {
                    item {
                        FilterItem(filerCriteria = it) {
                            onGameSeriesSelect(it.name)
                        }
                    }
                }
            }

            AmiiboFilterCritera.CHARACTER -> {
                characterList?.amiibo?.sortedBy { it.name }?.distinctBy { it.name }?.map {
                    item {
                        FilterItem(filerCriteria = it) {
                            onCharacterSelect(it.name)
                        }
                    }
                }
            }

            null -> { // no-op
            }
        }
    }
}

@Composable
@Preview
private fun FilterListPreview() {
}
