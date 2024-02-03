package com.github.karlity.amiibofinder.ui.amiibofilter.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponseList
import com.github.karlity.amiibofinder.core.models.Type
import com.github.karlity.amiibofinder.ui.amiibofilter.AmiiboFilterCritera

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterList(
    filterCriteria: AmiiboFilterCritera?,
    gameSeriesList: FilterCriteriaResponseList?,
    characterList: FilterCriteriaResponseList?,
    onTypeSelect: (Type) -> Unit,
    onCharacterSelect: (name: String) -> Unit,
    onGameSeriesSelect: (key: String) -> Unit,
    typeList: List<Type>,
) {
    LazyColumn {
        filterCriteria?.let {
            stickyHeader {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = filterCriteria.stringRes),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                    },
                )
            }
        }
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

            null -> {
            }
        }
    }
}

@Composable
@Preview
private fun FilterListPreview() {
}
