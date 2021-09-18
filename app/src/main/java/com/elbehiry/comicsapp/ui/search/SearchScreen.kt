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

package com.elbehiry.comicsapp.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.elbehiry.comicsapp.R
import com.elbehiry.comicsapp.ui.widget.LoadingContent
import com.elbehiry.comicsapp.ui.widget.NetworkImage
import com.elbehiry.comicsapp.ui.widget.SearchAppBar
import com.elbehiry.model.Comic

@Composable
fun SearchScreen(
    navController: NavController,
    onDetails: (Int?) -> Unit
) {
    val viewModel = hiltViewModel<SearchComicViewModel>()

    val comic by viewModel.comic.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        SearchHeaderItem(
            navController,
            stringResource(id = R.string.search)
        ) {
            viewModel.search(it)
        }
        LoadingContent(loading) {

            AnimatedVisibility(visible = comic != null) {
                SearchItem(comic = comic, onItemClick = onDetails)
            }

            AnimatedVisibility(visible = comic == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.no_search_result),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        maxLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchHeaderItem(
    navController: NavController,
    title: String,
    onSearch: (String) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue()) }
    SearchAppBar(
        title = title,
        textFieldValue = textState,
        onTextChanged = { textState = it },
        onBackPressed = { navController.navigateUp() },
        searchHint = stringResource(id = R.string.search_hint),
        backgroundColor = Color(0x801F1F1F)
    ) {
        onSearch(textState.text)
    }
}

@Composable
fun SearchItem(
    comic: Comic?,
    modifier: Modifier = Modifier,
    onItemClick: (Int?) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (image, time, title, desc) = createRefs()
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp)
                .clickable { onItemClick(comic?.num ?: 0) }
                .constrainAs(image) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    width = Dimension.fillToConstraints
                },
            color = MaterialTheme.colors.background,
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            NetworkImage(
                url = comic?.img ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = "${comic?.day}-${comic?.month}-${comic?.year}",
            style = MaterialTheme.typography.subtitle2,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .constrainAs(time) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    linkTo(
                        top = image.bottom,
                        bottom = title.top
                    )
                }
        )
        Text(
            text = comic?.title ?: "",
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            color = Color.White,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .constrainAs(title) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    linkTo(
                        top = time.bottom,
                        bottom = desc.top
                    )
                }
        )
        Text(
            text = "${comic?.alt}",
            style = MaterialTheme.typography.subtitle2,
            color = Color.White,
            maxLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .constrainAs(desc) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    linkTo(
                        top = title.bottom,
                        bottom = parent.bottom
                    )
                }
        )
    }
}
