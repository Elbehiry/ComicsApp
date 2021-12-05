package com.elbehiry.shared.network.callAdapters

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class NanaCall(
    val delegate: Call<Any>
) : Call<String> {
    override fun clone(): Call<String> = NanaCall(delegate.clone())

    override fun execute(): Response<String> {
        throw UnsupportedOperationException()
    }

    override fun enqueue(callback: Callback<String>) {
        delegate.enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        callback.onResponse(
                            this@NanaCall,
                            Response.success(response.raw().body()?.string())
                        )
                    } else {
                        throw IOException()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    throw Exception(t)

//                    callback.onResponse(
//                        this@NanaCall,
//                        Response.success(Result.failure<Throwable>(t))
//                    )
                }
            }
        )
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

//    @Throws(IOException::class)
//    private fun Response<Any>.toResult(): String {
//        return body()?.let { Result.success(it) } ?: Result.failure<String>(Exception(""))
//    }
}