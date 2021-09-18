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

package com.elbehiry.comicsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.elbehiry.comicsapp.ui.details.ComicsDetails
import com.elbehiry.comicsapp.ui.main.HomeView
import com.elbehiry.comicsapp.ui.navigation.MainDestinations.COMIC_DETAIL_ID_KEY
import com.elbehiry.comicsapp.ui.search.SearchScreen
import com.elbehiry.model.Comic

object MainDestinations {
    const val Home_ROUTE = "home"
    const val DETAILS_ROUTE = "detail"
    const val SEARCH_ROUTE = "search"
    const val COMIC_DETAIL_ID_KEY = "comicId"
}

@Composable
fun NavGraph(
    startDestination: String = MainDestinations.Home_ROUTE,
    onExplanation: (Int) -> Unit,
    onShare: (Comic?) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.Home_ROUTE) {
            HomeView(
                onDetails = {
                    navController.navigate(route = "${MainDestinations.DETAILS_ROUTE}/$it")
                },
                onShare = onShare
            ) {
                navController.navigate(
                    route = MainDestinations.SEARCH_ROUTE
                )
            }
        }
        composable(
            "${MainDestinations.DETAILS_ROUTE}/{$COMIC_DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(COMIC_DETAIL_ID_KEY) { type = NavType.IntType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val comicId = arguments.getInt(COMIC_DETAIL_ID_KEY)

            ComicsDetails(comicId, onExplanation) {
                if (backStackEntry.lifecycleIsResumed()) {
                    navController.navigateUp()
                }
            }
        }
        composable(MainDestinations.SEARCH_ROUTE) {
            SearchScreen(navController) {
                navController.navigate(route = "${MainDestinations.DETAILS_ROUTE}/$it")
            }
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
