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

package com.elbehiry.shared.domain.bookmark

import com.elbehiry.model.Comic
import com.elbehiry.shared.data.db.comics.datasource.IComicsLocalDataStore
import com.elbehiry.shared.di.IoDispatcher
import com.elbehiry.shared.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Save/Delete comic using [Comic] value.
 */
class ToggleSavedComicUseCase @Inject constructor(
    private val dataStore: IComicsLocalDataStore,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<ToggleSavedComicUseCase.Params, Unit>(ioDispatcher) {

    override suspend fun execute(parameters: Params) = dataStore.toggleSavedComic(parameters.comic)

    class Params private constructor(
        val comic: Comic
    ) {

        companion object {
            @JvmStatic
            fun create(
                comic: Comic,
            ): Params {
                return Params(comic)
            }
        }
    }
}
