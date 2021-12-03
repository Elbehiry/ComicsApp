package com.elbehiry.shared.network.features

class ResponseValidator(var validator: (Throwable) -> Throwable = { it }) : Feature
