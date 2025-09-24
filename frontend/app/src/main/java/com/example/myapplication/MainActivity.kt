package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.BookingConfirmationScreen
import com.example.myapplication.ui.screens.BookingScreen
import com.example.myapplication.ui.screens.BookingsScreen
import com.example.myapplication.ui.screens.ChildProfileScreen
import com.example.myapplication.ui.screens.DaycareDetailsScreen
import com.example.myapplication.ui.screens.HomeScreen
import com.example.myapplication.ui.screens.LoginScreen
import com.example.myapplication.ui.screens.PaymentScreen
import com.example.myapplication.ui.screens.ProfileScreen
import com.example.myapplication.ui.screens.SignUpScreen
import com.example.myapplication.ui.screens.SupportScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.BunooOrange
import androidx.compose.foundation.layout.padding
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.navigationBarsPadding

class MainActivity : ComponentActivity() {
    private var keepOnSplash: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { keepOnSplash }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Once the first composition happens, dismiss the splash
            LaunchedEffect(Unit) { keepOnSplash = false }

            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    DaycareApp()
                }
            }
        }
    }
}

private data class TopDestination(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)

private val topDestinations = listOf(
    TopDestination("home", Icons.Default.Home, "Home"),
    TopDestination("bookings", Icons.Default.List, "Bookings"),
    TopDestination("profile", Icons.Default.AccountCircle, "Profile"),
    TopDestination("support", Icons.Default.Help, "Support")
)

@Composable
fun DaycareApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Check if current route is an auth screen (no bottom bar needed)
    val isAuthScreen = currentRoute == "login" || currentRoute == "signup"

    androidx.compose.material3.Scaffold(
        bottomBar = {
            if (!isAuthScreen) {
                NavigationBar(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(70.dp),
                    containerColor = androidx.compose.ui.graphics.Color.White
                ) {
                    val destination: NavDestination? = backStackEntry?.destination
                    topDestinations.forEach { item ->
                        val selected = currentRoute?.startsWith(item.route) == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { androidx.compose.material3.Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BunooOrange,
                                selectedTextColor = BunooOrange,
                                indicatorColor = BunooOrange.copy(alpha = 0.12f),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "login", modifier = androidx.compose.ui.Modifier.padding(
            paddingValues
        )) {
            // Authentication screens
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNavigateToSignUp = {
                        navController.navigate("signup")
                    }
                )
            }
            composable("signup") {
                SignUpScreen(
                    onSignUpSuccess = {
                        navController.navigate("login") {
                            popUpTo("signup") { inclusive = true }
                        }
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            
            // Main app screens
            composable("home") {
                HomeScreen(
                    onOpenDetails = { daycareId ->
                        navController.navigate("details/$daycareId")
                    }
                )
            }
            composable("bookings") { BookingsScreen() }
            composable("profile") {
                ProfileScreen(
                    onBack = { navController.popBackStack() },
                    onEditProfile = { 
                        // Navigate to profile edit screen (can be implemented later)
                        // navController.navigate("editProfile")
                    },
                    onAddChild = {
                        navController.navigate("childProfile")
                    },
                    onEditChild = { childId ->
                        navController.navigate("childProfile/$childId")
                    },
                    onSignOut = {
                        // Handle sign out logic
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            composable("support") { SupportScreen(onBack = { navController.popBackStack() }) }
            composable(
                route = "details/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val daycareId = backStackEntry.arguments?.getString("id").orEmpty()
                DaycareDetailsScreen(
                    daycareId = daycareId,
                    onBack = { navController.popBackStack() },
                    onBook = { id -> navController.navigate("booking/$id") }
                )
            }
            composable(
                route = "booking/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val daycareId = backStackEntry.arguments?.getString("id").orEmpty()
                BookingScreen(
                    daycareId = daycareId,
                    onProceedPayment = { daycareFee, serviceFee, totalFee ->
                        navController.navigate("payment/$daycareId/$daycareFee/$serviceFee/$totalFee")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = "payment/{id}/{daycareFee}/{serviceFee}/{totalFee}",
                arguments = listOf(
                    navArgument("id") { type = NavType.StringType },
                    navArgument("daycareFee") { type = NavType.IntType },
                    navArgument("serviceFee") { type = NavType.IntType },
                    navArgument("totalFee") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val daycareId = backStackEntry.arguments?.getString("id").orEmpty()
                val daycareFee = backStackEntry.arguments?.getInt("daycareFee") ?: 0
                val serviceFee = backStackEntry.arguments?.getInt("serviceFee") ?: 0
                val totalFee = backStackEntry.arguments?.getInt("totalFee") ?: 0
                PaymentScreen(
                    daycareId = daycareId,
                    daycareFee = daycareFee,
                    serviceFee = serviceFee,
                    totalFee = totalFee,
                    onPaid = { navController.navigate("confirmation") },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("confirmation") {
                BookingConfirmationScreen(onGoToBookings = {
                    navController.navigate("bookings") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                })
            }
            composable("childProfile") { 
                ChildProfileScreen(
                    onBack = { navController.popBackStack() },
                    onSave = { child ->
                        // Handle saving child profile
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = "childProfile/{childId}",
                arguments = listOf(navArgument("childId") { type = NavType.StringType })
            ) { backStackEntry ->
                val childId = backStackEntry.arguments?.getString("childId")
                ChildProfileScreen(
                    childId = childId,
                    onBack = { navController.popBackStack() },
                    onSave = { child ->
                        // Handle saving child profile
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}