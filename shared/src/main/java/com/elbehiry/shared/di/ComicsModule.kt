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

import com.elbehiry.shared.data.comics.remote.ComicsDataSource
import com.elbehiry.shared.data.comics.remote.GetComicsRemoteDataSource
import com.elbehiry.shared.data.comics.repository.ComicsRepository
import com.elbehiry.shared.data.comics.repository.GetComicsRepository
import com.elbehiry.shared.data.db.comics.datasource.IComicsLocalDataStore
import com.elbehiry.shared.data.pref.repository.DataStoreOperations
import com.elbehiry.shared.data.remote.ComicsApi
import com.elbehiry.shared.network.integeration.retrofit.RetrofitHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ComicsModule {
    @Provides
    fun provideComicsDataSource(api: RetrofitHttpClient): ComicsDataSource =
        GetComicsRemoteDataSource(api)

    @Provides
    fun provideRandomRecipesRepository(
        comicsDataSource: ComicsDataSource,
        getComicsLocalDataStore: IComicsLocalDataStore,
        dataStoreRepository: DataStoreOperations
    ): ComicsRepository =
        GetComicsRepository(comicsDataSource, getComicsLocalDataStore, dataStoreRepository)
}
