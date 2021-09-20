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

package com.elbehiry.comicsapp.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import com.elbehiry.comicsapp.R
import com.elbehiry.comicsapp.ui.navigation.NavGraph
import com.elbehiry.comicsapp.ui.theme.ComicsComposeTheme
import com.elbehiry.model.Comic
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComicsComposeTheme {
                ProvideWindowInsets {
                    NavGraph(
                        onExplanation = {
                            openExplanationLink(it)
                        }
                    ) {
                        shareComic(it)
                    }
                }
            }
        }
    }

    private fun openExplanationLink(comicId: Int) {
        startActivity(
            Intent(Intent.ACTION_VIEW).apply {
                this.data = Uri.parse("${getString(R.string.explained_url)}$comicId")
            }
        )
    }

    private fun shareComic(comic: Comic?) {
        comic?.let {
            startActivity(
                Intent.createChooser(
                    Intent().apply {
                        this.action = Intent.ACTION_SEND
                        this.type = "text/plain"
                        this.putExtra(Intent.EXTRA_SUBJECT, it.title)
                        this.putExtra(Intent.EXTRA_TEXT, it.alt)
                    },
                    getString(R.string.share)
                )
            )
        }
    }
}
