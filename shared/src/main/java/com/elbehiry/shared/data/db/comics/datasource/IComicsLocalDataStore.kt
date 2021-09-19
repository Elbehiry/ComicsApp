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

package com.elbehiry.shared.data.db.comics.datasource

import com.elbehiry.model.Comic
import com.elbehiry.shared.result.Result
import kotlinx.coroutines.flow.Flow

interface IComicsLocalDataStore {
    suspend fun saveComic(comicItem: Comic)
    fun getComics(): Flow<Result<List<Comic>>>
    suspend fun getComicByNum(comicNum: Int?): Comic?
    suspend fun deleteComic(comicNum: Int?)
    suspend fun isComicSaved(comicNum: Int?): Boolean
    suspend fun searchComic(query: String): Comic?
    suspend fun toggleSavedComic(comic: Comic)
}
