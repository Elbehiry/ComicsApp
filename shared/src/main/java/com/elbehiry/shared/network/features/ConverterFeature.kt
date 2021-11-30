package com.elbehiry.shared.network.features

class ConverterFeature<T : Any>(val classT: Class<T>) : Feature {
    var convert: (d: T) -> Any = { it }
}
