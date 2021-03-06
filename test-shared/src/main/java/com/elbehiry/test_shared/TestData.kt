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

package com.elbehiry.test_shared

import com.elbehiry.model.Comic
import com.github.javafaker.Faker

val faker = Faker()

val COMIC_ITEM = Comic(
    news = faker.lorem().sentence(),
    img = faker.internet().image(),
    transcript = faker.lorem().sentence(),
    month = faker.number().digit(),
    year = faker.number().digits(4),
    num = faker.number().randomNumber().toInt(),
    link = faker.internet().url(),
    alt = faker.lorem().sentence(),
    title = faker.lorem().sentence(),
    day = faker.number().numberBetween(1, 31).toString(),
    safeTitle = faker.lorem().sentence()
)

val COMICS_ITEMS = listOf(
    COMIC_ITEM.copy(num = faker.number().digits(3).toInt()),
    COMIC_ITEM.copy(num = faker.number().digits(3).toInt()),
    COMIC_ITEM.copy(num = faker.number().digits(3).toInt()),
    COMIC_ITEM.copy(num = faker.number().digits(3).toInt())
)
