package com.elbehiry.shared.network.callAdapters

import com.elbehiry.shared.network.features.NanaException
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NanaCallAdapter(
    private val type: Type,
    private val validator: (NanaException) -> Throwable
) : CallAdapter<Any, Call<Any>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<Any>): Call<Any> {
        return NanaCall(call,validator)
    }
}