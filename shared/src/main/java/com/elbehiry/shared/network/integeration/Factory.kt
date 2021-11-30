package com.elbehiry.shared.network.integeration

import com.elbehiry.shared.network.client.HttpClient
import com.elbehiry.shared.network.client.ClientConfig


interface Factory<E, C : HttpClient<E>> {
    val config: ClientConfig<E>

    fun create(engine: E): C
}
