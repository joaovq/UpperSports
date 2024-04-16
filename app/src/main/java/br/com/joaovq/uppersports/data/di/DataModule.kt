package br.com.joaovq.uppersports.data.di

import br.com.joaovq.uppersports.data.remote.ApiSportsService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import org.koin.dsl.module
import java.net.SocketException

val dataModule = module {
    single {
        HttpClient(CIO) {
            engine {
                maxConnectionsCount = 1000
            }
            /*install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
                filter { request ->
                    request.url.host.contains("ktor.io")
                }
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }*/
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                    serializeNulls()
                    setLenient()
                }
            }
            install(HttpRequestRetry) {
                exponentialDelay()
                retryOnExceptionOrServerErrors(3)
                retryOnExceptionIf { request, cause ->
                    cause is SocketException
                }
            }
        }
    }

    single {
        ApiSportsService(get())
    }
}