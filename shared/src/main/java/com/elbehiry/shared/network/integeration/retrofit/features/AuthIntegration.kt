package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.Authenticator
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig

class AuthIntegration : FeatureIntegration<Authenticator, RetrofitConfig> {
    override fun integrate(feature: Authenticator, clientConfig: RetrofitConfig) {
        clientConfig.okHttpClient.addInterceptor {
            val request = it.request()
            feature.authenticate
            val response = it.proceed(request)
            response
        }
    }
}