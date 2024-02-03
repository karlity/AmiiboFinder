package com.github.karlity.amiibofinder.ui.amiibodetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun AmiiboDetails(
    amiibo: Amiibo,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier.fillMaxSize()) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.fillMaxWidth(),
            ) {
                AsyncImage(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .networkCachePolicy(CachePolicy.ENABLED).data(amiibo.image).build(),
                    contentDescription = stringResource(id = R.string.amiibo_image_content_description),
                    modifier =
                        Modifier
                            .size(300.dp)
                            .padding(10.dp),
                )
            }
        }
        item {
            Column(
                modifier =
                    Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
            ) {
                Text(
                    text = "${stringResource(id = R.string.character_label)} ${amiibo.character}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    text = "${stringResource(id = R.string.game_series_label)} ${amiibo.gameSeries}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    text = "${stringResource(R.string.amiibo_series_label)} ${amiibo.amiiboSeries}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp),
                )

                Text(
                    text = "${stringResource(R.string.type_label)} ${amiibo.type}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp),
                )

                Text(
                    text = stringResource(R.string.release_label),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(10.dp),
                )

                Text(
                    text = "${stringResource(R.string.north_america_release_label)} ${amiibo.release.na}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                )

                Text(
                    text = "${stringResource(R.string.japan_release_label)} ${amiibo.release.jp}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                )

                Text(
                    text = "${stringResource(R.string.europe_release_label)} ${amiibo.release.eu}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                )
                Text(
                    text = "${stringResource(R.string.australia_release_label)} ${amiibo.release.au}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                )
            }
        }
    }
}

@Composable
@Preview
private fun AmiiboDetailsPreview() {
    AmiiboDetails(
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
