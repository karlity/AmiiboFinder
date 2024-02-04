package com.github.karlity.amiibofinder.remote.datasource

import com.github.karlity.amiibofinder.core.Qualifiers
import com.github.karlity.amiibofinder.core.Qualifiers.AMIIBO_RETROFIT_CLIENT
import com.github.karlity.amiibofinder.core.rules.MainDispatcherRule
import com.github.karlity.amiibofinder.data.di.DataModule
import com.github.karlity.amiibofinder.remote.datasource.di.RemoteDatasourceModule
import com.github.karlity.amiibofinder.remote.datasource.mocks.amiiboMockResponse
import com.github.karlity.amiibofinder.remote.datasource.mocks.amiiboSingleMockResponse
import com.github.karlity.amiibofinder.remote.datasource.mocks.filterCriteriaMocks
import com.github.karlity.amiibofinder.remote.datasource.services.AmiiboServiceImpl
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AmiiboServiceTests : KoinTest {
    private val mockWebServer = MockWebServer()

    private val port = 8989

    private val url = "http:127.0.0.1:$port"

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private val remoteDataSource by inject<AmiiboServiceImpl>(named(Qualifiers.AMIIBO_SERVICE))

    @get:Rule
    val koinTestRule =
        KoinTestRule.create {
            modules(
                DataModule().module,
                RemoteDatasourceModule().module,
                RemoteDatasourceModule().manualModule,
                module {
                    single<Retrofit>(named(AMIIBO_RETROFIT_CLIENT)) {
                        Retrofit.Builder()
                            .baseUrl(url)
                            .client(OkHttpClient.Builder().build())
                            .addConverterFactory(MoshiConverterFactory.create(get()))
                            .build()
                    }
                },
            )
        }

    @get:Rule
    val mockProvider =
        MockProviderRule.create { clazz ->
            mockkClass(clazz)
        }

    @Before
    fun setup() {
        mockWebServer.start(8989)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Given a call When a user fetches Amiibos by AmiiboId Then the call should succeed`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(200).setBody(amiiboSingleMockResponse)
                    }
                }

            val result = remoteDataSource.getAmiiboByAmiiboId("0000000000000000")
            assert(result.isSuccess)
        }
    }

    @Test
    fun `Given a call with no internet connection When a user fetches Amiibos by AmiiboId Then the call should fail`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(400)
                    }
                }

            val result = remoteDataSource.getAmiiboByAmiiboId("0000000000000000")
            assert(result.isFailure)
        }
    }

    @Test
    fun `Given a call When a user fetches Amiibo Type by TypeId Then the call should succeed`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(200).setBody(amiiboMockResponse)
                    }
                }

            val result = remoteDataSource.getAmiibosByTypeId("0x02")
            assert(result.isSuccess)
        }
    }

    @Test
    fun `Given a call with an api error When a user fetches Amiibo Type by TypeId Then the call should fail`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(500)
                    }
                }

            val result = remoteDataSource.getAmiibosByTypeId("0x02")
            assert(result.isFailure)
        }
    }

    @Test
    fun `Given a call with a successful response when a user fetches Amiibos by Game Series Name then the call should succeed`() {
        runTest {
            // Set up a mock response with a successful status code (e.g., 200)
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(amiiboMockResponse))

            val result = remoteDataSource.getAmiibosByGameSeriesName("Super Mario")
            assert(result.isSuccess)
        }
    }

    @Test
    fun `Given a call with a successful response when a user fetches Amiibos by Character Name then the call should succeed`() {
        runTest {
            // Set up a mock response with a successful status code (e.g., 200)
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(amiiboMockResponse))

            val result = remoteDataSource.getAmiibosByCharacterName("Mario")
            assert(result.isSuccess)
        }
    }

    @Test
    fun `Given a call with an API error when a user fetches Amiibos by Game Series Name then the call should fail`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(500)
                    }
                }

            val result = remoteDataSource.getAmiibosByGameSeriesName("Super Mario")
            assert(result.isFailure)
        }
    }

    @Test
    fun `Given a call with an API error when a user fetches Amiibos by Character Name then the call should fail`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(500)
                    }
                }

            val result = remoteDataSource.getAmiibosByCharacterName("Mario")
            assert(result.isFailure)
        }
    }

    @Test
    fun `Given a call with a successful response when fetching Game Series List then the call should succeed`() {
        runTest {
            // Set up a mock response with a successful status code (e.g., 200)
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(filterCriteriaMocks))

            val result = remoteDataSource.getGameSeriesList()
            assert(result.isSuccess)
        }
    }

    @Test
    fun `Given a call with a successful response when fetching Character List then the call should succeed`() {
        runTest {
            // Set up a mock response with a successful status code (e.g., 200)
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(filterCriteriaMocks))

            val result = remoteDataSource.getCharacterList()
            assert(result.isSuccess)
        }
    }

    @Test
    fun `Given a call with an API error when fetching Game Series List then the call should fail`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(500)
                    }
                }

            val result = remoteDataSource.getGameSeriesList()
            assert(result.isFailure)
        }
    }

    @Test
    fun `Given a call with an API error when fetching Character List then the call should fail`() {
        runTest {
            mockWebServer.dispatcher =
                object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest): MockResponse {
                        return MockResponse().setResponseCode(500)
                    }
                }

            val result = remoteDataSource.getCharacterList()
            assert(result.isFailure)
        }
    }
}
