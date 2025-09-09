package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(onBack: () -> Unit, onEditChild: () -> Unit) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Profile")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onEditChild, modifier = Modifier.fillMaxWidth()) { Text("Child Profile") }
        }
    }
}

@Composable
fun ChildProfileScreen(onBack: () -> Unit) {
    val name = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val allergies = remember { mutableStateOf("") }
    val notes = remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Child Profile")
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = age.value, onValueChange = { age.value = it }, label = { Text("Age") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = allergies.value, onValueChange = { allergies.value = it }, label = { Text("Allergies") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = notes.value, onValueChange = { notes.value = it }, label = { Text("Notes") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Save") }
        }
    }
}

@Composable
fun SupportScreen(onBack: () -> Unit) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Support")
            Spacer(Modifier.height(12.dp))
            Text("FAQ • Chat • Call")
        }
    }
} 