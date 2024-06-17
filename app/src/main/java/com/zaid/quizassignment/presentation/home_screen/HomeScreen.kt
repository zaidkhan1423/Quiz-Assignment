package com.zaid.quizassignment.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zaid.quizassignment.R
import com.zaid.quizassignment.navigation.Screen
import com.zaid.quizassignment.ui.theme.QuizAssignmentTheme

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Quiz App",
                fontSize = MaterialTheme.typography.displayMedium.fontSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Image(painter = painterResource(id = R.drawable.quiz), contentDescription = null)

            Button(
                onClick = { navController.navigate(Screen.QuizScreen.route) },
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp)
                    .padding(horizontal = 28.dp)
            ) {
                Text(
                    text = "Start",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = Color.White
                )
            }

        }
    }

}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    QuizAssignmentTheme {
        HomeScreen(navController = NavController(LocalContext.current))
    }
}