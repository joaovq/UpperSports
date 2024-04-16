package br.com.joaovq.uppersports.data.remote

import com.google.gson.annotations.SerializedName

data class PaginatedResponse<out T>(
    val errors: List<String>,
    @SerializedName("get")
    val route: String,
    val paging: Paging,
    /*val parameters: String,*/
    val response: List<T>,
    val results: Int
)

data class Paging(
    val current: Int,
    val total: Int
)