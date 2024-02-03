package com.github.karlity.amiibofinder.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

private val typeIdKey = "typeIdKey"
private val typeIdArg = "{$typeIdKey}"

private val characterNameKey = "characterNameKey"
private val characterNameArg = "{$characterNameKey}"

private val gameSeriesNameKey = "gameSeriesNameKey"
private val gameSeriesNameArg = "{$gameSeriesNameKey}"

private val amiiboListRoute = "amiiboList?typeIdArg=$typeIdArg?characterNameIdArg=$characterNameArg?gameSeriesIdArg=$gameSeriesNameArg"

fun NavGraphBuilder.amiiboListScreen(
    onNavigateToAmiiboDetailScreen: (amiiboId: String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable(amiiboListRoute) {
    }
}
