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

package com.elbehiry.comicsapp.ui.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elbehiry.comicsapp.R
import com.elbehiry.comicsapp.ui.widget.BookMarkButton
import com.elbehiry.comicsapp.ui.widget.EmptyView
import com.elbehiry.comicsapp.ui.widget.LoadingContent
import com.elbehiry.comicsapp.ui.widget.NetworkImage
import com.elbehiry.model.Comic

@Composable
fun ComicsDetails(
    comicId: Int,
    onExplanation: (Int) -> Unit,
    upPress: () -> Unit
) {
    val viewModel = hiltViewModel<ComicDetailsViewModel>()
    val comicDetails by viewModel.comicDetails.collectAsState()
    val isLoading: Boolean by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState("")
    viewModel.getComicDetails(comicId)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        DetailsTopBar(upPress = upPress)
        LoadingContent(isLoading) {
            AnimatedVisibility(visible = errorMessage.isEmpty()) {
                LazyColumn {
                    item {
                        NetworkImage(
                            url = comicDetails?.img ?: "",
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }
                    item { ComicDivider() }
                    item {
                        DetailsOptions(comicDetails, onExplanation) {
                            viewModel.onBookMark(it)
                        }
                    }
                    item { ComicDivider() }
                    item {
                        Text(
                            text = comicDetails?.title ?: "",
                            style = MaterialTheme.typography.subtitle1,
                            color = Color.White,
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }

                    item {
                        Text(
                            text = "${comicDetails?.alt}",
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 2,
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }
            AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
                EmptyView(
                    titleText = stringResource(id = R.string.something_went_wrong),
                    descText = stringResource(id = R.string.try_again_later)
                )
            }
        }
    }
}

@Composable
fun DetailsTopBar(upPress: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.details),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
        },
        elevation = 6.dp,
        navigationIcon = {
            IconButton(onClick = { upPress() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        }
    )
}

@Composable
fun DetailsOptions(
    comicDetails: Comic?,
    onExplanation: (Int) -> Unit,
    onBookMarkClicked: (Comic?) -> Unit
) {
    Row(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
    ) {
        TextButton(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            onClick = {
                onExplanation(comicDetails?.num ?: 0)
            }
        ) {
            Icon(
                modifier = Modifier.padding(end = 4.dp),
                imageVector = Icons.Filled.Public,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(id = R.string.explanation),
                color = Color.White,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 12.sp
            )
        }
        BookMarkButton(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            onBookMark = {
                val isSaved = comicDetails?.saved ?: false
                comicDetails?.saved = !isSaved
                onBookMarkClicked(comicDetails)
            },
            selected = comicDetails?.saved ?: false
        )
    }
}

@Composable
private fun ComicDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}
