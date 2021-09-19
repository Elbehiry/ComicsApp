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

import com.elbehiry.shared.data.db.comics.datasource.IComicsLocalDataStore
import com.elbehiry.shared.domain.bookmark.ToggleSavedComicUseCase
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ToggleSavedComicUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var dataStore: IComicsLocalDataStore
    private lateinit var toggleSavedComicUseCase: ToggleSavedComicUseCase

    @Before
    fun setUp() {
        toggleSavedComicUseCase = ToggleSavedComicUseCase(
            dataStore, mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `save comic should call data source save comic function`() =
        mainCoroutineRule.runBlockingTest {
            val params = ToggleSavedComicUseCase.Params.create(COMIC_ITEM)
            toggleSavedComicUseCase(params)
            Mockito.verify(dataStore).toggleSavedComic(COMIC_ITEM)
        }
}
