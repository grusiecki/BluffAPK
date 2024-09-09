package com.example.bluff

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import model.Deck

@Composable
fun WhoLooseScreen(viewModel: GameViewModel, navController: NavController) {
    var looserIndex: Int = viewModel.listOfPlayers.indexOf(viewModel.loosingPlayer)
     viewModel.ifDeleteFlag = false
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
                    text = viewModel.loosingPlayer.name + " IS LOOSING THE GAME!",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = boldItalic,
                    color = Color.White
                )
                if(looserIndex == viewModel.listOfPlayers.size -1){
                    looserIndex = 0
                }

                viewModel.ifDeleteFlag = true
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

            Button(onClick = {
                navController.navigate("allCardsScreen")
            }) {
                Text("Show all cards")
            }
            Button(onClick = {
                viewModel.setCurrentUserIndex(looserIndex)

                viewModel.listOfCards.clear()
                viewModel.lastSet = model.Set.ONECARD
                viewModel.lastFirstAnswer = model.Figure.EIGHT
                viewModel.lastSecondAnswer = model.Figure.EIGHT
                viewModel.currentSet = model.Set.ONECARD
                val deck = Deck()
                viewModel.setCards(deck)
                navController.navigate("playerScreen")

            }) {

                Text("Continue")
            }
        }
    }
}