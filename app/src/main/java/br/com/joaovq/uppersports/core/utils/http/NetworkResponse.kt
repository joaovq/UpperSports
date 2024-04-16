package br.com.joaovq.uppersports.core.utils.http

sealed class NetworkResponse<out T: Any> {
    data class Success<out T: Any>(val data: T) : NetworkResponse<T>()
    data class Error(val exception: Exception) : NetworkResponse<Nothing>()
    data class BadRequest(val message: String) : NetworkResponse<Nothing>()
    data class InternalServerError(val message: String) : NetworkResponse<Nothing>()
}
