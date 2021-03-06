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

package com.elbehiry.shared.data.comics.repository

import com.elbehiry.model.Comic
import com.elbehiry.shared.data.comics.remote.ComicsDataSource
import com.elbehiry.shared.data.db.comics.datasource.IComicsLocalDataStore
import com.elbehiry.shared.data.pref.PreferencesKeys
import com.elbehiry.shared.data.pref.repository.DataStoreOperations
import com.elbehiry.shared.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository data source to get comics.
 */
class GetComicsRepository @Inject constructor(
    private val comicsDataSource: ComicsDataSource,
    private val getComicsLocalDataStore: IComicsLocalDataStore,
    private val dataStoreSource: DataStoreOperations
) : ComicsRepository {
    override fun getComic(): Flow<Result<Comic>> {
        return flow {
            val lastComic = comicsDataSource.getComic()
            dataStoreSource.save(PreferencesKeys.mostRecentComicNum, lastComic.num ?: 0)
            emit(Result.Success(lastComic))
        }
    }

    /**
     * Get comic from database if it's exist, if not call the service to get the most recent comic.
     */
    override fun getSpecificComic(comicNum: Int): Flow<Result<Comic>> {
        return flow {
            val isComicSaved = getComicsLocalDataStore.getComicByNum(comicNum)
            if (isComicSaved != null) {
                emit(Result.Success(isComicSaved))
            } else {
                emit(Result.Success(comicsDataSource.getRandomComic(comicNum)))
            }
        }
    }

    override fun getRandomComic(comicNum: Int): Flow<Result<Comic>> = flow {
        emit(Result.Loading)
        emit(Result.Success(comicsDataSource.getRandomComic(comicNum)))
    }
}
