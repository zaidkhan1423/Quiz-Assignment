package com.zaid.quizassignment.navigation.nav_graphs

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zaid.quizassignment.navigation.NavGraphRoutes
import com.zaid.quizassignment.navigation.Screen
import com.zaid.quizassignment.presentation.home_screen.HomeScreen
import com.zaid.quizassignment.presentation.quiz_screen.QuizScreen
import com.zaid.quizassignment.presentation.quiz_screen.QuizScreenViewModel

fun NavGraphBuilder.topLevelGraph(
    navController: NavHostController
) {
    navigation(startDestination = Screen.HomeScreen.route, route = NavGraphRoutes.TOP_LEVEL) {

        composable(route = Screen.HomeScreen.route) {

            HomeScreen(navController = navController)

        }
        composable(route = Screen.QuizScreen.route) {

            val quizViewModel: QuizScreenViewModel = hiltViewModel()

            QuizScreen(viewModel = quizViewModel,navController = navController)
        }

    }
}