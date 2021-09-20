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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elbehiry.model.Comic
import com.elbehiry.shared.domain.bookmark.SearchComicsUseCase
import com.elbehiry.shared.result.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for search screen.
 */
@HiltViewModel
class SearchComicViewModel @Inject constructor(
    private val searchComicsUseCase: SearchComicsUseCase
) : ViewModel() {

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    private val _comic = MutableStateFlow<Comic?>(null)
    val comic: StateFlow<Comic?> = _comic

    private val searchComic = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            searchComic.flatMapLatest { query ->
                flowOf(searchComicsUseCase(query))
            }.map {
                it.data
            }.collect { comic ->
                _comic.value = comic
                _loading.value = false
            }
        }
    }

    /**
     * Search for comics using [query] value.
     */
    fun search(query: String) {
        viewModelScope.launch {
            _loading.value = true
            searchComic.emit(query)
        }
    }
}
