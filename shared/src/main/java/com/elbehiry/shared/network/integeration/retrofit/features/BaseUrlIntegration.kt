package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.BaseUrl
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig

class BaseUrlIntegration : FeatureIntegration<BaseUrl, RetrofitConfig> {

    override fun integrate(feature: BaseUrl, clientConfig: RetrofitConfig) {
        clientConfig.retrofit.baseUrl(feature.url)
    }
}