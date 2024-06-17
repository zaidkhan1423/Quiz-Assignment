package com.zaid.quizassignment.presentation.quiz_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.zaid.quizassignment.R
import com.zaid.quizassignment.navigation.NavGraphRoutes
import com.zaid.quizassignment.navigation.Screen
import com.zaid.quizassignment.ui.theme.QuizAssignmentTheme

@Composable
fun QuizScreen(viewModel: QuizScreenViewModel, navController: NavController) {

    val uiState by viewModel.uiState.collectAsState()
    val question = uiState.questions[uiState.currentQuestionIndex]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        if (uiState.questions.isNotEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.size(20.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.timer_svgrepo_com),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "${uiState.remainingTime / 60}:${
                            String.format(
                                "%02d",
                                uiState.remainingTime % 60
                            )
                        }",
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "${uiState.currentQuestionIndex + 1}/${uiState.questions.size}",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(5.dp))
                LinearProgressIndicator(
                    progress = { (uiState.currentQuestionIndex + 1).toFloat() / uiState.questions.size },
                    modifier = Modifier
                        .widthIn(600.dp)
                        .padding(horizontal = 30.dp),
                )
                Spacer(modifier = Modifier.size(15.dp))
                Column(
                    modifier = Modifier
                        .widthIn(600.dp)
                        .wrapContentHeight()
                        .heightIn(120.dp)
                        .padding(horizontal = 25.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.06f))
                ) {
                    Text(
                        modifier = Modifier
                            .padding(15.dp),
                        text = question.question,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }

                Spacer(modifier = Modifier.size(15.dp))

                Column(
                    modifier = Modifier
                        .widthIn(600.dp)
                        .padding(horizontal = 25.dp)
                ) {
                    question.options.forEach { option ->
                        QuizOption(
                            option = option,
                            isSelected = uiState.selectedOption == option,
                            onOptionSelected = {
                                viewModel.selectOption(it)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.size(15.dp))
                Button(
                    onClick = {
                        viewModel.correctAnswersCount()
                        viewModel.nextQuestion()
                    },
                    modifier = Modifier
                        .height(48.dp)
                        .widthIn(600.dp)
                        .padding(horizontal = 25.dp)
                ) {
                    Text(
                        text = if (uiState.currentQuestionIndex < uiState.questions.size - 1) "Next" else "Finish",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.size(15.dp))
            }
        }

        if (uiState.shouldDialog) {
            Dialog(onDismissRequest = { }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Quiz Result",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${uiState.correctAnswers}/10",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.size(10.dp))
                        Button(
                            onClick = {
                                navController.navigate(Screen.HomeScreen.route) {
                                    popUpTo(NavGraphRoutes.TOP_LEVEL) {
                                        inclusive = true
                                    }
                                }
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .widthIn(600.dp)
                                .padding(horizontal = 25.dp)
                        ) {
                            Text(
                                text = "Want to play again",
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                color = Color.White
                            )
                        }

                    }
                }
            }
        }

    }
}

@Composable
fun QuizOption(option: String, isSelected: Boolean, onOptionSelected: (String) -> Unit) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Gray.copy(
            alpha = 0.1f
        )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable { onOptionSelected(option) }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = option,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = isSelected,
            onClick = { onOptionSelected(option) }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun QuizScreenPreview() {
    QuizAssignmentTheme {
        QuizScreen(
            viewModel = QuizScreenViewModel(LocalContext.current),
            navController = NavController(
                LocalContext.current
            )
        )
    }
}
