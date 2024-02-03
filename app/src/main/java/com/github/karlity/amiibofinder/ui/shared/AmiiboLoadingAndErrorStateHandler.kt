package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AmiiboLoadingAndErrorStateHandler(
    loadingState: LoadingState,
    errorTitle: String? = null,
    errorMessage: String? = null,
    onErrorConfirmationClick: () -> Unit,
    onErrorDismiss: () -> Unit,
) {
    when (loadingState) {
        LoadingState.LOADING -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator()
            }
        }

        LoadingState.ERROR -> {
            AmiiboErrorDialog(
                errorTitle = errorTitle,
                errorMessage = errorMessage,
                onConfirmButtonClick = onErrorConfirmationClick,
                onDismiss = onErrorDismiss,
            )
        }

        else -> {
            // no op
        }
    }
}

@Composable
@Preview
private fun AmiiboLoadingAndErrorStateHandlerPreview() {
    AmiiboLoadingAndErrorStateHandler(loadingState = LoadingState.ERROR, onErrorConfirmationClick = { }) {
    }
}
