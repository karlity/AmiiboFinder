package com.github.karlity.amiibofinder.ui.amiibolist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.github.karlity.amiibofinder.R
import com.github.karlity.amiibofinder.core.models.Amiibo
import com.github.karlity.amiibofinder.core.models.Release

@Composable
fun AmiiboDetailsView(amiibo: Amiibo) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
    ) {
        AsyncImage(
            model =
                ImageRequest.Builder(LocalContext.current)
                    .memoryCachePolicy(CachePolicy.ENABLED).diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED).data(amiibo.image).build(),
            contentDescription = stringResource(id = R.string.amiibo_image_content_description),
            modifier =
                Modifier
                    .size(175.dp)
                    .padding(5.dp),
        )

        Column(modifier = Modifier.padding(5.dp)) {
            Text(
                text = amiibo.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(5.dp),
            )

            Text(
                text = "${stringResource(id = R.string.character_label)} ${amiibo.character}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(5.dp),
            )
            Text(
                text = "${stringResource(id = R.string.game_series_label)} ${amiibo.gameSeries}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}

@Composable
@Preview
private fun AmiiboDetailsHeaderPreview() {
    AmiiboDetailsView(
        amiibo =
            Amiibo(
                amiiboSeries = "Amiibo Series",
                character = "Character",
                gameSeries = "Game Series",
                head = "",
                image = "",
                "Amiibo Name",
                release = Release("adf", "afd", "af", "af"),
                tail = "",
                type = "Type",
            ),
    )
}
