package com.example.tipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipapp.components.InputField
import com.example.tipapp.ui.theme.TipAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipView()
                }
            }
        }
    }
}

@Composable
fun TipView() {
    Column(modifier =
    Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(
            color = Color.White,
        ),
       horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(40.dp))

        // total amount box
        TotalAmountView()

        Spacer(Modifier.height(12.dp))

        // tip controls
        TipControlView()

    }
}

@Composable
fun TotalAmountView() {
    Card(modifier = Modifier
        .fillMaxWidth(0.8f)
        .height(140.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.Black,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Per Person", style = TextStyle(fontSize = 20.sp, color = Color.White))
            Text(text = "$44.33", style = TextStyle(fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold))
        }
    }
}

@Preview
@Composable
fun TipControlView() {
    BillForm() {
        billAmount -> Log.d("bill", "$billAmount")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier, onValueChanged: (String) -> Unit) {
    val billState = remember {
        mutableStateOf("")
    }
    val isValidState = remember(billState.value) {
        billState.value.trim().isNotEmpty()
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    Card(modifier = Modifier
        .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 0.2.dp,
            color = Color.LightGray
        ),

        ) {
        Column(
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // text field
            InputField(
                valueState = billState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if(!isValidState) return@KeyboardActions
                    onValueChanged(billState.value.trim())

                    keyboardController?.hide()
                }
            )

            // split control

//                Box() {
//                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
//                        Text(text = "Split")
//
////                        Spacer(modifier =  Modifier.fillMaxWidth())
//
//                        Card(
//                            Modifier
//                                .height(50.dp)
//                                .width(50.dp), backgroundColor = Color.Black) {
//
//                        }
//                    }
//                }


            // tip

            // tip seek
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipAppTheme {
        TipView()
    }
}