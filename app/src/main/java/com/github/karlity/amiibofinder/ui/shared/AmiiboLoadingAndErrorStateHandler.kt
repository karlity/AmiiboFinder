package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun AmiiboLoadingAndErrorStateHandler(loadingState: LoadingState) {
    when (loadingState) {
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
}
