package com.elbehiry.shared.network.integeration.retrofit

import android.content.Context
import com.elbehiry.shared.network.client.ClientConfig
import com.elbehiry.shared.network.features.*
import com.elbehiry.shared.network.integeration.Factory
import com.elbehiry.shared.network.integeration.retrofit.features.*
import retrofit2.Retrofit

class RetrofitClientFactory(val appContext: Context) : Factory<Retrofit, RetrofitHttpClient> {

    override val config: ClientConfig<Retrofit>
        get() = RetrofitConfig().apply { context = appContext }

    override fun create(engine: Retrofit): RetrofitHttpClient =
        RetrofitHttpClient(engine)

    class BaseUrlFactory(
        baseUrl: String,
        override val feature: BaseUrl = BaseUrl(baseUrl),
        override val featureIntegration: BaseUrlIntegration = BaseUrlIntegration(),
    ) : FeatureFactory<BaseUrl, BaseUrlIntegration>

    class TimeOutsFactory(
        var connectTimeInMills: Long = 1500L,
        var readTimeInMills: Long = 1500L,
        var writeTimeInMills: Long = 1500L,
        override val feature: TimeOuts = TimeOuts(
            connectTimeInMills = connectTimeInMills,
            readTimeInMills = readTimeInMills,
            writeTimeInMills = writeTimeInMills
        ),
        override val featureIntegration: TimeOutsIntegration = TimeOutsIntegration()
    ) : FeatureFactory<TimeOuts, TimeOutsIntegration>

    class CertificateFactory(
        override val feature: CertificatePinner = CertificatePinner(),
        override val featureIntegration: CertificateIntegration = CertificateIntegration(),
    ) : FeatureFactory<CertificatePinner, CertificateIntegration>

    class ChuckFactory(
        override val feature: Chuck = Chuck(),
        override val featureIntegration: ChuckIntegration = ChuckIntegration(),
    ) : FeatureFactory<Chuck, ChuckIntegration>

    class InterceptionFactory(
        override val feature: Interception = Interception(),
        override val featureIntegration: InterceptionIntegration = InterceptionIntegration(),
    ) : FeatureFactory<Interception, InterceptionIntegration>

    class AuthenticatorFactory(
        override val feature: AuthenticatorFeature = AuthenticatorFeature(),
        override val featureIntegration: AuthIntegration = AuthIntegration(),
    ) : FeatureFactory<AuthenticatorFeature, AuthIntegration>

    class ResponseValidatorFactory(
        override val feature: ResponseValidator = ResponseValidator(),
        override val featureIntegration: ResponseValidatorIntegration = ResponseValidatorIntegration(),
    ) : FeatureFactory<ResponseValidator, ResponseValidatorIntegration>
}