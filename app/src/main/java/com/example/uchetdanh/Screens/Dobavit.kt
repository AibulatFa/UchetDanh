package com.example.uchetdanh.Screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home



import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.uchetdanh.R
import com.example.uchetdanh.firebase.Product
import com.example.uchetdanh.navigation.Screen
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun DobavitScreen(navController: NavHostController) {
    val firestore = Firebase.firestore

    TopAppBar {

        IconButton(onClick = { navController.navigate(Screen.Start.route) }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    )
    {


        Column(

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            var product by remember { mutableStateOf(Product("", "", "", "", "")) }


            OutlinedTextField(
                value = product.name,
                onValueChange = { product = product.copy(name = it) },
                label = { Text("Наименование") }
            )

            OutlinedTextField(
                value = product.article,
                onValueChange = { product = product.copy(article = it) },
                label = { Text("Артикул") }
            )

            OutlinedTextField(
                value = product.model,
                onValueChange = { product = product.copy(model = it) },
                label = { Text("Модель") }
            )

            OutlinedTextField(
                value = product.manufacturer,
                onValueChange = { product = product.copy(manufacturer = it) },
                label = { Text("Производитель") }
            )

            OutlinedTextField(
                value = product.price,
                onValueChange = { product = product.copy(price = it) },
                label = { Text("Цена") }
            )

            Button(
                onClick = {
                    saveDataToFirestore(product)
                },
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(
                    start = 100.dp,
                    top = 12.dp,
                    end = 100.dp,
                    bottom = 12.dp
                )
            ) {
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Добавить")
            }
        }
    }
}

fun saveDataToFirestore(product: Product) {
    val firestore = Firebase.firestore

    firestore.collection("tovar").add(product)
        .addOnSuccessListener { documentReference ->
            // Действия при успешном сохранении данных
        }
        .addOnFailureListener { e ->
            // Действия при ошибке сохранения данных
        }
}