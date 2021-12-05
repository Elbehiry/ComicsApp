package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.callAdapters.NanaCallAdapterFactory
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.features.ResponseValidator
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig
import java.io.IOException

class ResponseValidatorIntegration : FeatureIntegration<ResponseValidator, RetrofitConfig> {

    override fun integrate(feature: ResponseValidator, clientConfig: RetrofitConfig) {
        clientConfig.retrofit.addCallAdapterFactory(NanaCallAdapterFactory(feature.validator))
    }
}