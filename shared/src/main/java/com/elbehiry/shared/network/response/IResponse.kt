package com.elbehiry.shared.network.response

import com.elbehiry.shared.network.request.IRequest

interface IResponse {
    fun request(): IRequest
    fun protocol(): String
    fun code(): Int
    fun message(): String?
    fun headers(): Map<String, List<String>>
    fun body(): String?
}