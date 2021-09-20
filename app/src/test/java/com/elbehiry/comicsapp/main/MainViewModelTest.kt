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

package com.elbehiry.comicsapp.main

import app.cash.turbine.test
import com.elbehiry.comicsapp.ui.main.MainViewModel
import com.elbehiry.shared.domain.browse.GetComicUseCase
import com.elbehiry.shared.domain.browse.GetRandomComicUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class MainViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getComicUseCase: GetComicUseCase

    @MockK
    private lateinit var getRandomComicUseCase: GetRandomComicUseCase
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `get last comic emits loading state successfully`() = mainCoroutineRule.runBlockingTest {
        coEvery { getComicUseCase(Unit) } returns flowOf(
            Result.Success(COMIC_ITEM)
        )
        coEvery { getRandomComicUseCase(any()) } returns flowOf(
            Result.Success(COMIC_ITEM)
        )

        mainViewModel = MainViewModel(
            getComicUseCase,
            getRandomComicUseCase
        )
        mainViewModel.isLoading.test {
            assertThat(expectItem()).isNotNull()
        }
    }

    @Test
    fun `get last comic emits comic state successfully`() = mainCoroutineRule.runBlockingTest {
        coEvery { getComicUseCase(Unit) } returns flowOf(
            Result.Success(COMIC_ITEM)
        )
        coEvery { getRandomComicUseCase(any()) } returns flowOf(
            Result.Success(COMIC_ITEM)
        )

        mainViewModel = MainViewModel(
            getComicUseCase,
            getRandomComicUseCase
        )
        mainViewModel.comic.test {
            assertThat(expectItem()).isEqualTo(COMIC_ITEM)
        }
    }

    @Test
    fun `get random comic emits comic state successfully`() = mainCoroutineRule.runBlockingTest {
        val num = faker.number().randomNumber().toInt()

        coEvery { getComicUseCase(Unit) } returns flowOf(
            Result.Success(COMIC_ITEM)
        )
        coEvery { getRandomComicUseCase(any()) } returns flowOf(
            Result.Success(
                COMIC_ITEM.copy(
                    num = num
                )
            )
        )

        mainViewModel = MainViewModel(
            getComicUseCase,
            getRandomComicUseCase
        )
        mainViewModel.getRandomComic(num)
        mainViewModel.comic.test {
            assertThat(expectItem()?.num).isEqualTo(num)
        }
    }

    @Test
    fun `get last comic successfully with empty error message and false has error`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { getComicUseCase(Unit) } returns flowOf(
                Result.Success(COMIC_ITEM)
            )
            coEvery { getRandomComicUseCase(any()) } returns flowOf(
                Result.Success(COMIC_ITEM)
            )

            mainViewModel = MainViewModel(
                getComicUseCase,
                getRandomComicUseCase
            )
            mainViewModel.errorMessage.test {
                assertThat(expectItem()).isEmpty()
            }

            mainViewModel.hasError.test {
                assertThat(expectItem()).isFalse()
            }
        }

    @Test
    fun `get last comic failed with right error message and true has error`() =
        mainCoroutineRule.runBlockingTest {
            val errorMessage = faker.lorem().sentence(3)

            coEvery { getComicUseCase(Unit) } returns
                flowOf(Result.Error(Exception(errorMessage)))

            coEvery { getRandomComicUseCase(any()) } returns flowOf(
                Result.Success(COMIC_ITEM)
            )

            mainViewModel = MainViewModel(
                getComicUseCase,
                getRandomComicUseCase
            )
            mainViewModel.errorMessage.test {
                assertThat(expectItem()).isEqualTo(errorMessage)
            }

            mainViewModel.hasError.test {
                assertThat(expectItem()).isTrue()
            }
        }

    @Test
    fun `save random comic should call getComicUseCase with right num`() {
        mainCoroutineRule.runBlockingTest {
            val num = faker.number().randomNumber().toInt()

            coEvery { getComicUseCase(Unit) } returns flowOf(
                Result.Success(COMIC_ITEM)
            )
            coEvery { getRandomComicUseCase(any()) } returns flowOf(
                Result.Success(COMIC_ITEM)
            )

            mainViewModel = MainViewModel(
                getComicUseCase,
                getRandomComicUseCase
            )
            mainViewModel.getRandomComic(num)
            coVerify { getRandomComicUseCase(any()) }
        }
    }
}
