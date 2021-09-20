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

package com.elbehiry.comicsapp.details

import app.cash.turbine.test
import com.elbehiry.comicsapp.ui.details.ComicDetailsViewModel
import com.elbehiry.shared.domain.bookmark.ToggleSavedComicUseCase
import com.elbehiry.shared.domain.browse.GetComicDetailsUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

class ComicDetailsViewModelTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var getComicDetailsUseCase: GetComicDetailsUseCase

    @MockK
    private lateinit var toggleSavedComicUseCase: ToggleSavedComicUseCase
    private lateinit var comicDetailsViewModel: ComicDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        toggleSavedComicUseCase = mockk()

        comicDetailsViewModel = ComicDetailsViewModel(
            getComicDetailsUseCase,
            toggleSavedComicUseCase
        )
    }

    @Test
    fun `get comic details emits ui states successfully`() {
        mainCoroutineRule.runBlockingTest {
            val comicNum = faker.number().randomNumber().toInt()
            val comic = COMIC_ITEM.copy(
                num = comicNum
            )
            coEvery { getComicDetailsUseCase(any()) } returns flowOf(
                Result.Success(comic)
            )

            comicDetailsViewModel.getComicDetails(comicNum)
            comicDetailsViewModel.comicDetails.test {
                val expectItem = expectItem()
                assertThat(expectItem?.num).isEqualTo(comicNum)
            }
            comicDetailsViewModel.errorMessage.test {
                assertThat(expectItem()).isEmpty()
            }
        }
    }

    @Test
    fun `get comic details with error emits error message to errorMessage flow`() {
        mainCoroutineRule.runBlockingTest {
            val errorMessage = faker.lorem().sentence(3)
            val comicNum = faker.number().randomNumber().toInt()
            coEvery { getComicDetailsUseCase(any()) } returns
                flowOf(Result.Error(Exception(errorMessage)))
            comicDetailsViewModel.getComicDetails(comicNum)
            comicDetailsViewModel.errorMessage.test {
                assertThat(expectItem()).isEqualTo(errorMessage)
            }
        }
    }

    @Test
    fun `save comic should call toggleSavedComicUseCase`() {
        mainCoroutineRule.runBlockingTest {
            comicDetailsViewModel.onBookMark(mockk())
            coVerify { toggleSavedComicUseCase(any()) }
        }
    }
}
