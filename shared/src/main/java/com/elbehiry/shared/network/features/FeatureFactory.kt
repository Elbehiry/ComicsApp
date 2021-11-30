package com.elbehiry.shared.network.features

interface FeatureFactory<F : Feature, I : FeatureIntegration<F, *>> {
    val feature: F
    val featureIntegration: I
}
