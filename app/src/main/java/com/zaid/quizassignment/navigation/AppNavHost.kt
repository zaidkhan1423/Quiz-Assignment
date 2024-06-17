package com.zaid.quizassignment.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.zaid.quizassignment.navigation.nav_graphs.topLevelGraph

@Composable
fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = NavGraphRoutes.TOP_LEVEL) {
        topLevelGraph(navController = navHostController)
    }
}

object NavGraphRoutes {
    const val TOP_LEVEL = "top_level"
}