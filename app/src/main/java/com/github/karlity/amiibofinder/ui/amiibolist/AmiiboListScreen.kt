package com.github.karlity.amiibofinder.ui.amiibolist

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.R
import com.github.karlity.amiibofinder.ui.amiibolist.components.AmiiboList
import com.github.karlity.amiibofinder.ui.shared.AmiiboLoadingAndErrorStateHandler
import com.github.karlity.amiibofinder.ui.shared.AmiiboTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun AmiiboListScreen(
    typeId: String?,
    characterName: String?,
    gameSeriesName: String?,
    amiiboListViewModel: AmiiboListViewModel = koinViewModel(),
    onNavigateToAmiiboDetailsScreen: (amiiboId: String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val state = amiiboListViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = state.value.amiiboList == null) {
        amiiboListViewModel.fetchAmiibos(
            typeId = typeId,
            characterName = characterName,
            gameSeriesName = gameSeriesName,
        )
    }

    Scaffold(
        topBar = {
            AmiiboTopAppBar(
                title = stringResource(id = R.string.amibo_list_title),
                onNavigateBack = onNavigateBack,
            )
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
        AmiiboList(
            amiiboList = state.value.amiiboList,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) { amiiboId ->
            onNavigateToAmiiboDetailsScreen(amiiboId)
        }
    }

    AmiiboLoadingAndErrorStateHandler(
        loadingState = state.value.loadingState,
        onErrorDismiss = { amiiboListViewModel.dismissError() },
        snackbarState = snackbarHostState,
        onErrorConfirmationClick = {
            amiiboListViewModel.fetchAmiibos(
                typeId = typeId,
                characterName = characterName,
                gameSeriesName = gameSeriesName,
            )
        },
    )
}

@Composable
@Preview
private fun AmiiboListScreenPreview() {
    AmiiboListScreen(null, null, null, onNavigateToAmiiboDetailsScreen = {}) {
    }
}
