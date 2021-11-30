package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.features.TimeOuts
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig
import java.util.concurrent.TimeUnit

class TimeOutsIntegration : FeatureIntegration<TimeOuts, RetrofitConfig> {

    override fun integrate(feature: TimeOuts, clientConfig: RetrofitConfig) {
        clientConfig.okHttpClient
            .readTimeout(feature.readTimeInMills, TimeUnit.MILLISECONDS)
            .writeTimeout(feature.writeTimeInMills, TimeUnit.MILLISECONDS)
            .connectTimeout(feature.connectTimeInMills, TimeUnit.MILLISECONDS)
    }
}