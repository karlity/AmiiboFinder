package com.github.karlity.amiibofinder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.github.karlity.amiibofinder.ui.amiibolist.AmiiboListScreen

val typeIdKey = "typeIdKey"
private val typeIdArg = "{$typeIdKey}"

val characterNameKey = "characterNameKey"
private val characterNameArg = "{$characterNameKey}"

val gameSeriesNameKey = "gameSeriesNameKey"
private val gameSeriesNameArg = "{$gameSeriesNameKey}"

private val amiiboListRoute =
    "amiiboList?typeIdArg=$typeIdArg?characterNameIdArg=$characterNameArg?gameSeriesIdArg=$gameSeriesNameArg"

fun NavGraphBuilder.amiiboListScreen(
    onNavigateToAmiiboDetailScreen: (amiiboId: String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable(amiiboListRoute) { navBackStackEntry ->
        AmiiboListScreen(
            onNavigateToAmiiboDetailsScreen = onNavigateToAmiiboDetailScreen,
        ) {
            onNavigateBack()
        }
    }
}

// The intention is for only one of these parameters to be declared when navigating
fun NavController.navigateToAmiiboListScreen(
    typeId: String?,
    characterName: String?,
    gameSeriesName: String?,
) {
    typeId?.let {
        this.navigate(amiiboListRoute.replace(typeIdArg, typeId)) {
            popUpTo(
                amiiboFilterRoute,
            )
        }
    }
    characterName?.let {
        this.navigate(amiiboListRoute.replace(characterNameArg, characterName)) {
            popUpTo(
                amiiboFilterRoute,
            )
        }
    }
    gameSeriesName?.let {
        this.navigate(
            amiiboListRoute.replace(
                gameSeriesNameArg,
                gameSeriesName,
            ),
        ) {
            popUpTo(
                amiiboFilterRoute,
            )
        }
    }
}
