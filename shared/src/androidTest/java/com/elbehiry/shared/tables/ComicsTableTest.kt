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

package com.elbehiry.shared.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.elbehiry.shared.data.db.ComicsDataBase
import com.elbehiry.shared.data.db.comics.tables.ComicsTable
import com.elbehiry.shared.utils.COMIC_ENTITY
import com.elbehiry.shared.utils.createMemoryDataBase
import org.assertj.core.api.Assertions.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComicsTableTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ComicsDataBase
    private lateinit var recipeTable: ComicsTable

    @Before
    fun setup() {
        database = createMemoryDataBase()
        recipeTable = database.comicsTable
    }

    @Test
    fun save_Comic_And_Get_Comic_Successfully() {
        runBlockingTest {
            val comic = COMIC_ENTITY
            recipeTable.saveComic(comic)
            val savedRecipe = recipeTable.getComic(comic.comicNum)
            assertThat(savedRecipe?.comicNum).isEqualTo(comic.comicNum)
        }
    }

    @After
    fun cleanUp() {
        database.close()
    }
}
