package com.github.karlity.amiibofinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AmiiboNavGraph(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = amiiboFilterRoute,
        modifier = modifier,
    ) {
        amiiboFilterScreen { typeId, characterName, gameSeriesName ->
        }
    }
}
