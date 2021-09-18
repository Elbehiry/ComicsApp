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
import com.elbehiry.shared.data.db.comics.datastore.IComicsLocalDataStore
import com.elbehiry.shared.domain.bookmark.GetComicsByNumLocallyUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.shared.result.data
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.faker
import com.elbehiry.test_shared.runBlockingTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetComicsByNumLocallyUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var dataStore: IComicsLocalDataStore
    private lateinit var getComicsByNumLocallyUseCase: GetComicsByNumLocallyUseCase

    @Before
    fun setUp() {
        getComicsByNumLocallyUseCase = GetComicsByNumLocallyUseCase(
            dataStore, mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `get comic by num returns data as Result_Success value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(dataStore.getComicByNum(any())).thenReturn(
                COMIC_ITEM
            )
            val num = faker.number().randomNumber().toInt()
            val result = getComicsByNumLocallyUseCase(num)
            Assertions.assertThat(result is Result.Success)
        }

    @Test
    fun `get comic by num returns data as Result_Success value with expected item value`() =
        mainCoroutineRule.runBlockingTest {
            whenever(dataStore.getComicByNum(any())).thenReturn(
                COMIC_ITEM
            )
            val num = faker.number().randomNumber().toInt()
            val result = getComicsByNumLocallyUseCase(num)
            Assert.assertEquals(result.data, COMIC_ITEM)
        }

    @Test
    fun `get comic by num fails should returns data as Result_Error value`() =
        mainCoroutineRule.runBlockingTest {
            given(dataStore.getComicByNum(any())).willAnswer {
                NetworkErrorException("Network Failure")
            }

            val num = faker.number().randomNumber().toInt()
            val result = getComicsByNumLocallyUseCase(num)
            Assertions.assertThat(result is Result.Error)
        }
}
