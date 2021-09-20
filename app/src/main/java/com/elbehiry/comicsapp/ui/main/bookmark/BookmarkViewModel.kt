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

package com.elbehiry.comicsapp.ui.main.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elbehiry.model.Comic
import com.elbehiry.shared.domain.bookmark.DeleteComicUseCase
import com.elbehiry.shared.domain.bookmark.GetSavedComicsUseCase
import com.elbehiry.shared.result.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for book mark screen.
 */
@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val gatSavedComicsUseCase: GetSavedComicsUseCase,
    private val deletedComicsUseCase: DeleteComicUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookmarkViewState())
    val state: StateFlow<BookmarkViewState>
        get() = _state

    init {
        getSavedRecipes()
    }

    private fun getSavedRecipes() {
        viewModelScope.launch {
            gatSavedComicsUseCase(Unit).collect {
                if (it.data.isNullOrEmpty()) {
                    _state.value = BookmarkViewState(isEmpty = true)
                } else {
                    _state.value = BookmarkViewState(comics = it.data!!)
                }
            }
        }
    }

    /**
     * delete the [comic] using [comic.num]
     */
    fun deleteComic(comic: Comic) {
        viewModelScope.launch {
            deletedComicsUseCase(comic.num)
        }
    }

    data class BookmarkViewState(
        val comics: List<Comic> = emptyList(),
        val isEmpty: Boolean = false
    )
}
