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

package com.elbehiry.shared.data.comics

import app.cash.turbine.test
import com.elbehiry.shared.data.comics.remote.ComicsDataSource
import com.elbehiry.shared.data.comics.repository.ComicsRepository
import com.elbehiry.shared.data.comics.repository.GetComicsRepository
import com.elbehiry.shared.data.db.comics.datasource.IComicsLocalDataStore
import com.elbehiry.shared.data.pref.repository.DataStoreOperations
import com.elbehiry.shared.result.data
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.whenever
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ComicsRepositoryTest {
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var comicsDataSource: ComicsDataSource

    @Mock
    private lateinit var getComicsLocalDataStore: IComicsLocalDataStore

    @Mock
    private lateinit var dataStoreSource: DataStoreOperations
    private lateinit var comicsRepository: ComicsRepository

    @Before
    fun setup() {
        comicsRepository =
            GetComicsRepository(comicsDataSource, getComicsLocalDataStore, dataStoreSource)
    }

    @Test
    fun `test get comics should call data source get comics and return successful`() {
        coroutineRule.runBlockingTest {
            doAnswer {  }.`when`(dataStoreSource).save(any(), any())
            whenever(comicsDataSource.getComic()).thenReturn(COMIC_ITEM)
            comicsRepository.getComic().test {
                Assert.assertEquals(expectItem().data, COMIC_ITEM)
                expectComplete()
            }
            Mockito.verify(comicsDataSource).getComic()
        }
    }

    @Test
    fun `test get specific comics should get data from data base if item is saved`() {
        coroutineRule.runBlockingTest {
            whenever(getComicsLocalDataStore.getComicByNum(any())).thenReturn(COMIC_ITEM)
            comicsRepository.getSpecificComic(
                faker.number().digits(2).toInt()
            ).test {
                Assert.assertEquals(expectItem().data, COMIC_ITEM)
                expectComplete()
            }
            Mockito.verify(getComicsLocalDataStore).getComicByNum(any())
        }
    }

    @Test
    fun `test get specific comics should call the service if comic not saved from the database`() {
        coroutineRule.runBlockingTest {
            whenever(getComicsLocalDataStore.getComicByNum(any())).thenReturn(null)
            whenever(comicsDataSource.getRandomComic(any())).thenReturn(COMIC_ITEM)
            comicsRepository.getSpecificComic(
                faker.number().digits(2).toInt()
            ).test {
                Assert.assertEquals(expectItem().data, COMIC_ITEM)
                expectComplete()
            }
            Mockito.verify(comicsDataSource).getRandomComic(any())
        }
    }
}
