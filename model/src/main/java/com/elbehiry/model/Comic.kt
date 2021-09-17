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

package com.elbehiry.model

import com.squareup.moshi.Json

data class Comic(

    @Json(name = "news")
    val news: String? = null,

    @Json(name = "img")
    val img: String? = null,

    @Json(name = "transcript")
    val transcript: String? = null,

    @Json(name = "month")
    val month: String? = null,

    @Json(name = "year")
    val year: String? = null,

    @Json(name = "num")
    val num: Int? = null,

    @Json(name = "link")
    val link: String? = null,

    @Json(name = "alt")
    val alt: String? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "day")
    val day: String? = null,

    @Json(name = "safe_title")
    val safeTitle: String? = null
)
