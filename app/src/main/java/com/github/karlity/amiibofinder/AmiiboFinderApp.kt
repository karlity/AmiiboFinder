package com.github.karlity.amiibofinder

import android.app.Application
import com.github.karlity.amiibofinder.data.di.DataModule
import com.github.karlity.amiibofinder.di.AppModule
import com.github.karlity.amiibofinder.domain.di.DomainModule
import com.github.karlity.amiibofinder.remote.datasource.BuildConfig
import com.github.karlity.amiibofinder.remote.datasource.di.RemoteDatasourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module
import timber.log.Timber

class AmiiboFinderApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@AmiiboFinderApp)
            androidLogger()
            modules(
                AppModule().module,
                DomainModule().module,
                RemoteDatasourceModule().module,
                RemoteDatasourceModule().manualModule,
                DataModule().module,
            )
        }
    }
}
