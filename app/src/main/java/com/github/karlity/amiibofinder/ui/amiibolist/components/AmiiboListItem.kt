package com.github.karlity.amiibofinder.ui.amiibolist.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.core.models.Amiibo
import com.github.karlity.amiibofinder.core.models.Release

@Composable
fun AmiiboListItem(
    amiibo: Amiibo,
    onAmiiboClick: (amiiboId: String) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        onClick = { onAmiiboClick("${amiibo.head}${amiibo.tail}") },
    ) {
        AmiiboDetailsView(amiibo)
    }
}

@Composable
@Preview
private fun AmiiboListItemPreview() {
    AmiiboListItem(
        amiibo =
            Amiibo(
                amiiboSeries = "Amiibo Series",
                character = "Character",
                gameSeries = "Game Series",
                head = "",
                image = "",
                "Amiibo Name",
                release = Release("", "", "", ""),
                tail = "",
                type = "Type",
            ),
    ) {
    }
}
