package com.elbehiry.shared.network.request

import okhttp3.Request

class RetrofitRequest(
    var request: Request,
) : IRequest {

    override fun url(): String {
        return request.url().toString()
    }

    override fun setUrl(url: String) {
        request = request.newBuilder().url(url).build()
    }

    override fun method(): String = request.method()

    override fun headers(): Map<String, List<String>> = request.headers().toMultimap()

    override fun body(): String = request.body().toString()

    override fun addHeader(header: Pair<String, String>) {
        request = request.newBuilder().addHeader(header.first, header.second).build()
    }

    override fun removeHeader(key: String) {
        request = request.newBuilder().removeHeader(key).build()
    }

    override fun tags(): Any? = request.tag()

}