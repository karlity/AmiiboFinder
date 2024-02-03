package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.R

@Composable
fun AmiiboErrorDialog(
    errorTitle: String?,
    errorMessage: String?,
    onConfirmButtonClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(
                text = errorTitle ?: stringResource(id = R.string.generic_error_title),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(5.dp),
            )
        },
        text = {
            Text(
                text = errorMessage ?: stringResource(id = R.string.generic_error_message),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(5.dp),
            )
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(5.dp),
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(5.dp),
                )
            }
        },
    )
}
