package com.github.karlity.amiibofinder.ui.amiibofilter

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.karlity.amiibofinder.ui.amiibofilter.components.AmiiboFilterSelectionView
import com.github.karlity.amiibofinder.ui.amiibofilter.components.FilterList
import com.github.karlity.amiibofinder.ui.shared.AmiiboLoadingAndErrorStateHandler
import com.github.karlity.amiibofinder.ui.shared.AmiiboTopAppBar
import com.github.karlity.amiibofinder.ui.shared.EmptyState
import com.github.karlity.amiibofinder.ui.shared.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AmiiboFilterScreen(
    amiiboFilterViewModel: AmiiboFilterViewModel = koinViewModel(),
    onNavigateToAmiiboList: (typeId: String?, characterName: String?, gameSeriesName: String?) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val viewModel = remember { amiiboFilterViewModel }
    val state = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            // We only want to show the TopAppBar after the filter is selected
            state.value.filterCriteria?.stringRes?.let {
                AmiiboTopAppBar(
                    title =
                        stringResource(it),
                    onNavigateBack =
                        remember(viewModel) {
                            {
                                viewModel.resetFilter()
                            }
                        },
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
        FilterList(
            modifier = Modifier.padding(paddingValues),
            filterCriteria = state.value.filterCriteria,
            gameSeriesList = state.value.gameSeriesList,
            characterList = state.value.characterList,
            onTypeSelect = { onNavigateToAmiiboList(it.key, null, null) },
            onCharacterSelect = { onNavigateToAmiiboList(null, it, null) },
            onGameSeriesSelect = { onNavigateToAmiiboList(null, null, it) },
            typeList = state.value.typeList,
        )

        if (state.value.loadingState != LoadingState.IDLE && state.value.loadingState != LoadingState.LOADING) {
            EmptyState()
        }

        AmiiboFilterSelectionView(filterCriteria = state.value.filterCriteria, onSelectedFilter = viewModel::setFilterCriteria)
    }

    AmiiboLoadingAndErrorStateHandler(
        loadingState = state.value.loadingState,
        snackbarState = snackbarHostState,
        onErrorDismiss =
            remember(viewModel) {
                { viewModel.resetFilter() }
            },
        onErrorConfirmationClick =
            remember(viewModel) {
                {
                    state.value.filterCriteria?.let { filter ->
                        viewModel.setFilterCriteria(filterCritera = filter)
                    } ?: viewModel.resetFilter()
                }
            },
    )

    BackHandler(state.value.filterCriteria != null) {
        viewModel.resetFilter()
    }
}

@Composable
@Preview
private fun AmiiboFilterScreenPreview() {
    AmiiboFilterScreen { type, characterName, gameSeriesName ->
    }
}
