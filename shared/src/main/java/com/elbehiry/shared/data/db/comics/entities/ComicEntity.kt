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

package com.elbehiry.shared.data.db.comics.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = ComicEntity.Schema.TABLE_NAME,
    primaryKeys = [ComicEntity.Schema.COMIC_NUM]
)
class ComicEntity(
    @ColumnInfo(name = Schema.COMIC_NUM) val comicNum: Int,
    @ColumnInfo(name = Schema.COMIC_TITLE) val title: String,
    @ColumnInfo(name = Schema.COMIC_ALT) val alt: String,
    @ColumnInfo(name = Schema.COMIC_DAY) val day: String,
    @ColumnInfo(name = Schema.COMIC_MONTH) val month: String,
    @ColumnInfo(name = Schema.COMIC_Year) val year: String,
    @ColumnInfo(name = Schema.COMIC_LINK) val link: String,
    @ColumnInfo(name = Schema.COMIC_IMAGE) val image: String
) {
    object Schema {
        const val TABLE_NAME = "comics"
        const val COMIC_NUM = "comicNum"
        const val COMIC_TITLE = "comicTitle"
        const val COMIC_ALT = "comicAlt"
        const val COMIC_DAY = "comicDay"
        const val COMIC_MONTH = "comicMonth"
        const val COMIC_Year = "comicYear"
        const val COMIC_LINK = "comicLink"
        const val COMIC_IMAGE = "comicImg"
    }
}
