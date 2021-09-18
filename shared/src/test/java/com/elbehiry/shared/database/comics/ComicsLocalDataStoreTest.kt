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

package com.elbehiry.shared.database.comics

import app.cash.turbine.test
import com.elbehiry.shared.data.db.comics.datastore.ComicsLocalDataStore
import com.elbehiry.shared.data.db.comics.datastore.IComicsLocalDataStore
import com.elbehiry.shared.data.db.comics.mapper.ComicMapper
import com.elbehiry.shared.data.db.comics.mapper.ComicMapperImpl
import com.elbehiry.shared.data.db.comics.tables.ComicsTable
import com.elbehiry.shared.result.data
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ComicsLocalDataStoreTest {

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var comicsTable: ComicsTable
    private lateinit var comicsDataSource: IComicsLocalDataStore
    private lateinit var mapper: ComicMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mapper = ComicMapperImpl()
        comicsDataSource = ComicsLocalDataStore(
            comicsTable,
            mapper,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun save_comic_to_table_successfully() = coroutineRule.runBlockingTest {
        comicsDataSource.saveComic(COMIC_ITEM)
        coEvery {
            comicsTable.saveComic(mapper.mapToDataBaseComic(COMIC_ITEM))
        }
    }

    @Test
    fun get_comics_from_table_successfully() = coroutineRule.runBlockingTest {
        comicsDataSource.saveComic(COMIC_ITEM)
        coEvery { comicsTable.getComics() } returns
            flowOf(listOf(mapper.mapToDataBaseComic(COMIC_ITEM)))
        comicsDataSource.getComics().test {
            assertThat(expectItem().data?.size).isNotZero
            expectComplete()
        }
    }

    @Test
    fun emits_comics_successfully() = coroutineRule.runBlockingTest {
        val num = faker.number().digit().toInt()
        val currentItem = COMIC_ITEM.copy(
            num = num
        )
        val recipes = listOf(mapper.mapToDataBaseComic(currentItem))
        coEvery { comicsTable.getComics() } returns flowOf(recipes)
        comicsDataSource.getComics().test {
            assertThat(expectItem().data?.get(0)?.num).isEqualTo(num)
            expectComplete()
        }
    }
}
