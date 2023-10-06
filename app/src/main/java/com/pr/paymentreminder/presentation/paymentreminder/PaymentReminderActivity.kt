package com.pr.paymentreminder.presentation.paymentreminder

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.compose.material.BottomNavigation
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.presentation.paymentreminder.fragments.GraphicFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.HomeFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.SettingsFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Deprecated(Constants.JAVA_DEPRECATED)
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

    @SuppressLint(Constants.UNUSED_MATERIAL)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content() {
        val navController = rememberNavController()

        val screens = listOf(
            CurrentScreen.Home,
            CurrentScreen.Graphic,
            CurrentScreen.Settings
        )

        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, screens)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = CurrentScreen.Home.route
            ) {
                val viewModel: HomeViewModel by viewModels()
                composable(CurrentScreen.Home.route) { HomeFragment(viewModel) }
                composable(CurrentScreen.Graphic.route) { GraphicFragment() }
                composable(CurrentScreen.Settings.route) { SettingsFragment() }
            }
        }
    }

    @Composable
    private fun BottomNavigationBar(
        navController: NavHostController,
        screens: List<CurrentScreen>
    ) {
        BottomNavigation (backgroundColor = Color.White) {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route

            screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

    sealed class CurrentScreen(val route: String, val icon: ImageVector) {
        object Home : CurrentScreen(Constants.HOME, Icons.Filled.Home)
        object Graphic : CurrentScreen(Constants.GRAPHIC, Icons.Filled.Info)
        object Settings : CurrentScreen(Constants.SETTINGS, Icons.Filled.Settings)
    }
}