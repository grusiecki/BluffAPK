package com.example.bluff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import model.Deck
import org.example.Player

class PlayerNameFlowScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun PlayerNameFlow(navController: NavController, playerCount: Int, viewModel: GameViewModel) {
    var currentPlayerIndex by remember { mutableStateOf(0) }
    val playerNames = remember { mutableListOf<String>() }

    if (currentPlayerIndex < playerCount) {
        PlayerNameInputScreen(
            playerNumber = currentPlayerIndex + 1, navController
        ) { playerName ->
            playerNames.add(playerName)
            val deck = Deck()
            viewModel.setListOfPlayers(playerName)
            if (playerNames.size == playerCount) {
                viewModel.setCards(deck)
                navController.navigate("playerScreen")

            } else {
                currentPlayerIndex++
            }
        }
    }
}

@Composable
fun PlayerNameInputScreen(
    playerNumber: Int, navController: NavController,
    onNameEntered: (String) -> Unit
) {
    var playerName by remember(playerNumber) { mutableStateOf("") }
    Box(

        modifier = Modifier
            .fillMaxSize()


    ) {IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp)
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, Color.Black, CircleShape)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.scale(1.5f)
        )
    }
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Player $playerNumber, enter your name",
                fontSize = 24.sp, // Ustaw rozmiar tekstu na 24 sp
                fontWeight = FontWeight.Bold, // Ustaw wagę tekstu na pogrubioną
                fontFamily = boldItalic,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onNameEntered(playerName) }) {
                Text("Submit")
            }
        }
    }
}