package com.github.karlity.amiibofinder.ui.amiibofilter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.R
import com.github.karlity.amiibofinder.ui.amiibofilter.AmiiboFilterCritera

@Composable
fun AmiiboFilterSelectionView(
    filterCriteria: AmiiboFilterCritera?,
    onSelectedFilter: (AmiiboFilterCritera) -> Unit,
) {
    AnimatedVisibility(
        visible = filterCriteria == null,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(50.dp),
            ) {
                Text(
                    text = stringResource(R.string.select_search_criteria),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    text = stringResource(R.string.by),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(10.dp),
                )

                enumValues<AmiiboFilterCritera>().map {
                    OutlinedButton(
                        onClick = { onSelectedFilter(it) },
                        modifier = Modifier.width(300.dp).padding(10.dp),
                    ) {
                        Text(
                            text = stringResource(it.stringRes),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AmiiboFilterSelectionViewPreview() {
    AmiiboFilterSelectionView(null) {
    }
}
