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

package com.elbehiry.comicsapp.bookmark

import app.cash.turbine.test
import com.elbehiry.comicsapp.ui.main.bookmark.BookmarkViewModel
import com.elbehiry.shared.domain.bookmark.DeleteComicUseCase
import com.elbehiry.shared.domain.bookmark.GetSavedComicsUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.test_shared.COMICS_ITEMS
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat
import java.lang.Exception

class BookmarkViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var gatSavedComicsUseCase: GetSavedComicsUseCase

    @MockK
    private lateinit var deletedComicsUseCase: DeleteComicUseCase

    private lateinit var bookmarkViewModel: BookmarkViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        gatSavedComicsUseCase = mockk()
        deletedComicsUseCase = mockk()
    }

    @Test
    fun `get saved comic from database should emits ui state`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { gatSavedComicsUseCase(Unit) } returns flowOf(Result.Success(COMICS_ITEMS))

            bookmarkViewModel = BookmarkViewModel(
                gatSavedComicsUseCase,
                deletedComicsUseCase
            )

            bookmarkViewModel.state.test {
                val expectedItem = expectItem()
                assertThat(expectedItem.comics).isEqualTo(COMICS_ITEMS)
                assertThat(expectedItem.isEmpty).isFalse()
            }
        }

    @Test
    fun `empty comics from database should emits ui empty state`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { gatSavedComicsUseCase(Unit) } returns flowOf(Result.Success(emptyList()))

            bookmarkViewModel = BookmarkViewModel(
                gatSavedComicsUseCase,
                deletedComicsUseCase
            )

            bookmarkViewModel.state.test {
                val expectedItem = expectItem()
                assertThat(expectedItem.comics).isEmpty()
                assertThat(expectedItem.isEmpty).isTrue()
            }
        }

    @Test
    fun `failed data from database should emits ui empty state`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { gatSavedComicsUseCase(Unit) } returns flowOf(Result.Error(Exception("")))

            bookmarkViewModel = BookmarkViewModel(
                gatSavedComicsUseCase,
                deletedComicsUseCase
            )

            bookmarkViewModel.state.test {
                val expectedItem = expectItem()
                assertThat(expectedItem.comics).isEmpty()
                assertThat(expectedItem.isEmpty).isTrue()
            }
        }

    @Test
    fun `delete comic should call deletedComicsUseCase`() {
        mainCoroutineRule.runBlockingTest {
            coEvery { gatSavedComicsUseCase(Unit) } returns flowOf(Result.Error(Exception("")))

            bookmarkViewModel = BookmarkViewModel(
                gatSavedComicsUseCase,
                deletedComicsUseCase
            )

            bookmarkViewModel.deleteComic(COMIC_ITEM)
            coVerify { deletedComicsUseCase(COMIC_ITEM.num) }
        }
    }
}
