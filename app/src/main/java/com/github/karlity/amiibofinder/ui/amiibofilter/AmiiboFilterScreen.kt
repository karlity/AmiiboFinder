package com.github.karlity.amiibofinder.ui.amiibofilter

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.karlity.amiibofinder.ui.amiibofilter.components.AmiiboFilterSelectionView
import com.github.karlity.amiibofinder.ui.amiibofilter.components.FilterList
import org.koin.androidx.compose.koinViewModel

@Composable
fun AmiiboFilterScreen(
    amiiboFilterViewModel: AmiiboFilterViewModel = koinViewModel(),
    onNavigateToAmiiboList: (typeId: String?, characterName: String?, gameSeriesName: String?) -> Unit,
) {
    val state = amiiboFilterViewModel.uiState.collectAsState()

    FilterList(
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

    when (state.value.loadingState) {
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
        }

        else -> {
            // no op
        }
    }
    BackHandler(state.value.filterCriteria != null) {
        amiiboFilterViewModel.resetFilter()
    }
}
