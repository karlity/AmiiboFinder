package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import kotlinx.coroutines.delay

@Composable
fun DebouncedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    debounceDuration: Long = 500,
    content: @Composable () -> Unit,
) {
    var isClickable by remember { mutableStateOf(true) }
    val density = LocalDensity.current.density
    val debounceDurationPx =
        with(LocalDensity.current) {
            (debounceDuration * density).toInt()
        }

    LaunchedEffect(isClickable) {
        if (isClickable) {
            delay(debounceDuration)
            isClickable = true
        }
    }

    IconButton(
        onClick = {
            if (isClickable) {
                isClickable = false
                onClick()
            }
        },
    ) {
        content()
    }
}
