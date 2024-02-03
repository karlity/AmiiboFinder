package com.github.karlity.amiibofinder.ui.amiibolist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

    AmiiboLoadingAndErrorStateHandler(loadingState = state.value.loadingState)
}

@Composable
@Preview
private fun AmiiboListScreenPreview() {
    AmiiboListScreen(null, null, null, onNavigateToAmiiboDetailsScreen = {}) {
    }
}
