package com.elbehiry.shared.domain.bookmark

import com.elbehiry.model.Comic
import com.elbehiry.shared.data.db.comics.datastore.IComicsLocalDataStore
import com.elbehiry.shared.di.IoDispatcher
import com.elbehiry.shared.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetComicsByNumLocallyUseCase @Inject constructor(
    private val dataStore: IComicsLocalDataStore,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<Int?, Comic?>(ioDispatcher) {
    override suspend fun execute(parameters: Int?): Comic? = dataStore.getComicByNum(parameters)
}
