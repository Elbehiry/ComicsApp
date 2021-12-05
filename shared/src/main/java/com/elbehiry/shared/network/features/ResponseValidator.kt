package com.elbehiry.shared.network.features

import java.io.IOException

sealed class NanaException : IOException() {
    class ServerException(override val cause: Throwable?, val code: Int? = null) : NanaException()
    class NetworkException(override val cause: Throwable?) : NanaException()
    class ClientException(val code: Int, override val message: String) : NanaException()
    class ApiException(val code: Int, override val message: String) : NanaException()
}

class ResponseValidator(var validator: (NanaException) -> Throwable = { it }) : Feature
