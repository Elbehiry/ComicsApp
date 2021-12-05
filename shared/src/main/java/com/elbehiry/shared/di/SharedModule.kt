/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.elbehiry.shared.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.elbehiry.shared.BuildConfig
import com.elbehiry.shared.data.pref.repository.DataStoreLocalSource
import com.elbehiry.shared.data.pref.repository.DataStoreOperations
import com.elbehiry.shared.network.Network
import com.elbehiry.shared.network.integeration.retrofit.RetrofitClientFactory
import com.elbehiry.shared.network.integeration.retrofit.RetrofitHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val dataStoreName = "ComicsDataStore"

@InstallIn(SingletonComponent::class)
@Module
class SharedModule {

    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ) = context.createDataStore(dataStoreName)

    @Singleton
    @Provides
    fun provideDataStoreSource(dataStore: DataStore<Preferences>): DataStoreOperations =
        DataStoreLocalSource(dataStore)

    @Provides
    fun provideNetwork(
        @ApplicationContext context: Context
    ): RetrofitHttpClient =
        Network.initialize(RetrofitClientFactory(context)) {
            install(RetrofitClientFactory.BaseUrlFactory(BuildConfig.xkcd_BASE_URL))
            install(RetrofitClientFactory.ChuckFactory())
            install(RetrofitClientFactory.TimeOutsFactory()) {
                connectTimeInMills = 5000L
                readTimeInMills = 5000L
                writeTimeInMills = 10000L
            }
            install(RetrofitClientFactory.InterceptionFactory()) {
                onSend = {
                    it.addHeader("Content-Type" to "application/json")
                    it
                }
                onReceive = {
                    it
                }
            }

            install(RetrofitClientFactory.AuthenticatorFactory()) {
                authenticate = {

                    it
                }
            }

            install(RetrofitClientFactory.CertificateFactory()) {
                host = ""
                certificate = emptyList()
            }
        }
}
