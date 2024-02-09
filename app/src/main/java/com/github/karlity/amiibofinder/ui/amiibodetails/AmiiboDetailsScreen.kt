package com.github.karlity.amiibofinder.ui.amiibodetails

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.ui.amiibodetails.components.AmiiboDetails
import com.github.karlity.amiibofinder.ui.shared.AmiiboLoadingAndErrorStateHandler
import com.github.karlity.amiibofinder.ui.shared.AmiiboTopAppBar
import com.github.karlity.amiibofinder.ui.shared.EmptyState
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AmiiboDetailsScreen(
    amiiboDetailsViewModel: AmiiboDetailsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel = remember { amiiboDetailsViewModel }
    val state = viewModel.uiState.collectAsState()

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
        if (state.value.loadingState != LoadingState.IDLE && state.value.loadingState != LoadingState.LOADING) {
            EmptyState()
        }
    }
    AmiiboLoadingAndErrorStateHandler(
        loadingState = state.value.loadingState,
        onErrorDismiss =
            remember(viewModel) {
                { viewModel.dismissError() }
            },
        snackbarState = snackbarHostState,
        onErrorConfirmationClick =
            remember(viewModel) {
                {
                    state.value.amiiboId?.let { amiiboId ->
                        viewModel.fetchAmiiboDetails(
                            amiiboId,
                        )
                    } ?: onNavigateBack()
                }
            },
    )
}

@Composable
@Preview
private fun AmiiboDetailsScreenPreview() {
    AmiiboDetailsScreen {}
}
