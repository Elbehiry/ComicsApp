package com.elbehiry.shared.network.integeration.retrofit.features

import com.elbehiry.shared.network.features.CertificatePinner
import com.elbehiry.shared.network.features.FeatureIntegration
import com.elbehiry.shared.network.integeration.retrofit.RetrofitConfig

class CertificateIntegration : FeatureIntegration<CertificatePinner, RetrofitConfig> {

    override fun integrate(feature: CertificatePinner, clientConfig: RetrofitConfig) {
        val certificatePinner = okhttp3.CertificatePinner.Builder()
            .add(feature.host, *feature.certificate.toTypedArray())
            .build()
        clientConfig.okHttpClient.certificatePinner(certificatePinner)
    }
}