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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.Car
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.data.Child
import com.example.myapplication.data.SampleChildren
import com.example.myapplication.data.SampleDaycares
import com.example.myapplication.ui.theme.BunooOrange

data class Session(
    val id: String,
    val name: String,
    val timeRange: String,
    val price: Int,
    val isAvailable: Boolean = true
)

data class ServiceOption(
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val isEnabled: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    daycareId: String, 
    onProceedPayment: () -> Unit, 
    onBack: () -> Unit
) {
    val daycare = SampleDaycares.firstOrNull { it.id == daycareId } ?: return
    val children = SampleChildren
    
    var selectedChildren by remember { mutableStateOf<List<Child>>(emptyList()) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedSession by remember { mutableStateOf<Session?>(null) }
    var showChildDropdown by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showSessionPicker by remember { mutableStateOf(false) }
    
    // Service options
    var pickupService by remember { mutableStateOf(false) }
    var dropService by remember { mutableStateOf(false) }
    var dropByParents by remember { mutableStateOf(true) }
    var pickupByParents by remember { mutableStateOf(true) }
    
    val sessions = listOf(
        Session("1", "EARLY DROP-OFF", "7:45 AM - 8:30 AM", 200),
        Session("2", "FULLDAY", "8:30 AM - 5:00 PM", 800, false),
        Session("3", "MORNING", "8:30 AM - 1:00 PM", 500),
        Session("4", "AFTERNOON", "1:00 PM - 5:00 PM", 400),
        Session("5", "LATE PICK-UP", "5:00 PM - 6:00 PM", 150)
    )
    
    val serviceOptions = listOf(
        ServiceOption("pickup", "Pickup Service", "We'll pick up your child", 200, pickupService),
        ServiceOption("drop", "Drop Service", "We'll drop off your child", 200, dropService),
        ServiceOption("drop_parents", "Drop by Parents", "You'll drop off your child", 0, dropByParents),
        ServiceOption("pickup_parents", "Pickup by Parents", "You'll pick up your child", 0, pickupByParents)
    )
    
    // Calculate total price
    val daycareFee = selectedSession?.price ?: 0
    val serviceFee = (if (pickupService) 200 else 0) + (if (dropService) 200 else 0)
    val totalFee = daycareFee + serviceFee

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Summary") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            
            // Daycare Information Section
            item {
                DaycareInfoCard(daycare = daycare)
            }
            
            // Child Selection and Date Section
            item {
                ChildAndDateCard(
                    children = children,
                    selectedChildren = selectedChildren,
                    onChildrenSelected = { selectedChildren = it },
                    showChildDropdown = showChildDropdown,
                    onShowChildDropdown = { showChildDropdown = it },
                    selectedDate = selectedDate,
                    onDateSelected = { selectedDate = it },
                    showDatePicker = showDatePicker,
                    onShowDatePicker = { showDatePicker = it }
                )
            }
            
            // Session Selection (only show if date is selected)
            if (selectedDate.isNotEmpty()) {
                item {
                    SessionSelectionCard(
                        sessions = sessions,
                        selectedSession = selectedSession,
                        onSessionSelected = { selectedSession = it },
                        showSessionPicker = showSessionPicker,
                        onShowSessionPicker = { showSessionPicker = it }
                    )
                }
            }
            
            // Service Options Section
            item {
                ServiceOptionsCard(
                    serviceOptions = serviceOptions,
                    onPickupServiceChanged = { pickupService = it },
                    onDropServiceChanged = { dropService = it },
                    onDropByParentsChanged = { dropByParents = it },
                    onPickupByParentsChanged = { pickupByParents = it }
                )
            }
            
            // Price Breakdown Section
            item {
                PriceBreakdownCard(
                    daycareFee = daycareFee,
                    serviceFee = serviceFee,
                    totalFee = totalFee
                )
            }
            
            // Proceed Payment Button
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onProceedPayment,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BunooOrange
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = selectedChildren.isNotEmpty() && selectedDate.isNotEmpty() && selectedSession != null
                ) {
                    Text(
                        text = "Proceed Payment",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DaycareInfoCard(daycare: com.example.myapplication.data.Daycare) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Daycare Image
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
            
            // Daycare Info
            Column {
                Text(
                    text = daycare.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = daycare.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun ChildAndDateCard(
    children: List<Child>,
    selectedChildren: List<Child>,
    onChildrenSelected: (List<Child>) -> Unit,
    showChildDropdown: Boolean,
    onShowChildDropdown: (Boolean) -> Unit,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    showDatePicker: Boolean,
    onShowDatePicker: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Child Selection
            Text(
                text = "Select Children",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Box {
                OutlinedTextField(
                    value = if (selectedChildren.isEmpty()) "Select children" else "${selectedChildren.size} child(ren) selected",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShowChildDropdown(true) },
                    shape = RoundedCornerShape(8.dp)
                )
                
                DropdownMenu(
                    expanded = showChildDropdown,
                    onDismissRequest = { onShowChildDropdown(false) }
                ) {
                    children.forEach { child ->
                        DropdownMenuItem(
                            text = { 
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = selectedChildren.contains(child),
                                        onCheckedChange = { isChecked ->
                                            if (isChecked) {
                                                onChildrenSelected(selectedChildren + child)
                                            } else {
                                                onChildrenSelected(selectedChildren - child)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(child.name)
                                }
                            },
                            onClick = {
                                if (selectedChildren.contains(child)) {
                                    onChildrenSelected(selectedChildren - child)
                                } else {
                                    onChildrenSelected(selectedChildren + child)
                                }
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Date Selection
            Text(
                text = "Select Date",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedTextField(
                value = selectedDate.ifEmpty { "Select date" },
                onValueChange = {},
                readOnly = true,
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Calendar")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onShowDatePicker(true) },
                shape = RoundedCornerShape(8.dp)
            )
            
            // Simple date picker (in a real app, you'd use a proper date picker)
            if (showDatePicker) {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val dates = listOf("Mon, Sep 15", "Tue, Sep 16", "Wed, Sep 17", "Thu, Sep 18", "Fri, Sep 19")
                    items(dates) { date ->
                        Button(
                            onClick = {
                                onDateSelected(date)
                                onShowDatePicker(false)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedDate == date) BunooOrange else Color.Gray.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = date,
                                color = if (selectedDate == date) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SessionSelectionCard(
    sessions: List<Session>,
    selectedSession: Session?,
    onSessionSelected: (Session) -> Unit,
    showSessionPicker: Boolean,
    onShowSessionPicker: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Pick Sessions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            sessions.forEach { session ->
                SessionOption(
                    session = session,
                    isSelected = selectedSession?.id == session.id,
                    onSelected = { onSessionSelected(session) }
                )
                if (session != sessions.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun SessionOption(
    session: Session,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    val backgroundColor = when {
        !session.isAvailable -> Color.Gray.copy(alpha = 0.1f)
        isSelected -> BunooOrange.copy(alpha = 0.2f)
        else -> Color.White
    }
    
    val textColor = when {
        !session.isAvailable -> Color.Gray
        isSelected -> BunooOrange
        else -> Color.Black
    }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) BunooOrange else Color.Gray.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = session.isAvailable) { onSelected() }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = session.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
            Text(
                text = session.timeRange,
                style = MaterialTheme.typography.bodySmall,
                color = textColor.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun ServiceOptionsCard(
    serviceOptions: List<ServiceOption>,
    onPickupServiceChanged: (Boolean) -> Unit,
    onDropServiceChanged: (Boolean) -> Unit,
    onDropByParentsChanged: (Boolean) -> Unit,
    onPickupByParentsChanged: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Pick & Drop Service",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Pickup Service
            ServiceOptionRow(
                icon = Icons.Default.Person,
                title = "Pickup Service",
                description = "We'll pick up your child",
                price = "₹200",
                isEnabled = serviceOptions[0].isEnabled,
                onToggle = onPickupServiceChanged
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Drop Service
            ServiceOptionRow(
                icon = Icons.Default.Person,
                title = "Drop Service",
                description = "We'll drop off your child",
                price = "₹200",
                isEnabled = serviceOptions[1].isEnabled,
                onToggle = onDropServiceChanged
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Drop by Parents
            ServiceOptionRow(
                icon = Icons.Default.Person,
                title = "Drop by Parents",
                description = "You'll drop off your child",
                price = "Free",
                isEnabled = serviceOptions[2].isEnabled,
                onToggle = onDropByParentsChanged
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Pickup by Parents
            ServiceOptionRow(
                icon = Icons.Default.Person,
                title = "Pickup by Parents",
                description = "You'll pick up your child",
                price = "Free",
                isEnabled = serviceOptions[3].isEnabled,
                onToggle = onPickupByParentsChanged
            )
        }
    }
}

@Composable
private fun ServiceOptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    price: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = price,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = if (price == "Free") Color.Green else MaterialTheme.colorScheme.onSurface
            )
            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = BunooOrange,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }
    }
}

@Composable
private fun PriceBreakdownCard(
    daycareFee: Int,
    serviceFee: Int,
    totalFee: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Price Breakdown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Daycare Fee
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Daycare Fee",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "₹$daycareFee",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            // Service Fee (only show if > 0)
            if (serviceFee > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Service Fee",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "₹$serviceFee",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))
            
            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "₹$totalFee",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = BunooOrange
                )
            }
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