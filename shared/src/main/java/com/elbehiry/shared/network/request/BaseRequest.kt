package com.elbehiry.shared.network.request

import com.elbehiry.shared.network.client.HttpClient
import com.elbehiry.shared.network.features.NanaException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class BaseRequest(
    val url: String,
    var queries: Map<String, String> = emptyMap(),
    var headers: Map<String, String> = emptyMap()
)

class GetRequest(url: String) : BaseRequest(url)

class PostRequest(url: String, var body: String) : BaseRequest(url)

class FormRequest(url: String, var formData: Map<String, Any> = emptyMap()) : BaseRequest(url)

suspend inline fun <reified R : Any> HttpClient<*>.get(
    url: String,
    builder: GetRequest.() -> Unit,
): Result<R> {
    val request = GetRequest(url).apply(builder)
    return try {
        val respo = call(request)
        Result.success(Json { ignoreUnknownKeys = true }.decodeFromString(respo))
    } catch (e: Throwable) {
        val exception = when (e) {
            is SerializationException -> NanaException.ServerException(e)
            else -> e
        }
        Result.failure(exception)
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
        val exception = when (e) {
            is SerializationException -> NanaException.ServerException(e)
            else -> e
        }
        Result.failure(exception)
    }
}

suspend inline fun <reified REQ, reified RES : Any> HttpClient<*>.post(
    url: String,
    body: REQ,
    builder: PostRequest.() -> Unit = {},
): Result<RES> {
    return try {
        val request =
            PostRequest(url, Json { ignoreUnknownKeys = true }.encodeToString(body)).apply(builder)
        val response = call(request)
        val obj = Json { ignoreUnknownKeys = true }.decodeFromString<RES>(response)
        Result.success(obj)
    } catch (e: Throwable) {
        val exception = when (e) {
            is SerializationException -> NanaException.ServerException(e)
            else -> e
        }
        Result.failure(exception)
    }
}

suspend inline fun <reified REQ, reified RES : Any> HttpClient<*>.postOrThrow(
    url: String,
    body: REQ,
    builder: PostRequest.() -> Unit = {},
): RES? {
    return post<REQ, RES>(url, body, builder).getOrThrow()
}