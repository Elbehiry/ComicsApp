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

package com.elbehiry.comicsapp.search

import app.cash.turbine.test
import com.elbehiry.comicsapp.ui.search.SearchComicViewModel
import com.elbehiry.shared.domain.bookmark.SearchComicsUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchComicViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var searchComicsUseCase: SearchComicsUseCase

    private lateinit var searchComicViewModel: SearchComicViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { searchComicsUseCase(any()) } returns Result.Success(COMIC_ITEM)
        searchComicViewModel = SearchComicViewModel(searchComicsUseCase)
    }

    @Test
    fun `search for quote should emits ui state success`() = mainCoroutineRule.runBlockingTest {
        val query = faker.lorem().sentence()
        searchComicViewModel.search(query)
        searchComicViewModel.comic.test {
            Assert.assertEquals(expectItem(), COMIC_ITEM)
        }
    }

    @Test
    fun `search comics emits loading state and remove it after data returned`() =
        mainCoroutineRule.runBlockingTest {
            searchComicViewModel.loading.test {
                Assert.assertFalse(expectItem())
            }
            val query = faker.lorem().sentence()
            searchComicViewModel.search(query)
            searchComicViewModel.loading.test {
                Assert.assertFalse(expectItem())
            }
        }
}
