package com.example.bluff

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import model.GameViewModel

@Composable
fun PlayerScreen(viewModel: GameViewModel, navController: NavController) {

    val currentUserName = viewModel.listOfPlayers[viewModel.currentUserIndex].name


    Box(

        modifier = Modifier
            .fillMaxSize()


    ) {

        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navController.navigate("gameScreen") } // On click navigate to gameScreen
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = currentUserName,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldItalic,
                color = Color.White
            )
            if(viewModel.lastFirstAnswer != model.Figure.EIGHT){
            Text(
                text = viewModel.lastSet.str,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldItalic,
                color = Color.White
            )
                Text(
                    text = viewModel.lastFirstAnswer.str,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )
                if(viewModel.lastSet == model.Set.ROYALFLUSH || viewModel.lastSet == model.Set.FULL || viewModel.lastSet == model.Set.TWOPAIRS){
                Text(
                    text = viewModel.lastSecondAnswer.str,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )}
            }

            Text(
                text = "Ready? click on the screen",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldItalic,
                color = Color.White
            )
        }
    }
}