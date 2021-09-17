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

package com.elbehiry.comicsapp.ui.main.comics

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.elbehiry.comicsapp.R
import com.elbehiry.comicsapp.ui.main.MainViewModel
import com.elbehiry.comicsapp.ui.widget.EmptyView
import com.elbehiry.comicsapp.ui.widget.LoadingContent
import com.elbehiry.model.Comic

@Composable
fun ComicsView(
    onShare: (Comic?) -> Unit,
    onDetails: (Int) -> Unit
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val comic by viewModel.comic.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val hasError by viewModel.hasError.collectAsState()

    LoadingContent(loading) {
        AnimatedVisibility(visible = (comic != null && !hasError)) {
            ComicItem(
                comic,
                onShare = { onShare(comic) },
                getRandomComic = {
                    viewModel.getRandomComic(comic?.num ?: 0)
                },
                onDetails = onDetails
            )
        }
        AnimatedVisibility(visible = hasError) {
            EmptyView(
                titleText = stringResource(id = R.string.something_went_wrong),
                descText = stringResource(id = R.string.try_again_later)
            )
        }
    }
}
