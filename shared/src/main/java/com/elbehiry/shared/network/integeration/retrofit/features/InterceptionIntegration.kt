package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.features.Interception
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig
import com.elbehiry.shared.network.request.RetrofitRequest
import com.elbehiry.shared.network.response.RetrofitResponse

class InterceptionIntegration : FeatureIntegration<Interception, RetrofitConfig> {
    override fun integrate(feature: Interception, clientConfig: RetrofitConfig) {
        clientConfig.okHttpClient.addInterceptor {
            val request = it.request()
            feature.onSend(RetrofitRequest(request))
            val response = it.proceed(request)
            feature.onReceive(RetrofitResponse(response))
            response
        }
    }
}