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

package com.elbehiry.shared.domain.usecases

import android.accounts.NetworkErrorException
import app.cash.turbine.test
import com.elbehiry.shared.data.comics.repository.ComicsRepository
import com.elbehiry.shared.domain.browse.GetComicByIdUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.shared.result.data
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.flow.flowOf
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetComicByIdUseCaseTest {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var comicsRepository: ComicsRepository
    private lateinit var getComicByIdUseCase: GetComicByIdUseCase

    @Before
    fun setUp() {
        getComicByIdUseCase = GetComicByIdUseCase(
            comicsRepository, mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `get random comics returns data as Result_Success value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(comicsRepository.getRandomComic(any())).thenReturn(
                flowOf(
                    Result.Success(COMIC_ITEM)
                )
            )
            getComicByIdUseCase(createDummyParams()).test {
                Assertions.assertThat(expectItem() is Result.Success)
                expectComplete()
            }
        }

    @Test
    fun `get random comics returns data as Result_Success value with expected item value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(comicsRepository.getRandomComic(any())).thenReturn(
                flowOf(
                    Result.Success(COMIC_ITEM)
                )
            )
            getComicByIdUseCase(createDummyParams()).test {
                Assert.assertEquals(expectItem().data, COMIC_ITEM)
                expectComplete()
            }
        }

    @Test
    fun `get random comics fails should returns data as Result_Error value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(comicsRepository.getRandomComic(any())).thenReturn(
                flowOf(Result.Error(NetworkErrorException("Network Failure")))
            )

            getComicByIdUseCase(createDummyParams()).test {
                Assertions.assertThat(expectItem() is Result.Error)
                expectComplete()
            }
        }

    @Test
    fun `get random comics fails should returns data as Result_Error message value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(comicsRepository.getRandomComic(any())).thenReturn(
                flowOf(Result.Error(NetworkErrorException("Network Failure")))
            )

            getComicByIdUseCase(createDummyParams()).test {
                Assertions.assertThat(
                    (expectItem() as Result.Error).exception is NetworkErrorException
                )
                expectComplete()
            }
        }

    private fun createDummyParams() = GetComicByIdUseCase.Params.create(
        faker.number().digits(2).toInt()
    )
}
