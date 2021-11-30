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
import com.elbehiry.shared.network.integeration.retrofit.RetrofitHttpClient
import com.elbehiry.shared.network.request.get
import com.elbehiry.shared.network.request.post
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import javax.inject.Inject

/**
 * Remote data source to get comics.
 */
@Serializable
data class D(val id:Int)
class GetComicsRemoteDataSource @Inject constructor(
    private val client: RetrofitHttpClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ComicsDataSource {
    override suspend fun getComic(): Result<Comic> = withContext(ioDispatcher) {
        val res2:Result<Comic> = client.get("https://dindinntask.getsandbox.com/orders"){
            headers = mapOf("application/json" to "Content-Type")
        }

        val res:Result<D> = client.post("https://dindinntask.getsandbox.com/test_post",D(1)){
            headers = mapOf("application/json" to "Content-Type")

        }
        client.get(
            url = "info.0.json"
        ) {

        }
    }

    override suspend fun getRandomComic(comicNum: Int): Result<Comic> = withContext(ioDispatcher) {
        client.get(
            url = "${Result}/info.0.json"
        ) {
        }
    }
}
