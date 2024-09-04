package com.example.bluff

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import model.Card
import model.Rankable

@Composable
fun GameScreen(
    navController: NavController,
    onOptionSelected: (String) -> Unit,
    onGameEnd: () -> Unit,
    viewModel: GameViewModel
) {
    var currentPlayerIndexVar = viewModel.currentUserIndex
    val playerCards = viewModel.listOfPlayers[viewModel.currentUserIndex].hand
    var currentOption by remember { mutableStateOf("") }
    var secondPairVisible by remember { mutableStateOf(false) }
    var firstPair by remember { mutableStateOf("") }
    val cardWidth = remember { 100.dp }
    val aspectRatio = 0.8f
    var showPlayerCards by remember { mutableStateOf(true) }

    var isEnabled = true

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
        IconButton(
            onClick = {
                if (currentOption.isEmpty()) {
                    navController.popBackStack()
                } else {
                    currentOption = ""

                }
            },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Your Cards",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            if (showPlayerCards) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    playerCards.forEach { card ->
                        Box(

                            modifier = Modifier
                                .padding(4.dp) // Odstęp między kartami
                                .weight(1f, fill = false),
                            // Używamy fill false, aby dopasować rozmiar do zawartości
                        ) {
                            Image(
                                painter = painterResource(id = card.imageResId),
                                contentDescription = "Card",
                                modifier = Modifier
                                    .width(cardWidth)
                                    .aspectRatio(aspectRatio) // Podtrzymanie proporcji
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))



            if (currentOption.isEmpty()) {
                toggleButtonsColumns().forEach { rowLabels ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        rowLabels.forEach { label ->
                            isEnabled = viewModel.lastSet.rank <= label.rank

                            GameButton(
                                label, isEnabled
                            ) {
                                viewModel.equalSet = viewModel.lastSet.rank == label.rank
                                viewModel.currentSet = label
                                currentOption =  label.str
                                onOptionSelected(label.str)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                when (currentOption) {
                    "One Card", "Pair", "Three", "Four" -> {

                        cardOptions().forEach { rowLabels ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                // .weight(1f)

                            )
                            {

                                rowLabels.forEach { label ->

                                    isEnabled = if(viewModel.equalSet){
                                        viewModel.lastFirstAnswer.rank < label.rank
                                    }else{
                                        true
                                    }

                                    GameButton(
                                        label, isEnabled) {
                                        showPlayerCards = false
                                        viewModel.lastFirstAnswer = label
                                        currentPlayerIndexVar = afterChoosingSet(
                                            currentPlayerIndex = currentPlayerIndexVar,
                                            navController = navController,
                                            viewModel = viewModel
                                        )
                                        viewModel.lastSet=viewModel.currentSet
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    "Two Pairs" -> {

                        if (firstPair.isEmpty()) {
                            Text(
                                "First Pair",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = boldItalic
                            )
                            cardOptions().forEach { rowLabels ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    //.weight(1f)
                                ) {


                                    rowLabels.forEach { label ->
                                         isEnabled = if(viewModel.equalSet){viewModel.lastFirstAnswer.rank < label.rank} else{true}
                                        GameButton(
                                            label, isEnabled) {
                                            viewModel.lastFirstAnswer = label
                                            firstPair = label.str
                                            secondPairVisible =
                                                true // Umożliwienie wyboru drugiej pary
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        } else if (secondPairVisible) {
                            Text(
                                "Second Pair", color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = boldItalic
                            )
                            cardOptions().forEach { rowLabels ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    // .weight(1f)
                                ) {

                                    // Przyciski dla drugiej pary


                                    rowLabels.forEach { label ->
                                         isEnabled = if(viewModel.equalSet){viewModel.lastSecondAnswer.rank < label.rank} else{true}
                                        GameButton(
                                            label, isEnabled){
                                            viewModel.lastSecondAnswer = label
                                            showPlayerCards = false
                                            currentPlayerIndexVar = afterChoosingSet(
                                                currentPlayerIndex = currentPlayerIndexVar,
                                                navController = navController,
                                                viewModel = viewModel
                                            )
                                            viewModel.lastSet=viewModel.currentSet
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                    "Straight" -> {

                        val cardOptions = listOf(model.SmallOrBig.SMALL, model.SmallOrBig.BIG)
                        cardOptions.forEach { label ->
                             isEnabled = if(viewModel.equalSet){viewModel.lastFirstAnswer.rank < label.rank} else{true}
                            GameButton(
                                label, isEnabled
                            ) {
                                viewModel.lastFirstAnswer = label
                                showPlayerCards = false
                                currentPlayerIndexVar = afterChoosingSet(
                                    currentPlayerIndex = currentPlayerIndexVar,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                                viewModel.lastSet=viewModel.currentSet
                            }
                        }
                    }

                    "Full" -> {

                        if (firstPair.isEmpty()) {
                            Text(
                                "Three", color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = boldItalic
                            )
                            // Przyciski dla pierwszej pary

                            cardOptions().forEach { rowLabels ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    // .weight(1f)
                                ) {
                                    rowLabels.forEach { label ->
                                         isEnabled = if(viewModel.equalSet){viewModel.lastFirstAnswer.rank < label.rank} else{true}
                                        GameButton(
                                            label, isEnabled
                                        ) {
                                            viewModel.lastFirstAnswer = label
                                            firstPair = label.str
                                            secondPairVisible =
                                                true
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        } else if (secondPairVisible) {
                            Text(
                                "Pair", color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = boldItalic
                            )
                            cardOptions().forEach { rowLabels ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    //  .weight(1f)
                                ) {
                                    rowLabels.forEach { label ->
                                         isEnabled = if(viewModel.equalSet){viewModel.lastFirstAnswer.rank < label.rank} else{true}
                                        GameButton(
                                            label, isEnabled) {
                                            viewModel.lastSecondAnswer = label
                                            showPlayerCards = false
                                            currentPlayerIndexVar = afterChoosingSet(
                                                currentPlayerIndex = currentPlayerIndexVar,
                                                navController = navController,
                                                viewModel = viewModel
                                            )
                                            viewModel.lastSet=viewModel.currentSet

                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }

                    "Flush" -> {

                        val suits = listOf(model.Color.HEART,model.Color.DIAMOND, model.Color.SPADE, model.Color.CLUB)
                        suits.forEach { label ->
                             isEnabled = if(viewModel.equalSet){viewModel.lastFirstAnswer.rank < label.rank} else{true}
                            GameButton(
                                label, isEnabled
                            ) {
                                viewModel.lastFirstAnswer = label
                                showPlayerCards = false
                                currentPlayerIndexVar = afterChoosingSet(
                                    currentPlayerIndex = currentPlayerIndexVar,
                                    navController = navController,
                                    viewModel = viewModel
                                )
                                viewModel.lastSet=viewModel.currentSet
                            }
                        }
                    }

                    "Royal Flush" -> {

                        if (firstPair.isEmpty()) {
                            Text(
                                "Small or Big", color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = boldItalic
                            )
                            // Przyciski dla pierwszej pary
                            val cardOptions = listOf(model.SmallOrBig.SMALL,model.SmallOrBig.BIG)
                            cardOptions.forEach { label ->
                                 isEnabled = if(viewModel.equalSet){viewModel.lastFirstAnswer.rank < label.rank} else{true}
                                GameButton(
                                    label, isEnabled
                                ) {
                                    viewModel.lastFirstAnswer = label
                                    firstPair = label.str
                                    secondPairVisible = true
                                }
                            }
                        } else if (secondPairVisible) {
                            Text(
                                "Flush", color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = boldItalic
                            )
                            val suits = listOf(model.Color.HEART, model.Color.DIAMOND, model.Color.SPADE, model.Color.CLUB)
                            suits.forEach { label ->
                                 isEnabled = if(viewModel.equalSet){viewModel.lastSecondAnswer.rank < label.rank} else{true}
                                GameButton(
                                    label, isEnabled
                                ) {
                                    viewModel.lastSecondAnswer = label
                                    showPlayerCards = false
                                    currentPlayerIndexVar = afterChoosingSet(
                                        currentPlayerIndex = currentPlayerIndexVar,
                                        navController = navController,
                                        viewModel = viewModel
                                    )
                                    viewModel.lastSet=viewModel.currentSet
                                }
                            }
                        }
                    }

                }

            }


        }
        Button(
            onClick = { onOptionSelected("Check") },
            shape = CutCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(16.dp)
                .fillMaxHeight(0.3f)
                .align(Alignment.BottomCenter)


        ) {
            Text("Check", color = Color.White)
        }
    }
}


@Composable
fun GameButton(set: Rankable,  isEnabled: Boolean, onOptionSelected: (String) -> Unit) {
    Button(
        onClick = { onOptionSelected(set.str) },
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = if (isEnabled) Color(0xFF6200EA) else Color.Gray),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(set.str)
    }

}


@Composable
fun CheckButton(onOptionSelected: (String) -> Unit, modifier: Modifier) {
    Button(
        onClick = { onOptionSelected("Check") },
        shape = CutCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(4.dp)
            .fillMaxHeight(0.5f)


    ) {
        Text("Check", color = Color.White)
    }
}


fun afterChoosingSet(
    currentPlayerIndex: Int,
    viewModel: GameViewModel,
    navController: NavController
): Int {
    var currentPlayerIndexVar: Int = currentPlayerIndex
    if (currentPlayerIndex + 1 < viewModel.listOfPlayers.size) {
        currentPlayerIndexVar++
        viewModel.setCurrentUserIndex(currentPlayerIndexVar)


        navController.navigate("playerScreen") // Teraz przejdź do kolejnego gracza

        navController.navigate("playerScreen")
    } else {
        viewModel.setCurrentUserIndex(0)
        navController.navigate("playerScreen")
    }
    return currentPlayerIndexVar
}

fun toggleButtonsColumns() = listOf(
    listOf(model.Set.ONECARD, model.Set.PAIR, model.Set.TWOPAIRS),
    listOf(model.Set.THREE, model.Set.STRAIGHT, model.Set.FULL),
    listOf(model.Set.FOUR, model.Set.FLUSH, model.Set.ROYALFLUSH),

    )

fun cardOptions() = listOf(
    listOf(model.Figure.NINE, model.Figure.TEN, model.Figure.JACK, model.Figure.QUEEN),
    listOf(model.Figure.KING, model.Figure.ACE)
)


//@Preview(showBackground = true)
//@Composable
//fun GameScreenPreview() {
//    val dummyCards = listOf(
//        R.drawable.ace_karo, // Zamień te zasoby na rzeczywiste karty
//        R.drawable.king_kier,
//        R.drawable.queen_pik,
//        R.drawable.jack_trefl,
//        R.drawable.ten_karo
//    )
//    GameScreen(playerCards = dummyCards) {
//        println("Selected Option: $it")
//    }
//}