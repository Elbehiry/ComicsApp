package com.elbehiry.shared.network.integeration.retrofit

import android.content.Context
import com.elbehiry.shared.network.client.ClientConfig
import com.elbehiry.shared.network.converter.StringConverterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofitConfig : ClientConfig<Retrofit> {
    lateinit var context: Context
    val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate?>?,
                                                authType: String?) = Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate?>?,
                                                authType: String?) = Unit

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )
        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        sslSocketFactory(sslSocketFactory,
            trustAllCerts[0] as X509TrustManager
        )
        hostnameVerifier { _, _ -> true }
    }
    val retrofit: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(
            StringConverterFactory()
        ).addConverterFactory(Json.asConverterFactory(MediaType.parse("application/json; charset=utf-8")!!))

    override fun build(): Retrofit {
        return retrofit.client(okHttpClient.build()).build()
    }
}
