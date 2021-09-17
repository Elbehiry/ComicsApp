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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.elbehiry.comicsapp.R
import com.elbehiry.comicsapp.ui.theme.ComicsComposeTheme
import com.elbehiry.comicsapp.ui.widget.NetworkImage
import com.elbehiry.model.Comic

@Composable
fun ComicItem(
    comic: Comic?,
    modifier: Modifier = Modifier,
    onShare: () -> Unit,
    getRandomComic: () -> Unit,
    onDetails: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onDetails(comic?.num ?: 0) }
    ) {
        val (image, time, title, desc, random) = createRefs()
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(250.dp)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                NetworkImage(
                    url = comic?.img ?: "",
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    color = colorResource(id = R.color.black_alpha),
                    shape = CircleShape,
                    modifier = modifier
                        .padding(4.dp)
                        .align(Alignment.TopEnd)
                        .requiredSize(36.dp, 36.dp)
                        .clickable { onShare() }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        tint = colorResource(id = android.R.color.white),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }
            }
        }
        Text(
            text = "${comic?.day}-${comic?.month}-${comic?.year}",
            style = MaterialTheme.typography.subtitle2,
            maxLines = 1,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 4.dp)
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
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
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
            maxLines = 2,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .constrainAs(desc) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    linkTo(
                        top = title.bottom,
                        bottom = random.top
                    )
                }
        )

        Button(
            onClick = { getRandomComic() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp, horizontal = 16.dp)
                .constrainAs(random) {
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                    linkTo(
                        top = desc.bottom,
                        bottom = parent.bottom
                    )
                },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = stringResource(id = R.string.get_random_comic),
                style = MaterialTheme.typography.body2,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
fun PreviewInspirationItem() {
    ComicsComposeTheme {
        ComicItem(Comic(title = "title preview"), onShare = {}, getRandomComic = {}) {}
    }
}
