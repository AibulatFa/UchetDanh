package com.example.uchetdanh.Screens

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.uchetdanh.R
import com.example.uchetdanh.navigation.Screen
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

@Composable
fun DocumentsScreen(navController: NavHostController) {
    TopAppBar {
        IconButton(onClick = { navController.navigate(Screen.Start.route) }) {
            Icon(Icons.Filled.Home, contentDescription = "Home")
        }
    }

    val documents = remember { mutableStateListOf<String>() }

    Box(modifier = Modifier.fillMaxSize()) {
        ColumnWithCenteredContent {
            FileUploadButton(documents)
            Spacer(modifier = Modifier.height(16.dp))
            if (documents.isNotEmpty()) {
                DocumentList(documents)
            }
        }
    }
}

@Composable
fun FileUploadButton(documents: MutableList<String>) {
    val context = LocalContext.current
    val selectedFiles = remember { mutableStateListOf<Pair<Uri, String>>() }

    val getContent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) { uris ->
            selectedFiles.clear()
            selectedFiles.addAll(uris.map { uri ->
                val fileName = getFileName(context, uri)
                uri to fileName
            })
            uploadFiles(context, selectedFiles, documents)
        }

    Button(onClick = { getContent.launch("*/*") }) {
        Text("Выбрать файл")
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (selectedFiles.isNotEmpty()) {
        Column {
            for (file in selectedFiles) {
                Text("Загруженный файл: ${file.second}")
            }
        }
    }
}

fun uploadFiles(context: Context, files: List<Pair<Uri, String>>, documents: MutableList<String>) {
    val storageRef: StorageReference = Firebase.storage.reference

    for (file in files) {
        val uri = file.first
        val fileName = file.second

        val fileRef: StorageReference = storageRef.child(fileName)
        fileRef.putFile(uri)
            .addOnSuccessListener {
                Log.d("Upload", "Файл $fileName успешно загружен в Cloud Storage")
                documents.add(fileName) // Добавить имя файла в список документов
            }
            .addOnFailureListener { exception ->
                Log.e(
                    "Upload",
                    "Ошибка при загрузке файла $fileName в Cloud Storage: ${exception.message}"
                )
            }
    }
}

@SuppressLint("Range")
fun getFileName(context: Context, uri: Uri): String {
    var fileName = ""
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return fileName
}

@Composable
fun ColumnWithCenteredContent(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun DocumentList(documents: List<String>) {
    Column {
        for (document in documents) {
            Text("Документ: $document")
        }
    }
}
