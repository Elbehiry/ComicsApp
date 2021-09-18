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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elbehiry.comicsapp.ui.main.FetchType
import com.elbehiry.model.Comic
import com.elbehiry.shared.domain.browse.GetComicByIdUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.comicsapp.utils.WhileViewSubscribed
import com.elbehiry.shared.domain.bookmark.IsComicSavedUseCase
import com.elbehiry.shared.domain.bookmark.GetComicsByNumLocallyUseCase
import com.elbehiry.shared.domain.bookmark.SaveRecipeUseCase
import com.elbehiry.shared.domain.bookmark.DeleteComicUseCase
import com.elbehiry.shared.result.data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicDetailsViewModel @Inject constructor(
    private val getComicByIdUseCase: GetComicByIdUseCase,
    private val isComicSavedUseCase: IsComicSavedUseCase,
    private val getComicsByNumLocallyUseCase: GetComicsByNumLocallyUseCase,
    private val savedComicsUseCase: SaveRecipeUseCase,
    private val deleteComicUseCase: DeleteComicUseCase
) : ViewModel() {


    private val _errorMessage = Channel<String>(1, BufferOverflow.DROP_LATEST)
    val errorMessage: Flow<String> =
        _errorMessage.receiveAsFlow().shareIn(viewModelScope, WhileViewSubscribed)

    private val _comicDetails = MutableStateFlow<Comic?>(null)
    val comicDetails: StateFlow<Comic?> = _comicDetails

    private val getDetails = MutableSharedFlow<Int>()
    private val viewState: StateFlow<Result<Comic?>> = getDetails.flatMapLatest { comicNum ->
        flowOf(isComicSavedUseCase(comicNum))
            .flatMapLatest {
                if (it.data == true) {
                    flowOf(getComicsByNumLocallyUseCase(comicNum))
                } else {
                    val params = GetComicByIdUseCase.Params.create(comicNum)
                    getComicByIdUseCase(params)
                }
            }.onEach {
                if (it is Result.Error) {
                    _errorMessage.trySend(it.exception.message ?: "Error")
                }
            }
    }.stateIn(viewModelScope, WhileViewSubscribed, Result.Loading)

    val isLoading: StateFlow<Boolean> = viewState
        .mapLatest {
            it == Result.Loading
        }.stateIn(viewModelScope, WhileViewSubscribed, false)

    init {
        viewModelScope.launch {
            viewState
                .mapLatest {
                    it.data
                }.collect { comic ->
                    if (comic != null) {
                        _errorMessage.trySend("")
                        _comicDetails.value = comic
                    }
                }
        }
    }

    fun getComicDetails(comicId: Int) {
        viewModelScope.launch {
            getDetails.emit(comicId)
        }
    }

    fun onBookMark(comic: Comic?) {
        comic?.let {
            viewModelScope.launch {
                if (isComicSavedUseCase(comic.num).data == true) {
                    deleteComicUseCase(comic.num)
                } else {
                    savedComicsUseCase(comic)
                }
            }
        }
    }
}
