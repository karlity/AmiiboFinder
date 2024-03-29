package com.github.karlity.amiibofinder.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.karlity.amiibofinder.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmiiboTopAppBar(
    title: String,
    onNavigateBack: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            onNavigateBack?.let {
                DebouncedIconButton(onClick = {
                    onNavigateBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription =
                            stringResource(
                                id = R.string.navigate_back_content_description,
                            ),
                    )
                }
            }
        },
    )
}
