package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.data.SampleDaycares
import com.example.myapplication.ui.theme.BunooOrange
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Booking status enum
enum class BookingStatus {
    CONFIRMED, TRACKING, COMPLETED, CANCELLED
}

// Booking data model
data class Booking(
    val id: String,
    val daycareId: String,
    val childName: String,
    val date: Date,
    val sessionType: String,
    val timeRange: String,
    val status: BookingStatus,
    val hasPickupDrop: Boolean,
    val price: Int,
    val pickupTime: String? = null,
    val dropTime: String? = null
)

// Sample booking data
val sampleBookings = listOf(
    Booking(
        id = "1",
        daycareId = "1",
        childName = "Emily C.",
        date = Calendar.getInstance().apply { 
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DAY_OF_MONTH, 27)
        }.time,
        sessionType = "Full Day Program",
        timeRange = "8:00 AM - 5:00 PM",
        status = BookingStatus.CONFIRMED,
        hasPickupDrop = false,
        price = 800
    ),
    Booking(
        id = "2",
        daycareId = "2",
        childName = "Emily C.",
        date = Calendar.getInstance().apply { 
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DAY_OF_MONTH, 29)
        }.time,
        sessionType = "Afternoon Care + Pickup",
        timeRange = "8:00 AM - 5:00 PM",
        status = BookingStatus.TRACKING,
        hasPickupDrop = true,
        price = 55,
        pickupTime = "4:30 PM"
    ),
    Booking(
        id = "3",
        daycareId = "1",
        childName = "Emily C.",
        date = Calendar.getInstance().apply { 
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DAY_OF_MONTH, 31)
        }.time,
        sessionType = "Full Day Program",
        timeRange = "8:00 AM - 5:00 PM",
        status = BookingStatus.CONFIRMED,
        hasPickupDrop = false,
        price = 800
    ),
    Booking(
        id = "4",
        daycareId = "1",
        childName = "Emily C.",
        date = Calendar.getInstance().apply { 
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DAY_OF_MONTH, 20)
        }.time,
        sessionType = "Full Day Program",
        timeRange = "8:00 AM - 5:00 PM",
        status = BookingStatus.COMPLETED,
        hasPickupDrop = false,
        price = 800
    )
)

@Composable
fun BookingsScreen() {
    var selectedTab by remember { mutableStateOf("Current") }
    
    Scaffold(
        containerColor = Color(0xFFFFFBFE) // Same as profile section - light off-white
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Title
            Text(
                text = "Bookings",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Tab selector
            TabSelector(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Bookings list
            val upcomingBookings = sampleBookings.filter { 
                it.status != BookingStatus.COMPLETED && it.status != BookingStatus.CANCELLED 
            }
            val pastBookings = sampleBookings.filter { 
                it.status == BookingStatus.COMPLETED || it.status == BookingStatus.CANCELLED 
            }
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (selectedTab == "Current") {
                    item {
                        Text(
                            text = "Upcoming",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    items(upcomingBookings) { booking ->
                        BookingCard(booking = booking)
                    }
                } else {
                    item {
                        Text(
                            text = "Past",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    items(pastBookings) { booking ->
                        BookingCard(booking = booking)
                    }
                }
            }
        }
    }
}

@Composable
private fun TabSelector(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                Color(0xFFE5E5E5),
                RoundedCornerShape(12.dp)
            )
    ) {
        listOf("Current", "Past").forEach { tab ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (selectedTab == tab) BunooOrange.copy(alpha = 0.1f) else Color.Transparent,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onTabSelected(tab) }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = tab,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (selectedTab == tab) BunooOrange else Color.Black
                    )
                    if (selectedTab == tab) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(2.dp)
                                .background(BunooOrange, RoundedCornerShape(1.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookingCard(booking: Booking) {
    val daycare = SampleDaycares.firstOrNull { it.id == booking.daycareId }
    val dateFormat = SimpleDateFormat("EEE, MMM d", Locale.getDefault())
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Daycare icon placeholder (you can replace with actual image)
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        BunooOrange.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Daycare",
                    tint = BunooOrange,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Booking details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dateFormat.format(booking.date),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${booking.sessionType} for ${booking.childName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = "Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = booking.timeRange,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                if (booking.status == BookingStatus.COMPLETED) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Completed",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
            
            // Right side content
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    Icons.Default.CalendarToday,
                    contentDescription = "Calendar",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                when (booking.status) {
                    BookingStatus.CONFIRMED -> {
                        Button(
                            onClick = { /* Handle confirmed booking */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "Confirmed",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                    BookingStatus.TRACKING -> {
                        Button(
                            onClick = { /* Handle track ride */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BunooOrange
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.DirectionsCar,
                                    contentDescription = "Track",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Track Emily's Ride",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = "$${booking.price}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                    BookingStatus.COMPLETED -> {
                        Button(
                            onClick = { /* Handle completed booking */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "Completed",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                    BookingStatus.CANCELLED -> {
                        Button(
                            onClick = { /* Handle cancelled booking */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Text(
                                text = "Cancelled",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
} 