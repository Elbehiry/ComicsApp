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

package com.elbehiry.shared.data.db.comics.datastore

import com.elbehiry.model.Comic
import com.elbehiry.shared.data.db.comics.entities.ComicEntity
import com.elbehiry.shared.data.db.comics.mapper.ComicMapper
import com.elbehiry.shared.data.db.comics.tables.ComicsTable
import com.elbehiry.shared.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ComicsLocalDataStore @Inject constructor(
    private val comicsTable: ComicsTable,
    private val comicsMapper: ComicMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : IComicsLocalDataStore {
    override suspend fun saveComic(comicItem: Comic) {
        withContext(ioDispatcher) {
            comicsTable.saveComic(
                comicsMapper.mapToDataBaseComic(comicItem)
            )
        }
    }

    override fun getComics(): Flow<Result<List<Comic>>> =
        comicsTable.getComics().toListDataRecipeFlow()

    override suspend fun getComicByNum(comicNum: Int?): Comic? {
        val savedRecipe = comicsTable.getComic(comicNum)
        return if (savedRecipe != null) {
            comicsMapper.mapToDataComic(savedRecipe)
        } else {
            null
        }
    }

    override suspend fun deleteComic(comicNum: Int?) = comicsTable.deleteRecipe(comicNum)

    override suspend fun isComicSaved(comicNum: Int?): Boolean = comicsTable.isComicSaved(comicNum)

    override suspend fun searchComic(query: String): Comic? {
        val comicEntity = comicsTable.searchComicByQuery(query)
        return if (comicEntity != null) {
            comicsMapper.mapToDataComic(comicEntity)
        } else {
            null
        }
    }

    private fun Flow<List<ComicEntity>>.toListDataRecipeFlow(): Flow<Result<List<Comic>>> {
        return this.map { items ->
            Result.Success(items.toDataComics())
        }
    }

    private fun List<ComicEntity>.toDataComics(): List<Comic> {
        return this.map {
            comicsMapper.mapToDataComic(it)
        }
    }
}
