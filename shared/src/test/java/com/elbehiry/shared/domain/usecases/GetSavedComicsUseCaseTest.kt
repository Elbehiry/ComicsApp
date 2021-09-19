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

import app.cash.turbine.test
import com.elbehiry.shared.data.db.comics.datasource.IComicsLocalDataStore
import com.elbehiry.shared.domain.bookmark.GetSavedComicsUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.shared.result.data
import com.elbehiry.test_shared.COMICS_ITEMS
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.runBlockingTest
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
class GetSavedComicsUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var dataStore: IComicsLocalDataStore
    private lateinit var getSavedComicsUseCase: GetSavedComicsUseCase

    @Before
    fun setUp() {
        getSavedComicsUseCase = GetSavedComicsUseCase(
            dataStore, mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `get saved comics returns data as Result_Success value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(dataStore.getComics()).thenReturn(
                flowOf(Result.Success(COMICS_ITEMS))
            )
            getSavedComicsUseCase(Unit).test {
                Assertions.assertThat(expectItem() is Result.Success)
                expectComplete()
            }
        }

    @Test
    fun `get saved comics returns data as Result_Success value with expected item value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(dataStore.getComics()).thenReturn(
                flowOf(Result.Success(COMICS_ITEMS))
            )
            getSavedComicsUseCase(Unit).test {
                Assert.assertEquals(expectItem().data, COMICS_ITEMS)
                expectComplete()
            }
        }

    @Test
    fun `get saved comics fails should returns data as Result_Error value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(dataStore.getComics()).thenReturn(
                flowOf(Result.Error(Exception("No Data Base")))
            )

            getSavedComicsUseCase(Unit).test {
                Assertions.assertThat(expectItem() is Result.Error)
                expectComplete()
            }
        }

    @Test
    fun `get comics fails should returns data as Result_Error message value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(dataStore.getComics()).thenReturn(
                flowOf(Result.Error(RuntimeException("No Data Base")))
            )

            getSavedComicsUseCase(Unit).test {
                Assertions.assertThat(
                    (expectItem() as Result.Error).exception is RuntimeException
                )
                expectComplete()
            }
        }
}
