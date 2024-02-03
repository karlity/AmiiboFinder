package com.github.karlity.amiibofinder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.karlity.amiibofinder.ui.amiibodetails.AmiiboDetailsScreen

private val amiiboIdKey = "amiiboIdKey"
private val amiiboIdArg = "{$amiiboIdKey}"

val amiiboDetailsRoute = "amiiboDetails/$amiiboIdArg"

fun NavGraphBuilder.amiiboDetailsScreen(onNavigateBack: () -> Unit) {
    composable(amiiboDetailsRoute) { navBackStackEntry ->
        val amiiboId = navBackStackEntry.arguments?.getString(amiiboIdKey)

        AmiiboDetailsScreen(amiiboId = amiiboId) {
            onNavigateBack()
        }
    }
}

fun NavController.navigateToAmiiboDetails(amiiboId: String) {
    this.navigate(amiiboDetailsRoute.replace(amiiboIdArg, amiiboId))
}
