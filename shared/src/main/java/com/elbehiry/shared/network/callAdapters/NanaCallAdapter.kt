package com.elbehiry.shared.network.callAdapters

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NanaCallAdapter(
    private val type: Type
) : CallAdapter<Any, Call<String>> {
    override fun responseType(): Type = type

    override fun adapt(call: Call<Any>): Call<String> {
        return NanaCall(call)
    }
}