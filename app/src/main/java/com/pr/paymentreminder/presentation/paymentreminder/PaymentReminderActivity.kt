package com.pr.paymentreminder.presentation.paymentreminder

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pr.paymentreminder.R

import com.pr.paymentreminder.presentation.paymentreminder.fragments.GraphicFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.HomeFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.SettingsFragment

class PaymentReminderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

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

    //TODO: Set and view fragments
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation(backgroundColor = Color(0xea, 0xdd, 0xff)) {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        selected = navController.currentBackStackEntry?.destination?.route == "Home",
                        onClick = { navController.navigate("Home") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                        selected = navController.currentBackStackEntry?.destination?.route == "Settings",
                        onClick = { navController.navigate("Graphic") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Warning, contentDescription = null) },
                        selected = navController.currentBackStackEntry?.destination?.route == "Graphic",
                        onClick = { navController.navigate("Graphic") }
                    )
                }
            }
        ) {
            NavHost(navController, startDestination = "Home") {
                composable("Home") { HomeFragment() }
                composable("Settings") { SettingsFragment() }
                composable("Graphic") { GraphicFragment() }
            }
        }
    }
}