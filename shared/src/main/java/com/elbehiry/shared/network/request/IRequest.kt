package com.elbehiry.shared.network.request

interface IRequest {
    fun url(): String
    fun method(): String
    fun headers(): Map<String, String>
    fun body(): String?
    fun setUrl(url: String)
    fun addHeader(key: String, value: String)
    fun removeHeader(key: String)
    fun tags(): List<String>
}