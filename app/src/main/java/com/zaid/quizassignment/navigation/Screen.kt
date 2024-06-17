package com.zaid.quizassignment.navigation

sealed class Screen(val route: String) {

    data object HomeScreen : Screen("home_screen")
    data object QuizScreen : Screen("quiz_screen")

}