package com.elbehiry.shared.network.features

class TimeOuts(
    var connectTimeInMills: Long = 0L,
    var readTimeInMills: Long = 0L,
    var writeTimeInMills: Long = 0L,
) : Feature