package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.myapplication.data.SampleDaycares

@Composable
fun DaycareDetailsScreen(daycareId: String, onBack: () -> Unit, onBook: (String) -> Unit) {
    val daycare = SampleDaycares.firstOrNull { it.id == daycareId } ?: return
    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text(daycare.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = daycare.openHours)
            Spacer(Modifier.height(12.dp))
            Text("Facilities", style = MaterialTheme.typography.titleMedium)
            Text(daycare.facilities.joinToString(" • "))
            Spacer(Modifier.height(12.dp))
            Text("Reviews", style = MaterialTheme.typography.titleMedium)
            Text("\"Lovely staff and safe environment.\"")
            Spacer(Modifier.height(24.dp))
            Button(onClick = { onBook(daycare.id) }) { Text("Book Now") }
        }
    }
}

@Composable
fun BookingScreen(daycareId: String, onProceedPayment: () -> Unit, onBack: () -> Unit) {
    var date by remember { mutableStateOf("Tue, April 28") }
    var start by remember { mutableStateOf("9:00 am") }
    var end by remember { mutableStateOf("10:00 am") }
    var pickDrop by remember { mutableStateOf(true) }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Booking Summary", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date") }, modifier = Modifier.fillMaxWidth())
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(value = start, onValueChange = { start = it }, label = { Text("Start") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = end, onValueChange = { end = it }, label = { Text("End") }, modifier = Modifier.weight(1f))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = pickDrop, onCheckedChange = { pickDrop = it })
                Text("Pick & Drop")
            }
            Spacer(Modifier.height(8.dp))
            Divider()
            Spacer(Modifier.height(8.dp))
            Text("₹ 3000")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onProceedPayment, modifier = Modifier.fillMaxWidth()) { Text("Proceed Payment") }
        }
    }
}

@Composable
fun PaymentScreen(daycareId: String, onPaid: () -> Unit, onBack: () -> Unit) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Payment", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text("Credit Card • Google Pay • Apple Pay")
            Spacer(Modifier.height(24.dp))
            Button(onClick = onPaid, modifier = Modifier.fillMaxWidth()) { Text("Pay ₹ 3000") }
        }
    }
}

@Composable
fun BookingConfirmationScreen(onDone: () -> Unit) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)) {
            Text("Booking Confirmed", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            Text("Your child will be picked up at 9:45 am")
            Spacer(Modifier.height(24.dp))
            Button(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Done") }
        }
    }
} 