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

package com.elbehiry.shared.data.db.di

import android.content.Context
import androidx.room.Room
import com.elbehiry.shared.data.db.ComicsDataBase
import com.elbehiry.shared.data.db.Constants
import com.elbehiry.shared.data.db.MIGRATIONS
import com.elbehiry.shared.data.db.comics.datastore.ComicsLocalDataStore
import com.elbehiry.shared.data.db.comics.datastore.IComicsLocalDataStore
import com.elbehiry.shared.data.db.comics.mapper.ComicMapper
import com.elbehiry.shared.data.db.comics.mapper.ComicMapperImpl
import com.elbehiry.shared.data.db.comics.tables.ComicsTable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideComicsDatabase(@ApplicationContext context: Context): ComicsDataBase {
        return Room.databaseBuilder(
            context,
            ComicsDataBase::class.java,
            Constants.DATABASE_NAME
        ).addMigrations(*MIGRATIONS)
            .build()
    }

    @Provides
    @Singleton
    fun provideComicsTable(comicsDataBase: ComicsDataBase): ComicsTable {
        return comicsDataBase.comicsTable
    }

    @Provides
    @Singleton
    fun provideComicsMapper(): ComicMapper {
        return ComicMapperImpl()
    }

    @Provides
    @Singleton
    fun provideComicsDataStore(
        comicsTable: ComicsTable,
        comicsMapper: ComicMapper,
    ): IComicsLocalDataStore {
        return ComicsLocalDataStore(comicsTable, comicsMapper)
    }
}
