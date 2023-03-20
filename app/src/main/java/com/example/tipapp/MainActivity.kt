package com.example.tipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.tipapp.util.calculateTipAmount
import com.example.tipapp.util.calculateTotalTipPerPerson
import com.example.tipapp.widgets.CircularIconButton

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

    val splitCounterState = remember {
        mutableStateOf(1)
    }


    val tipAmountState = remember() {
        mutableStateOf( 0.0)
    }


    val sliderPositionState = remember {
        mutableStateOf(value = 0f)
    }


    val billState = remember {
        mutableStateOf("")
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()

    val billPerPersonValue = billState.value.ifEmpty { "0.0" }


    Column(modifier =
    Modifier
        .background(
            color = Color.White,
        ),
       horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(40.dp))

        // total amount box
        TotalPerPersonView(
            totalTip = calculateTotalTipPerPerson(
            totalBill = billPerPersonValue.toDouble(),
            splitBy = splitCounterState.value,
            tipPercentage = tipPercentage
        ))

        Spacer(Modifier.height(12.dp))

        // tip controls
        BillForm(modifier = Modifier,
            splitCounterState = splitCounterState,
            tipAmountState = tipAmountState,
            sliderPositionState = sliderPositionState,
            billState = billState,
            tipPercentage = tipPercentage,
        ) {}

    }
}

@Composable
fun TotalPerPersonView(totalTip: Double = 34.0) {
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
            Text(text = "$ ${"%.2f".format(totalTip)}", style = TextStyle(fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold))
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier,
             splitCounterState : MutableState<Int>,
             tipAmountState: MutableState<Double>,
             sliderPositionState: MutableState<Float>,
             tipPercentage: Int,
             billState: MutableState<String>,
             onValueChanged: (String) -> Unit
) {

    val isValidState = remember(billState.value) {
        billState.value.trim().isNotEmpty()
    }


    val keyboardController = LocalSoftwareKeyboardController.current



    Card(
        modifier = modifier
        .fillMaxWidth(0.95f)
            .padding(start = 12.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 0.5.dp,
            color = Color.LightGray
        ),

        ) {
        Column(
            verticalArrangement = Arrangement.Top,
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

            if (isValidState) {
                Row( horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text(text = "Split", modifier = Modifier.align(alignment = Alignment.CenterVertically))

                    Spacer(Modifier.fillMaxWidth(0.5F))

                    Row() {
                        CircularIconButton(icon = Icons.Default.Remove,
                            onClick = {
                                if (splitCounterState.value > 1) {
                                    splitCounterState.value -= 1
                                }
                            }
                        )

                        Text(text = "${ splitCounterState.value }", modifier = Modifier.align(Alignment.CenterVertically))

                        CircularIconButton(icon = Icons.Default.Add,
                            onClick = {
                                splitCounterState.value += 1
                              }
                        )
                    }
                }

                // Tip Row

                Row( horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
                    Text(text = "Tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))

                    Spacer(Modifier.fillMaxWidth(0.75F))


                    Text(text = "$ ${"%.2f".format(tipAmountState.value)}", modifier = Modifier.align(Alignment.CenterVertically))

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center)
                {
                    Text(text = "$tipPercentage %")

                    Spacer(modifier = Modifier.width(50.dp))
                    
                    Slider(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        value = sliderPositionState.value, onValueChange = {
                       newValue ->  sliderPositionState.value = newValue
                            tipAmountState.value = calculateTipAmount(billState.value.toDouble(), tipPercentage)
                    },
                        steps = 5,
                    )

                }
            } else {
                Box() {}
            }

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