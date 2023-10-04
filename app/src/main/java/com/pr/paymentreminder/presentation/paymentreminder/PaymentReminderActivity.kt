package com.pr.paymentreminder.presentation.paymentreminder

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.paymentreminder.compose.BottomNavigationBar
import com.pr.paymentreminder.presentation.paymentreminder.fragments.GraphicFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.HomeFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.SettingsFragment

class PaymentReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    sealed class Screen(val route: String, val icon: ImageVector) {
        object Home : Screen("home", Icons.Filled.Home)
        object Settings : Screen("settings", Icons.Filled.Settings)
        object Graphic : Screen("graphic", Icons.Filled.Info)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.exit_question)
            .setMessage(R.string.exit_confirmation)
            .setPositiveButton(R.string.yes) { _, _ ->
                finish()
            }
            .setNegativeButton(R.string.no, null)
            .create()
        dialog.show()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content() {
        val navController = rememberNavController()
        val items = listOf(Screen.Home, Screen.Settings, Screen.Graphic)

        Scaffold(
            bottomBar = { BottomNavigationBar(navController, items) }
        ) {
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { HomeFragment() }
                composable(Screen.Settings.route) { SettingsFragment() }
                composable(Screen.Graphic.route) { GraphicFragment() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(
        navController: NavHostController,
        items: List<PaymentReminderActivity.Screen>
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            //backgroundColor = Color(0xea, 0xdd, 0xff),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        Log.d("PaymentReminderActivity", "Navigating to ${screen.route}")
                        if (currentRoute != screen.route) {
                            navController.navigate(Screen.Home.route, Screen.Home.) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }
        }
    }
}