package com.elbehiry.shared.network.converter

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class StringConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return StringResponseBodyConverter()
    }

    class StringResponseBodyConverter : Converter<ResponseBody, String> {
        override fun convert(value: ResponseBody): String? {
            return value.string()
        }
    }
}