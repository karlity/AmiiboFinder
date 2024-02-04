package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.R

@Composable
fun EmptyState() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = stringResource(id = R.string.no_results_icon),
                modifier = Modifier.size(50.dp),
            )
            Text(
                text = stringResource(id = R.string.no_results),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(5.dp),
            )
        }
    }
}

@Composable
@Preview
private fun EmptyStatePreview() {
    EmptyState()
}
