package com.elbehiry.shared.network

import com.elbehiry.shared.network.client.ClientConfig
import com.elbehiry.shared.network.client.HttpClient
import com.elbehiry.shared.network.integeration.Factory

class Network {
    companion object {
        inline fun <reified E, reified C : HttpClient<E>> initialize(
            factory: Factory<E, C>,
            configBuilder: ClientConfig<E>.() -> Unit,
        ): C {
            val engine = factory.config.apply(configBuilder).build()
            return factory.create(engine)
        }
    }
}
