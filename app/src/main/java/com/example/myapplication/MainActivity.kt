package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    GuessTheNumberGame()
                }
            }
        }
    }
}

@Composable
fun GuessTheNumberGame() {
    var targetNumber by remember { mutableStateOf(Random.nextInt(1, 100)) }
    var attempts by remember { mutableStateOf(0) }
    var guess by remember { mutableStateOf(TextFieldValue("")) }
    var hint by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Guess a number between 1 and 100", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary)
                .padding(4.dp)
        ) {
        BasicTextField(
            value = guess,
            onValueChange = { guess = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField ->
                if (guess.text.isEmpty()) {
                    Text("Enter a number", color = Color.Gray)
                }
                innerTextField()
            }
        ) }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val userGuess = guess.text.toIntOrNull()
            if (userGuess != null) {
                attempts++
                hint = when {
                    userGuess > targetNumber -> "Too high! Try again."
                    userGuess < targetNumber -> "Too low! Try again."
                    else -> {
                        val result = "Correct! You guessed it in $attempts attempts."
                        resetGame {
                            targetNumber = Random.nextInt(1, 100)
                            attempts = 0
                            hint = result
                        }
                        return@Button
                    }
                }
            } else {
                hint = "Please enter a valid number"
            }
        }) {
            Text("Submit Guess")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = hint, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun resetGame(action: () -> Unit) {
    action()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        GuessTheNumberGame()
    }
}
