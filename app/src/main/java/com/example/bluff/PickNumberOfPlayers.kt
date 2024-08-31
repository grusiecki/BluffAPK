package com.example.bluff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.Deck

class PickNumberOfPlayersScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }

    var numberOfPlayers = 0
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickNumberOfPlayer(navController: NavController) {


    Box(

        modifier = Modifier
            .fillMaxSize()
        //  .padding(paddingValues)

    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        IconButton(
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
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Pick number of Players",
                fontSize = 24.sp, // Ustaw rozmiar tekstu na 24 sp
                fontWeight = FontWeight.Bold, // Ustaw wagę tekstu na pogrubioną
                fontFamily = boldItalic,
                color = Color.White
            )
            Button(onClick = {
                navController.navigate("setPlayersName/2")
            }) {
                Text("2 Players")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {

                navController.navigate("setPlayersName/3")
            }) {
                Text("3 Players")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("setPlayersName/4") }) {
                Text("4 Players")
            }
        }
    }


}





