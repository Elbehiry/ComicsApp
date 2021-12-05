package com.elbehiry.shared.network.response

import com.elbehiry.shared.network.request.IRequest
import com.elbehiry.shared.network.request.RetrofitRequest
import okhttp3.Response

class RetrofitResponse(var response: Response) : IResponse {

    override fun request(): IRequest = RetrofitRequest(response.request())

    override fun protocol(): String = response.protocol().toString()

    override fun code(): Int = response.code()

    override fun message(): String? = response.message()

    override fun headers(): Map<String, List<String>> = response.headers().toMultimap()

    override fun body(): String? = response.body()?.string()
    override fun addRequestHeader(pair: Pair<String, String>) {
        response = response.newBuilder()
            .request(response.request().newBuilder().addHeader(pair.first, pair.second).build())
            .build()
    }
}