package com.elbehiry.shared.network.features

import com.elbehiry.shared.network.client.ClientConfig

interface FeatureIntegration<F : Feature, C : ClientConfig<*>> {
    fun integrate(feature: F, clientConfig: C)
}
