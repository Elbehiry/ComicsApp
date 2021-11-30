package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.ConverterFeature
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType

class ConverterIntegration<T : Any> : FeatureIntegration<ConverterFeature<T>, RetrofitConfig> {
    override fun integrate(feature: ConverterFeature<T>, clientConfig: RetrofitConfig) {
//        clientConfig.retrofit.addConverterFactory(object : Converter.Factory() {
//            override fun responseBodyConverter(
//                type: Type,
//                annotations: Array<out Annotation>,
//                retrofit: Retrofit,
//            ): Converter<ResponseBody, *>? {
//
//                val responseType: Type =
//                    TypeToken.getParameterized(feature.classT.genericSuperclass, type).type
//                val delegateConverter: Converter<ResponseBody, T> =
//                    retrofit.nextResponseBodyConverter(this, responseType, annotations)
//
//                Converter<ResponseBody, Any> { body ->
//
//                    val apiResponse: T = delegateConverter.convert(body)!!
//                    feature.convert(apiResponse)
//                }
//                return super.responseBodyConverter(type, annotations, retrofit)
//            }
//
//            override fun requestBodyConverter(
//                type: Type,
//                parameterAnnotations: Array<out Annotation>,
//                methodAnnotations: Array<out Annotation>,
//                retrofit: Retrofit,
//            ): Converter<*, RequestBody>? {
//
//                return super.requestBodyConverter(type,
//                    parameterAnnotations,
//                    methodAnnotations,
//                    retrofit)
//            }
//        })
    }
}

class ClassTypeInfo(
    val type: KClass<*>,
    val reifiedType: Type,
    val kotlinType: KType,
)