package com.elbehiry.shared.network.callAdapters

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NanaCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        check(returnType is ParameterizedType) {
            ("Call return type must be parameterized"
                    + " as Call<Foo> or Call<? extends Foo>")
        }

        val resultType = getParameterUpperBound(0, returnType)

        val resultRawType = getRawType(resultType)

        if (resultRawType != Result::class.java) {
            return null
        }


        check(resultType is ParameterizedType) {
            ("Result return type must be parameterized"
                    + " as Result<Foo> or Result<? extends Foo>")
        }
        val responseType = getParameterUpperBound(0, resultType)

        return NanaCallAdapter(responseType)
    }
}