package com.elbehiry.shared.network.callAdapters

import com.elbehiry.shared.network.features.NanaException

import com.google.android.gms.common.api.ApiException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import com.elbehiry.shared.network.features.NanaException.ApiException as MyApiException

class NanaCall(
    private val delegate: Call<Any>,
    private val validator: (NanaException) -> Throwable
) : Call<Any> {
    override fun clone(): Call<Any> = NanaCall(delegate.clone(), validator)

    override fun execute(): Response<Any> {
        throw UnsupportedOperationException()
    }

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(
            object : Callback<Any> {
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful) {
                        callback.onResponse(
                            this@NanaCall,
                            Response.success(response.body())
                        )
                    } else {
                        callback.onFailure(
                            call,
                            validator.invoke(
                                NanaException.ClientException(
                                    response.code(),
                                    response.errorBody()?.string() ?: ""
                                )
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    callback.onFailure(call, validator.invoke(t.toFailure()))
                }
            }
        )
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()


    private fun Throwable.toFailure() =
        when (this) {
            is HttpException -> NanaException.ServerException(this, code())
            is ApiException -> MyApiException(statusCode, message ?: "api exception")
            else -> NanaException.NetworkException(this)
        }

}