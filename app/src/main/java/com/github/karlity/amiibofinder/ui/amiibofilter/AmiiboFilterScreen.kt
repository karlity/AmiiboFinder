package com.github.karlity.amiibofinder.ui.amiibofilter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.karlity.amiibofinder.ui.amiibofilter.components.AmiiboFilterSelectionView
import com.github.karlity.amiibofinder.ui.amiibofilter.components.FilterList
import com.github.karlity.amiibofinder.ui.shared.AmiiboLoadingAndErrorStateHandler
import com.github.karlity.amiibofinder.ui.shared.AmiiboTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun AmiiboFilterScreen(
    amiiboFilterViewModel: AmiiboFilterViewModel = koinViewModel(),
    onNavigateToAmiiboList: (typeId: String?, characterName: String?, gameSeriesName: String?) -> Unit,
) {
    val state = amiiboFilterViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            // We only want to show the TopAppBar after the filter is selected
            state.value.filterCriteria?.stringRes?.let {
                AmiiboTopAppBar(
                    title =
                        stringResource(it),
                    onNavigateBack = { amiiboFilterViewModel.resetFilter() },
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
        AmiiboFilterSelectionView(filterCriteria = state.value.filterCriteria) { filter ->
            amiiboFilterViewModel.setFilterCriteria(filter)
        }
    }

    AmiiboLoadingAndErrorStateHandler(
        loadingState = state.value.loadingState,
        onErrorDismiss = { amiiboFilterViewModel.dismissError() },
        onErrorConfirmationClick = {
            state.value.filterCriteria?.let { filter ->
                amiiboFilterViewModel.setFilterCriteria(filterCritera = filter)
            } ?: amiiboFilterViewModel.resetFilter()
        },
    )

    BackHandler(state.value.filterCriteria != null) {
        amiiboFilterViewModel.resetFilter()
    }
}
