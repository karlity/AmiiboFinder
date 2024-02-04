package com.github.karlity.amiibofinder.ui.amiibodetails

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.ui.amiibodetails.components.AmiiboDetails
import com.github.karlity.amiibofinder.ui.shared.AmiiboLoadingAndErrorStateHandler
import com.github.karlity.amiibofinder.ui.shared.AmiiboTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun AmiiboDetailsScreen(
    amiiboId: String?,
    amiiboDetailsViewModel: AmiiboDetailsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state = amiiboDetailsViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.value.amiibo == null) {
        amiiboId?.let {
            amiiboDetailsViewModel.fetchAmiiboDetails(amiiboId)
        } ?: amiiboDetailsViewModel.setError()
    }

    Scaffold(
        topBar = {
            state.value.amiibo?.name?.let { name ->
                AmiiboTopAppBar(
                    title = name,
                    onNavigateBack = onNavigateBack,
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    shape = RoundedCornerShape(7.dp),
                    snackbarData = it,
                )
            }
        },
    ) { paddingValues ->
        state.value.amiibo?.let { amiibo ->
            AmiiboDetails(amiibo = amiibo, modifier = Modifier.padding(paddingValues))
        }
    }

    AmiiboLoadingAndErrorStateHandler(
        loadingState = state.value.loadingState,
        onErrorDismiss = { amiiboDetailsViewModel.dismissError() },
        snackbarState = snackbarHostState,
        onErrorConfirmationClick = {
            amiiboId?.let {
                amiiboDetailsViewModel.fetchAmiiboDetails(
                    amiiboId,
                )
            } ?: onNavigateBack()
        },
    )
}

@Composable
@Preview
private fun AmiiboDetailsScreenPreview() {
    AmiiboDetailsScreen(amiiboId = "") {}
}
