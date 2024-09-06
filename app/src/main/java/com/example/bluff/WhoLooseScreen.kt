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

@Composable
fun WhoLooseScreen(viewModel: GameViewModel, navController: NavController) {
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
                .clickable {
                    viewModel.setCurrentUserIndex(viewModel.listOfPlayers.indexOf(viewModel.loosingPlayer))
                    //TODO adding exception when player loose
                    viewModel.listOfCards.clear()
                    viewModel.lastSet = model.Set.ONECARD
                    viewModel.lastFirstAnswer = model.Figure.EIGHT
                    viewModel.lastSecondAnswer = model.Figure.EIGHT
                    viewModel.currentSet = model.Set.ONECARD
//TODO set a new cards
                    navController.navigate("playerScreen")
                }
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = viewModel.loosingPlayer.name + " LOOSE!",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldItalic,
                color = Color.White
            )
            if(viewModel.loosingPlayer.numberOfCards > 5) {
                Text(
                    text = viewModel.loosingPlayer.name + "IS LOOSING THE GAME!\"",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )
                viewModel.removePlayer(viewModel.loosingPlayer)
            }
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
            if (viewModel.lastSet == model.Set.FULL || viewModel.lastSet== model.Set.TWOPAIRS || viewModel.lastSet == model.Set.ROYALFLUSH) {
                Text(

                    text = viewModel.lastSecondAnswer.str,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )
            }
            if (viewModel.setExist) {
                Text(
//TODO check why shows not exist when exist
                    text = "EXIST",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )
            } else {
                Text(

                    text = "NOT EXIST",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )
            }

            Text(
                text = "Click on the screen to continue",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldItalic,
                color = Color.White
            )
        }
    }
}