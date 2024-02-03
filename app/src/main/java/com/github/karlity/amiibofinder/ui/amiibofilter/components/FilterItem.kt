package com.github.karlity.amiibofinder.ui.amiibofilter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.core.models.FilterCriteriaResponse

@Composable
fun FilterItem(
    filerCriteria: FilterCriteriaResponse,
    onFilterSelect: (FilterCriteriaResponse) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
        onClick = { onFilterSelect(filerCriteria) },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(
                text = filerCriteria.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(10.dp),
            )
        }
    }
}

@Composable
@Preview
private fun FilterItemPreview() {
    FilterItem(filerCriteria = FilterCriteriaResponse("key", "name")) {
    }
}
