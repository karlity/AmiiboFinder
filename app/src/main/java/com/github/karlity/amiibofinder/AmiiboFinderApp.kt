package com.github.karlity.amiibofinder

import android.app.Application
import com.github.karlity.amiibofinder.data.di.DataModule
import com.github.karlity.amiibofinder.di.AppModule
import com.github.karlity.amiibofinder.domain.di.DomainModule
import com.github.karlity.amiibofinder.presentation.di.PresentationModule
import com.github.karlity.amiibofinder.remote.datasource.di.RemoteDatasourceModule
import com.github.karlity.amiibofinder.remote.datasource.di.remoteDatasourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class AmiiboFinderApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AmiiboFinderApp)
            androidLogger()
            modules(
                AppModule().module,
                remoteDatasourceModule,
                DomainModule().module,
                RemoteDatasourceModule().module,
                RemoteDatasourceModule().manualModule,
                DataModule().module,
                PresentationModule().module,
            )
        }
    }
}
