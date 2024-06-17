package com.zaid.quizassignment.presentation.quiz_screen

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zaid.quizassignment.data.model.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class QuizScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizScreenUiState())
    val uiState: StateFlow<QuizScreenUiState> = _uiState.asStateFlow()

    private lateinit var timer: CountDownTimer

    init {
        if (_uiState.value.questions.isEmpty()) {
            loadQuestions()
        }
        startTimer()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            val inputStream = context.assets.open("questions.json")
            val json = InputStreamReader(inputStream).readText()
            val type = object : TypeToken<List<Question>>() {}.type
            _uiState.value = _uiState.value.copy(questions = Gson().fromJson(json, type))
        }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(_uiState.value.remainingTime * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                _uiState.value = _uiState.value.copy(remainingTime = millisUntilFinished / 1000)
            }

            override fun onFinish() {
                // Handle quiz completion
            }
        }
        timer.start()
    }


    fun selectOption(option: String) {
        _uiState.value = _uiState.value.copy(selectedOption = option)
    }

    var correctAnswers = 0
    fun correctAnswersCount(){
        if (_uiState.value.questions[_uiState.value.currentQuestionIndex].answer == uiState.value.selectedOption) {
            correctAnswers++
        }
    }

    fun nextQuestion() {

        if (_uiState.value.currentQuestionIndex < _uiState.value.questions.size - 1) {
            _uiState.value = _uiState.value.copy(
                currentQuestionIndex = _uiState.value.currentQuestionIndex + 1,
                selectedOption = null
            )
        } else {
            _uiState.update {
                it.copy(
                    correctAnswers = correctAnswers,
                    shouldDialog = true
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}
