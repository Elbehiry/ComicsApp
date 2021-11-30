package com.elbehiry.shared.network.integeration.retrofit.features

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.elbehiry.shared.network.features.Chuck
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig

class ChuckIntegration : FeatureIntegration<Chuck, RetrofitConfig> {
    override fun integrate(feature: Chuck, clientConfig: RetrofitConfig) {
        clientConfig.okHttpClient.addInterceptor(
            ChuckerInterceptor.Builder(clientConfig.context).build()
        )
    }
}
