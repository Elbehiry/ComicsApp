package com.elbehiry.shared.network.integeration.retrofit

import com.elbehiry.shared.network.client.HttpClient
import com.elbehiry.shared.network.request.BaseRequest
import com.elbehiry.shared.network.request.FormRequest
import com.elbehiry.shared.network.request.GetRequest
import com.elbehiry.shared.network.request.PostRequest
import retrofit2.Retrofit

class RetrofitHttpClient(override var engine: Retrofit) : HttpClient<Retrofit>() {


    private val service by lazy {
        engine.create(RetrofitServices::class.java)
    }

    override suspend fun call(request: BaseRequest): String {
        return when (request) {
            is GetRequest -> service.get(request.url, request.queries)
            is PostRequest -> service.post(request.url, request.body,request.headers)
            is FormRequest -> service.form(request.url, request.formData)
        }
    }
}
