package com.example.bluff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.Card
import model.Deck

class ListOfCards : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()

        }
    }
}
@Composable
fun ListOfCardsScreen(navController: NavHostController) {
    val deck = Deck()
    CardListScreen(deck.deck)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen(deck: List<Card>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deck of Cards") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(deck) { card ->
                CardRow(card)
            }
        }
    }
}

@Composable
fun CardRow(card: Card) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Image(
            painter = painterResource(id = card.imageResId),
            contentDescription = "${card.figure} of ${card.color}",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "${card.figure} of ${card.color}",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}