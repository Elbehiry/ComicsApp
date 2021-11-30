package com.elbehiry.shared.network.integeration.retrofit

import com.elbehiry.shared.network.client.HttpClient
import com.elbehiry.shared.network.request.BaseRequest
import com.elbehiry.shared.network.request.FormRequest
import com.elbehiry.shared.network.request.GetRequest
import com.elbehiry.shared.network.request.PostRequest
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import java.io.IOException

class RetrofitHttpClient(override var engine: Retrofit) : HttpClient<Retrofit>() {


    private val service by lazy {
        engine.create(RetrofitServices::class.java)
    }

    override suspend fun call(request: BaseRequest): String {
        return when (request) {
            is GetRequest -> {
                val respone = service.get(request.url, request.queries, request.fullApiResponse)
                    .execute()
                respone.body() ?: throw IOException("Body can't be null")

            }
            is PostRequest<*> -> service.post(request.url, request.body, request.fullApiResponse)
            is FormRequest -> service.form(request.url, request.formData, request.fullApiResponse)
        }
    }
}
