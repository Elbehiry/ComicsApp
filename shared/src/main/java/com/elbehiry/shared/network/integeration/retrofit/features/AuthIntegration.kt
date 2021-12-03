package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.AuthenticatorFeature
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig
import com.elbehiry.shared.network.request.RetrofitRequest
import com.elbehiry.shared.network.response.RetrofitResponse
import java.io.IOException

class AuthIntegration : FeatureIntegration<AuthenticatorFeature, RetrofitConfig> {
    override fun integrate(feature: AuthenticatorFeature, clientConfig: RetrofitConfig) {
        clientConfig.okHttpClient.authenticator { _, response ->
            val retrofitResponse = feature.authenticate(RetrofitResponse(response))
            val retrofitRequest = retrofitResponse.request()
            if (retrofitRequest is RetrofitRequest) {
                retrofitRequest.request
            } else {
                throw IOException("request must be RetrofitRequest")
            }
        }
    }
}