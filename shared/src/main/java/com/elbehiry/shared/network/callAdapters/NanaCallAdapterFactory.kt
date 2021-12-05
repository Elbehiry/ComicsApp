package com.elbehiry.shared.network.callAdapters

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NanaCallAdapterFactory(private val validator: (NanaException) -> Throwable) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        return NanaCallAdapter(returnType,validator)
    }
}