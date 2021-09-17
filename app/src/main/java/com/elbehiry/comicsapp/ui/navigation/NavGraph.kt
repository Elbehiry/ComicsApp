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
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elbehiry.comicsapp.ui.main.HomeView
import com.elbehiry.comicsapp.ui.main.MainViewModel
import com.elbehiry.model.Comic

object MainDestinations {
    const val Home_ROUTE = "home"
    const val DETAILS_ROUTE = "detail"
    const val SEARCH_ROUTE = "search"
}

@Composable
fun NavGraph(
    startDestination: String = MainDestinations.Home_ROUTE,
    onShare: (Comic?) -> Unit
) {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable((MainDestinations.Home_ROUTE)) {
            HomeView(
                onDetails = { actions.openDetails },
                onShare = onShare
            ) { actions.openSearch() }
        }
        composable((MainDestinations.DETAILS_ROUTE)) {
        }
        composable((MainDestinations.SEARCH_ROUTE)) {
        }
    }
}

class MainActions(navController: NavHostController) {
    val openSearch: () -> Unit = {
        navController.navigate(MainDestinations.SEARCH_ROUTE)
    }

    val openDetails = { comicNumber: Int ->
        navController.navigate("${MainDestinations.DETAILS_ROUTE}/$comicNumber")
    }
}
