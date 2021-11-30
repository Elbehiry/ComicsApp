package com.elbehiry.shared.network.integeration.retrofit

import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.QueryMap
import retrofit2.http.Url

@JvmSuppressWildcards
interface RetrofitServices {
    @GET
    suspend fun get(
        @Url url: String,
        @QueryMap map: @JvmSuppressWildcards Map<String, String> = emptyMap()
    ): String

    @POST
    suspend fun post(
        @Url url: String,
        @Body body: String,
        @HeaderMap headers: Map<String, String>
    ): String

    @FormUrlEncoded
    suspend fun form(
        @Url url: String,
        @FieldMap map: Map<String, Any>
    ): String
}