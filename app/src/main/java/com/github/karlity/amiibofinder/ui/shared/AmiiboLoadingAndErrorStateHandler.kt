package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.karlity.amiibofinder.R

@Composable
fun AmiiboLoadingAndErrorStateHandler(
    loadingState: LoadingState,
    snackbarState: SnackbarHostState,
    errorTitle: String? = null,
    errorMessage: String? = null,
    onErrorConfirmationClick: () -> Unit,
    onErrorDismiss: () -> Unit,
) {
    val noInternetError = stringResource(R.string.no_internet_error)
    when (loadingState) {
        LoadingState.LOADING -> {
            Surface {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
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

        LoadingState.NO_INTERNET -> {
            LaunchedEffect(key1 = Unit) {
                snackbarState.showSnackbar(
                    message = noInternetError,
                    duration = SnackbarDuration.Short,
                )
            }
        }

        else -> {
            // no op - other states handles elsewhere
        }
    }
}

@Composable
@Preview
private fun AmiiboLoadingAndErrorStateHandlerPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    AmiiboLoadingAndErrorStateHandler(
        loadingState = LoadingState.ERROR,
        snackbarState = snackbarHostState,
        onErrorConfirmationClick = { },
    ) {
    }
}
