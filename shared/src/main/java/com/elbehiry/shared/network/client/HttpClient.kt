package com.elbehiry.shared.network.client

import com.elbehiry.shared.network.request.BaseRequest

abstract class HttpClient<ENGINE> {
    protected abstract var engine: ENGINE
    abstract suspend fun call(request: BaseRequest): String
}