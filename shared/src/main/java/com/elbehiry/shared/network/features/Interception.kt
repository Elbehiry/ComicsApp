package com.elbehiry.shared.network.features

import com.elbehiry.shared.network.request.IRequest
import com.elbehiry.shared.network.response.IResponse

class Interception : Feature {
    var onSend: (IRequest) -> IRequest = { it }
    var onReceive: (IResponse) -> IResponse = { it }
}