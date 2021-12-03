package com.elbehiry.shared.network.response

import com.elbehiry.shared.network.request.IRequest
import com.elbehiry.shared.network.request.RetrofitRequest
import okhttp3.Response

class RetrofitResponse(val response: Response) : IResponse {

    override fun request(): IRequest = RetrofitRequest(response.request())

    override fun protocol(): String = response.protocol().toString()

    override fun code(): Int = response.code()

    override fun message(): String? = response.message()

    override fun headers(): Map<String, List<String>> = response.headers().toMultimap()

    override fun body(): String? = response.body()?.string()
}