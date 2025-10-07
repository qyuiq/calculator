package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AllScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun InterVallues(
    orderSum: String,
    onOrderSumChange: (String) -> Unit,
    dishNumber: String,
    onDishNumberChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Сумма заказа:")

        OutlinedTextField(
            value = orderSum,
            onValueChange = onOrderSumChange,
            label = { Text("Введите сумму") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Количество блюд:")

        OutlinedTextField(
            value = dishNumber,
            onValueChange = onDishNumberChange,
            label = { Text("Введите количество") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun TipSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Slider(
        modifier = Modifier.padding(10.dp),
        valueRange = 0f..25f,
        value = sliderPosition,
        onValueChange = onPositionChange
    )
}

@Composable
fun DiscountSelector(selectedDiscount: Int) {
    val discounts = listOf(3, 5, 7, 10)
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Скидка:",
            fontSize = 20.sp,
        )
        discounts.forEach { discount ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(
                    selected = (discount == selectedDiscount),
                    onClick = {},
                    enabled = false
                )
                Text(
                    text = "$discount%",
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
fun AllScreen(modifier: Modifier = Modifier) {
    var orderSum by remember { mutableStateOf("") }
    var dishNumber by remember { mutableStateOf("") }
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val selectedDiscount = when (dishNumber.toIntOrNull() ?: 0) {
        in 1..2 -> 3
        in 3..5 -> 5
        in 6..10 -> 7
        in 11..Int.MAX_VALUE -> 10
        else -> 0
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        InterVallues(
            orderSum = orderSum,
            onOrderSumChange = { orderSum = it },
            dishNumber = dishNumber,
            onDishNumberChange = { dishNumber = it }
        )


        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Чаевые:",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 20.sp,
            )
        }
        TipSlider(
            sliderPosition = sliderPosition,
            onPositionChange = { sliderPosition = it }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp)
        ) {
            Text("0", style = MaterialTheme.typography.headlineMedium)
            Text("25", style = MaterialTheme.typography.headlineMedium)
        }

        DiscountSelector(selectedDiscount = selectedDiscount)
        Spacer(modifier = Modifier.height(0.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AllScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
