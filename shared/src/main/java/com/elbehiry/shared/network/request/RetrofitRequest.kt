package com.elbehiry.shared.network.request

import okhttp3.Request

class RetrofitRequest(
    val request: Request,
) : IRequest {

    override fun url(): String {
        return request.url().toString()
    }

    override fun setUrl(url: String) {
        request.newBuilder().url(url).build()
    }

    override fun method(): String = request.method()

    override fun headers(): Map<String, List<String>> = request.headers().toMultimap()

    override fun body(): String = request.body().toString()

    override fun addHeader(header : Pair<String,String>) {
        request.headers().newBuilder().add(header.first,header.second).build()
    }

    override fun removeHeader(key: String) {
        request.headers().newBuilder().removeAll(key)
    }

    override fun tags(): Any? =  request.tag()

}