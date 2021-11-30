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

package com.elbehiry.comicsapp.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.elbehiry.comicsapp.utils.NotificationUtils
import com.elbehiry.shared.data.pref.PreferencesKeys
import com.elbehiry.shared.data.pref.repository.DataStoreOperations
import com.elbehiry.shared.data.remote.ComicsApi
import androidx.hilt.work.HiltWorker
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Worker to run in background, if new comic is published on the service,
 * It fires internal (local) notification.
 */
@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val api: ComicsApi,
    private val dataStoreOperations: DataStoreOperations
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
//        val recentComic = api.getComic()
//        val savedComicNum = dataStoreOperations.read(PreferencesKeys.mostRecentComicNum)
//        recentComic.num?.let { num ->
//            if (num > savedComicNum) {
//                NotificationUtils.showNotification(recentComic, applicationContext)
//                return Result.success()
//            }
//        }
        return Result.success()
    }
}
