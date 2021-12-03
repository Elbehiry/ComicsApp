package com.elbehiry.shared.network.features

import com.elbehiry.shared.network.response.IResponse

class AuthenticatorFeature(var authenticate: (response: IResponse) -> IResponse = { it }) : Feature
