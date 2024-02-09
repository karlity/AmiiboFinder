package com.github.karlity.amiibofinder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.karlity.amiibofinder.ui.amiibodetails.AmiiboDetailsScreen

val amiiboIdKey = "amiiboIdKey"
private val amiiboIdArg = "{$amiiboIdKey}"

val amiiboDetailsRoute = "amiiboDetails/$amiiboIdArg"

fun NavGraphBuilder.amiiboDetailsScreen(onNavigateBack: () -> Unit) {
    composable(amiiboDetailsRoute) {
        AmiiboDetailsScreen {
            onNavigateBack()
        }
    }
}

fun NavController.navigateToAmiiboDetails(amiiboId: String) {
    this.navigate(amiiboDetailsRoute.replace(amiiboIdArg, amiiboId))
}
