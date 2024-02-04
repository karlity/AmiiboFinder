package com.github.karlity.amiibofinder.remote.datasource.di

import com.github.karlity.amiibofinder.core.Qualifiers.AMIIBO_RETROFIT_CLIENT
import com.github.karlity.amiibofinder.remote.datasource.BuildConfig
import com.github.karlity.amiibofinder.remote.datasource.services.api.AmiiboApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteDatasourceModule =
    module {
        single<Moshi> {
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        single<Retrofit>(named(AMIIBO_RETROFIT_CLIENT)) {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .build()
        }

        single<AmiiboApi> {
            val retrofit = get<Retrofit>(named(AMIIBO_RETROFIT_CLIENT))
            retrofit.create(AmiiboApi::class.java)
        }
    }

@Module
@ComponentScan("com.github.karlity.amiibofinder.remote.datasource")
public class RemoteDatasourceModule {
    val manualModule get() = remoteDatasourceModule
}
