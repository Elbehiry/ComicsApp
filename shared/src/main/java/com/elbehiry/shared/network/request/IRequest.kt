package com.elbehiry.shared.network.request

interface IRequest {
    fun url(): String
    fun method(): String
    fun headers(): Map<String, List<String>>
    fun body(): String
    fun setUrl(url: String)
    fun addHeader(header : Pair<String,String>)
    fun removeHeader(key: String)
    fun tags(): Any?
}