package com.elbehiry.shared.network.integeration.retrofit

import kotlinx.serialization.json.JsonObject
import retrofit2.Call
import retrofit2.http.*

@JvmSuppressWildcards
interface RetrofitServices {


    @GET
    fun get(
        @Url url: String,
        @QueryMap map: @JvmSuppressWildcards Map<String, String> = emptyMap(),
        @Tag fullApiResponse: Boolean = false
    ): Call<String>

    @POST
    fun <REQ, RES> post(
        @Url url: String,
        @Body body: REQ,
        @Tag fullApiResponse: Boolean = false,
    ): RES

    @FormUrlEncoded
    fun <R> form(
        @Url url: String,
        @FieldMap map: Map<String, Any>,
        @Tag fullApiResponse: Boolean = false,
    ): R
}