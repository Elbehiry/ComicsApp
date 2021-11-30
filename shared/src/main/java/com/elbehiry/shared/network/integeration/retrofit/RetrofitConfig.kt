package com.elbehiry.shared.network.integeration.retrofit

import com.elbehiry.shared.network.callAdapters.NanaCallAdapterFactory
import com.elbehiry.shared.network.client.ClientConfig
import com.elbehiry.shared.network.converter.StringConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitConfig : ClientConfig<Retrofit> {
    val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    val retrofit: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(
            StringConverterFactory()
        )

    override fun build(): Retrofit {
        return retrofit.client(okHttpClient.build()).build()
    }
}
