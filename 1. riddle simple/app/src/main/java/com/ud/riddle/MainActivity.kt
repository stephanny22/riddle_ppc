package com.ud.riddle

import android.app.GameState
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ud.riddle.models.enum.GameStateEnum
import com.ud.riddle.models.enum.Player
import com.ud.riddle.ui.theme.RiddleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RiddleAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen(){
    val context = LocalContext.current
    var name by remember{ mutableStateOf( value = "") }

    var gameState by remember{ mutableStateOf( value = GameStateEnum.CREATING_PLAYERS) }

    val players = remember { mutableStateListOf<Player>() }

    val secret = "cactus"
    var impostorPosition:Int?
    var positionClue = 0

    when(gameState){

        GameStateEnum.CREATING_PLAYERS -> {
            Column (modifier = Modifier.fillMaxSize().padding(all=20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text( text = "Input a name player")
                TextField(
                    label = { Text( text= "Label")},
                    value= name,
                    onValueChange = {
                        name = it
                    }
                )

                Button(onClick = {
                    players.add(Player(name=name))
                    name = ""
                }){Text(text="Add")}

                Button(onClick = {
                    players.shuffle()
                    impostorPosition = players.indices.random()
                    players[impostorPosition].isImpostor=true

                    gameState = GameStateEnum.SHOWING_CLUE
                })
                {Text(text="Start")}
                if (players.size > 0){
                    Text(text= "players:")

                    for (player in players){
                        Text(text= player.name)
                    }
                }
            }
        }
        GameStateEnum.SHOWING_CLUE -> {
            var currentPlayer = players[positionClue]

            Column (modifier = Modifier.fillMaxSize().padding(all=20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "It's turn of ${currentPlayer.name}")

                Button(onClick = {
                    val isImpostor = currentPlayer.isImpostor
                    if(isImpostor){
                        Toast.makeText(context,  "You are the impostor",  Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "You are innocent, the word is $secret", Toast.LENGTH_LONG).show()
                    }
                }) { Text(text = "Show clue") }


                Button(onClick = {
                    currentPlayer = players[positionClue]
                    positionClue++

                    if (positionClue == players.size) {
                        gameState = GameStateEnum.IN_TURN
                    }

                }) { Text(text = "Next") }
            }
        }
        GameStateEnum.IN_TURN -> {

        }
        GameStateEnum.END -> {

        }
    }
}
