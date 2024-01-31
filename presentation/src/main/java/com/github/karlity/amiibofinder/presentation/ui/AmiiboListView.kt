package com.github.karlity.amiibofinder.presentation.ui

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.github.karlity.amiibofinder.core.models.AmiiboList
import com.github.karlity.amiibofinder.domain.interactor.GetAllAmiibos
import org.koin.compose.koinInject

@Composable
fun AmiiboListView(getAllAmiibos: GetAllAmiibos = koinInject()) {
    val state =
        remember {
            mutableStateOf<AmiiboList?>(null)
        }
    LaunchedEffect(Unit) {
        getAllAmiibos.invoke().onSuccess {
            state.value = it
        }.onFailure {
            Log.wtf("wtf", "wtfff $it")
        }
    }

    Text(text = state.value.toString())
}
