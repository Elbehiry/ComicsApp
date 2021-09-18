package com.elbehiry.shared.domain.usecases

import com.elbehiry.shared.data.db.comics.datastore.IComicsLocalDataStore
import com.elbehiry.shared.domain.bookmark.SaveRecipeUseCase
import com.elbehiry.test_shared.COMIC_ITEM
import com.elbehiry.test_shared.MainCoroutineRule
import com.elbehiry.test_shared.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveRecipeUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var dataStore: IComicsLocalDataStore
    private lateinit var saveRecipeUseCase: SaveRecipeUseCase

    @Before
    fun setUp() {
        saveRecipeUseCase = SaveRecipeUseCase(
            dataStore, mainCoroutineRule.testDispatcher
        )
    }

    @Test
    fun `save comic should call data source save comic function`() =
        mainCoroutineRule.runBlockingTest {
            saveRecipeUseCase(COMIC_ITEM)
            verify(dataStore).saveComic(COMIC_ITEM)
        }
}