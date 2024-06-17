package com.zaid.quizassignment.presentation.quiz_screen

import com.zaid.quizassignment.data.model.Question


data class QuizScreenUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOption: String? = null,
    val remainingTime: Long = 600L,
    val snackBarMessage: String? = null,
    val shouldDialog: Boolean = false,
    val correctAnswers: Int = -1
)
