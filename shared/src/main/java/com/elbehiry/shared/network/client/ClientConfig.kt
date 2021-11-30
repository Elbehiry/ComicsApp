package com.elbehiry.shared.network.client

import com.elbehiry.shared.network.features.Feature
import com.elbehiry.shared.network.features.FeatureFactory
import com.elbehiry.shared.network.features.FeatureIntegration

interface ClientConfig<ENGINE> {
    fun <F : Feature, I : FeatureIntegration<F, C>, C : ClientConfig<ENGINE>, FF : FeatureFactory<F, I>> install(
        feature: FF,
        featureBuilder: F.() -> Unit = {},
    ) {
        feature.feature.apply(featureBuilder).let {
            feature.featureIntegration.integrate(feature.feature, this as C)
        }
    }

    fun build(): ENGINE
}

