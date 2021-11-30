package com.elbehiry.shared.network.request

import com.elbehiry.shared.network.client.HttpClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

sealed class BaseRequest(
    val url: String,
    var queries: Map<String, String> = emptyMap(),
    var headers: Map<String, Any> = emptyMap(),
    var fullApiResponse: Boolean = false,
)

class GetRequest(url: String) : BaseRequest(url)

class PostRequest<TYPE>(url: String, var body: TYPE) : BaseRequest(url)

class FormRequest(url: String, var formData: Map<String, Any> = emptyMap()) : BaseRequest(url)

suspend inline fun <reified R : Any> HttpClient<*>.get(
    url: String,
    builder: GetRequest.() -> Unit,
): Result<R> {
    val request = GetRequest(url).apply(builder)
    return try {
        val respo = call(request)
        Result.success(Json.decodeFromString(respo))
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

suspend inline fun <reified R : Any> HttpClient<*>.form(
    url: String,
    builder: FormRequest.() -> Unit,
): Result<R> {
    val request = FormRequest(url).apply(builder)
    return try {
        Result.success(Json.decodeFromString(call(request)))
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

suspend inline fun <reified REQ, reified RES : Any> HttpClient<*>.post(
    url: String,
    body: REQ,
    builder: PostRequest<REQ>.() -> Unit,
): Result<RES> {
    val request = PostRequest(url, body).apply(builder)
    return try {
        Result.success(Json.decodeFromString(call(request)))
    } catch (e: Throwable) {
        Result.failure(e)
    }
}