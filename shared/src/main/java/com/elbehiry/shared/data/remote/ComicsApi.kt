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

package com.elbehiry.shared.data.remote

import com.elbehiry.model.Comic
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicsApi {
    @GET("info.0.json")
    suspend fun getComic(): Comic

    @GET("{comic_id}/info.0.json")
    suspend fun getRandomComic(@Path("comic_id")comicId: Int): Comic
}
