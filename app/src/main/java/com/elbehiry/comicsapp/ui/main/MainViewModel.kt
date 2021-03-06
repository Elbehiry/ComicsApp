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

package com.elbehiry.comicsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elbehiry.model.Comic
import com.elbehiry.shared.domain.browse.GetComicUseCase
import com.elbehiry.shared.result.Result
import com.elbehiry.shared.result.data
import com.elbehiry.comicsapp.utils.WhileViewSubscribed
import com.elbehiry.shared.domain.browse.GetRandomComicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for main screen.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getComicUseCase: GetComicUseCase,
    private val getRandomComicUseCase: GetRandomComicUseCase
) : ViewModel() {

    private val _comic = MutableStateFlow<Comic?>(null)
    val comic: StateFlow<Comic?> = _comic

    private val _errorMessage = Channel<String>(1, BufferOverflow.DROP_LATEST)
    val errorMessage: Flow<String> =
        _errorMessage.receiveAsFlow().shareIn(viewModelScope, WhileViewSubscribed)

    val hasError = MutableStateFlow(false)

    private val getComics = MutableSharedFlow<FetchType>()
    private val viewState: StateFlow<Result<Comic>> = getComics.flatMapLatest { type ->
        when (type) {
            is FetchType.MostRecent -> {
                getComicUseCase(Unit)
            }
            is FetchType.Random -> {
                val params = GetRandomComicUseCase.Params.create(type.comicNum)
                getRandomComicUseCase(params)
            }
        }
    }.onEach {
        if (it is Result.Error) {
            hasError.value = true
            _errorMessage.trySend(it.exception.message ?: "Error")
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
                        hasError.value = false
                        _errorMessage.trySend("")
                        _comic.value = comic
                    }
                }
        }

        getMostRecentComic()
    }

    /**
     * Fetch the most recent available comic online.
     */
    private fun getMostRecentComic() {
        viewModelScope.launch {
            getComics.emit(FetchType.MostRecent)
        }
    }

    /**
     * Get random comic using comic [num] value.
     */
    fun getRandomComic(num: Int) {
        viewModelScope.launch {
            getComics.emit(FetchType.Random(num))
        }
    }
}

/**
 * This [sealed] class helps to indicate the type of call.
 * Actually this helps to create(merge) just one flow for both calls.
 */
sealed class FetchType {
    object MostRecent : FetchType()
    class Random(val comicNum: Int) : FetchType()
}
