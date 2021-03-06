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

package com.elbehiry.shared.data.comics.remote

import com.elbehiry.model.Comic
import com.elbehiry.shared.data.remote.ComicsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Remote data source to get comics.
 */
class GetComicsRemoteDataSource @Inject constructor(
    private val api: ComicsApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ComicsDataSource {
    override suspend fun getComic(): Comic = withContext(ioDispatcher) {
        api.getComic()
    }

    override suspend fun getRandomComic(comicNum: Int): Comic = withContext(ioDispatcher) {
        api.getRandomComic(comicNum)
    }
}
