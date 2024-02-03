package com.github.karlity.amiibofinder.ui.amiibodetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.github.karlity.amiibofinder.R
import com.github.karlity.amiibofinder.core.models.Amiibo

@Composable
fun AmiiboDetails(
    amiibo: Amiibo,
    modifier: Modifier = Modifier,
) {
    Surface {
        Column(modifier = modifier.fillMaxSize()) {
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
                            .size(250.dp)
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
            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

            Text(
                text = "${stringResource(R.string.amiibo_series_label)} ${amiibo.amiiboSeries}",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
