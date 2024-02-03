package com.github.karlity.amiibofinder.ui.amiibolist.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.karlity.amiibofinder.core.models.AmiiboList

@Composable
fun AmiiboList(
    amiiboList: AmiiboList?,
    modifier: Modifier = Modifier,
    onAmiiboClick: (amiiboId: String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        amiiboList?.amiibo?.map {
            item {
                AmiiboListItem(amiibo = it) { amiiboId ->
                    onAmiiboClick(
                        amiiboId,
                    )
                }
            }
        }
    }
}
