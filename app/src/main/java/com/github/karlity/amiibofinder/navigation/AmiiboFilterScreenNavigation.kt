package com.github.karlity.amiibofinder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.karlity.amiibofinder.ui.amiibofilter.AmiiboFilterScreen

val amiiboFilterRoute = "amiiboFilter"

fun NavGraphBuilder.amiiboFilterScreen(
    onNavigateToAmiiboListScreen: (typeId: String?, characterName: String?, gameSeriesName: String?) -> Unit,
) {
    composable(amiiboFilterRoute) { navBackStackEntry ->
        AmiiboFilterScreen(onNavigateToAmiiboList = onNavigateToAmiiboListScreen)
    }
}

fun NavController.navigateToAmiiboFilterScreen() {
    this.navigate(amiiboFilterRoute)
}
