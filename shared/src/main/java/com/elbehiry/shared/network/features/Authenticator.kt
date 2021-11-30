package com.elbehiry.shared.network.features

import com.elbehiry.shared.network.response.IResponse

class Authenticator(var authenticate: (response: IResponse) -> IResponse = { it }) : Feature
