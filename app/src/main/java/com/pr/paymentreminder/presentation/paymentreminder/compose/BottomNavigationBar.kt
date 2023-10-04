package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity

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
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
