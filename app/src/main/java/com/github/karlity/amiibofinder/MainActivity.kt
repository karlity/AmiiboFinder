package com.github.karlity.amiibofinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.github.karlity.amiibofinder.navigation.AmiiboNavGraph
import com.github.karlity.amiibofinder.ui.theme.AmiiboAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmiiboAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AmiiboNavGraph(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
