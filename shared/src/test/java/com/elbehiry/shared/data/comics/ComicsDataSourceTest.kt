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

import com.elbehiry.shared.data.comics.remote.ComicsDataSource
import com.elbehiry.shared.data.comics.remote.GetComicsRemoteDataSource
import com.elbehiry.shared.data.remote.ComicsApi
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner::class)
class ComicsDataSourceTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    @Mock
    private lateinit var api: ComicsApi
    private lateinit var comicsDataSource: ComicsDataSource

    @Before
    fun setUp() {
        comicsDataSource = GetComicsRemoteDataSource(
            api, mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `test get comics should call api get comics and return data successful`() {
        mainCoroutineRule.runBlockingTest {
            whenever(api.getComic()).thenReturn(COMIC_ITEM)
            val item = comicsDataSource.getComic()
            verify(api).getComic()
            Assert.assertEquals(item, COMIC_ITEM)
        }
    }

    @Test
    fun `test get random comics should call api get random comics and return data successful`() {
        mainCoroutineRule.runBlockingTest {
            whenever(api.getRandomComic(any())).thenReturn(COMIC_ITEM)
            val item = comicsDataSource.getRandomComic(
                faker.number().digits(2).toInt()
            )
            verify(api).getRandomComic(any())
            Assert.assertEquals(item, COMIC_ITEM)
        }
    }
}
