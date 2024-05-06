package br.com.joaovq.uppersports.data.remote

import br.com.joaovq.uppersports.BuildConfig
import br.com.joaovq.uppersports.core.utils.http.ApiSportsHeaders
import br.com.joaovq.uppersports.core.utils.http.NetworkResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueResponse
import br.com.joaovq.uppersports.league.data.remote.model.standings.LeagueStandingsResponse
import br.com.joaovq.uppersports.league.data.remote.model.league.LeagueType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.request
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import timber.log.Timber

class ApiSportsService(httpClient: HttpClient) {
    private val log = Timber.tag(this::class.java.simpleName)

    private val sportsClient = httpClient.config {
        defaultRequest {
            url {
                host = "v3.football.api-sports.io"
                protocol = URLProtocol.HTTPS
                headers {
                    append(ApiSportsHeaders.XRapidAPIHost, "v3.football.api-sports.io")
                    append(ApiSportsHeaders.XRapidAPIKey, BuildConfig.API_KEY_SPORTS)
                }
            }
        }
        ResponseObserver {
            log.i("${it.request.url}:   br.com.joaovq.uppersports.data.remote.Status code ${it.status}")
            log.i("Request time ${it.requestTime}")
            log.i("Response time ${it.responseTime}")
        }
    }

    suspend fun getLeagueById(id: Int = 0): NetworkResponse<PaginatedResponse<List<LeagueResponse>>> {
        val httpResponse = sportsClient.get("leagues") {
            parameter("id", id)
        }
        return try {
            when (httpResponse.status) {
                HttpStatusCode.OK -> {
                    log.i("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.Success(httpResponse.body())
                }

                HttpStatusCode.BadRequest -> {
                    log.e("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.BadRequest(httpResponse.body())
                }

                else -> NetworkResponse.InternalServerError("Internal server error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e)
        }
    }

    suspend fun getAllLeagues(
        search: String = "",
        type: LeagueType? = null
    ): NetworkResponse<PaginatedResponse<List<LeagueResponse>>> {
        val httpResponse = sportsClient.get("leagues") {
            search.isNotBlank().let { isNotBlank ->
                if (isNotBlank) parameter("search", search)
            }
            type?.value?.let {
                parameter("type", it)
            }
        }
        return try {
            when (httpResponse.status) {
                HttpStatusCode.OK -> {
                    log.i("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.Success(httpResponse.body())
                }

                HttpStatusCode.BadRequest -> {
                    log.e("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.BadRequest(httpResponse.body())
                }

                else -> NetworkResponse.InternalServerError("Internal server error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e)
        }
    }

    suspend fun getLiveFixtures(
        to: String? = null,
        from: String? = null
    ): NetworkResponse<PaginatedResponse<List<FixtureResponse>>> {
        val httpResponse = sportsClient.get("fixtures") {
            parameter("live", "all")
            to?.let {
                parameter("to", it)
            }
            from?.let {
                parameter("from", it)
            }
        }
        return try {
            when (httpResponse.status) {
                HttpStatusCode.OK -> {
                    log.i("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.Success(httpResponse.body())
                }

                HttpStatusCode.BadRequest -> {
                    log.e("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.BadRequest(httpResponse.body())
                }

                else -> NetworkResponse.InternalServerError("Internal server error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e)
        }
    }

    suspend fun getTeams(
        search: String?
    ): NetworkResponse<PaginatedResponse<List<TeamResponse>>> {
        val httpResponse = sportsClient.get("teams") {
            search?.let {
                parameter("search", it)
            }
        }
        return try {
            when (httpResponse.status) {
                HttpStatusCode.OK -> {
                    log.i("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.Success(httpResponse.body())
                }

                HttpStatusCode.BadRequest -> {
                    log.e("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.BadRequest(httpResponse.body())
                }

                else -> NetworkResponse.InternalServerError("Internal server error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e)
        }
    }

    suspend fun getStandingsLeague(
        season: Int
    ): NetworkResponse<PaginatedResponse<LeagueStandingsResponse>> {
        val httpResponse = sportsClient.get("fixtures") {
            parameter("season", season)
        }
        return try {
            when (httpResponse.status) {
                HttpStatusCode.OK -> {
                    log.i("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.Success(httpResponse.body())
                }

                HttpStatusCode.BadRequest -> {
                    log.e("Body: ${httpResponse.body<String>()}")
                    NetworkResponse.BadRequest(httpResponse.body())
                }

                else -> NetworkResponse.InternalServerError("Internal server error")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e)
        }
    }
}