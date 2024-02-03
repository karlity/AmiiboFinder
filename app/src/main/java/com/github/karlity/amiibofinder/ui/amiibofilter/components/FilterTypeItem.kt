package com.github.karlity.amiibofinder.ui.amiibofilter.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.core.models.Type

@Composable
fun FilterTypeItem(
    type: Type,
    onFilterSelect: (Type) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
        onClick = { onFilterSelect(type) },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(
                text = type.text,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}
