package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.AuthenticatorFeature
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig
import com.elbehiry.shared.network.request.RetrofitRequest
import com.elbehiry.shared.network.response.RetrofitResponse

class AuthIntegration : FeatureIntegration<AuthenticatorFeature, RetrofitConfig> {
    override fun integrate(feature: AuthenticatorFeature, clientConfig: RetrofitConfig) {
        clientConfig.okHttpClient.authenticator { _, response ->
            val retrofitResponse = feature.authenticate(RetrofitResponse(response))
            val retrofitRequest = retrofitResponse.request() as RetrofitRequest
            retrofitRequest.request
        }
    }
}