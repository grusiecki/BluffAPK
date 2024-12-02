package com.example.bluff

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.FirebaseApp
import model.AuthViewModel
import model.GameViewModel

val boldItalic = FontFamily(
    Font(R.font.roboto_bold_italic)
)
    val dummyCards = listOf(
        R.drawable.ace_karo, // Zamień te zasoby na rzeczywiste karty
        R.drawable.king_kier,
        R.drawable.queen_pik,
        R.drawable.jack_trefl,
        R.drawable.ten_karo
    )
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
//        val app = FirebaseApp.getInstance()
//        Log.d("FirebaseApp", "Firebase initialized: ${app.name}")
        setContent {
            AppNavigator()

        }
    }
}

@Composable
fun MyScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to the Bluff",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = boldItalic,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("pickNumberOfPlayersScreen")}) {
                Text(text = "Start Offline Game")
            }
            Button(onClick = { navController.navigate("authScreen")}) {
                Text(text = "Start Online Game")
            }
        }
    }
}
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val viewModel: GameViewModel = viewModel()
    var currentUserIndex: Int
    NavHost(navController, startDestination = "mainScreen") {

        composable("mainScreen") { MyScreen(navController) }
        composable("pickNumberOfPlayersScreen") { PickNumberOfPlayer(navController) }
        composable("authScreen"){ AuthScreen(AuthViewModel()) }

        composable(
            "setPlayersName/{playerCount}",
            arguments = listOf(navArgument("playerCount") { type = NavType.IntType })
        ) { backStackEntry ->
            val playerCount = backStackEntry.arguments?.getInt("playerCount") ?: 2
            PlayerNameFlow(navController, playerCount, viewModel)
        }
        composable(
            "gameScreen",
        ) {

            GameScreen( navController = navController,  onOptionSelected = {
                println("Selected option in GameScreen: ")},onGameEnd = { println("End") }, viewModel = viewModel)
            }
        composable("playerScreen") {

            PlayerScreen(viewModel, navController)
        }
        composable("whoLooseScreen") {

            WhoLooseScreen(viewModel, navController)
        }
        composable("allCardsScreen") {

            AllCardsScreen( navController, viewModel)
        }
        composable("endGameScreen") {

            EndGameScreen( navController, viewModel)
        }
        }

    }





@Preview(showBackground = true)
@Composable
fun PreviewMyScreen() {
    AppNavigator()
}