package com.elbehiry.shared.network.features

import com.elbehiry.shared.network.features.Feature

class ResponseValidator(var validator: (Throwable) -> Throwable = { it }) : Feature
