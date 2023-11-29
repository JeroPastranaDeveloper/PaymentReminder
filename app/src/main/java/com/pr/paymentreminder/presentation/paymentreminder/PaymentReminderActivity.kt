package com.pr.paymentreminder.presentation.paymentreminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.compose.material.BottomNavigation
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.notifications.AlarmReceiver
import com.pr.paymentreminder.presentation.paymentreminder.fragments.GraphicFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.HomeFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.SettingsFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.HomeViewModel
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class PaymentReminderActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val context: Context = this@PaymentReminderActivity

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkNotificationPermissions()

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val dialog = AlertDialog.Builder(this@PaymentReminderActivity)
                    .setTitle(R.string.exit_question)
                    .setMessage(R.string.exit_confirmation)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        finish()
                    }
                    .setNegativeButton(R.string.no, null)
                    .create()
                dialog.show()
            }
        })

        val services = homeViewModel.services.value
        for (service in services) {
            scheduleNotification(service)
        }

        setContent {
            Content()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun scheduleNotification(service: Service) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val sdf = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        val date = sdf.parse(service.date)
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 16)
            set(Calendar.MINUTE, 19)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                val settingsViewModel: SettingsViewModel by viewModels()
                composable(CurrentScreen.Home.route) { HomeFragment(homeViewModel) }
                composable(CurrentScreen.Graphic.route) { GraphicFragment() }
                composable(CurrentScreen.Settings.route) { SettingsFragment(settingsViewModel) }
            }
        }
    }

    @Composable
    private fun BottomNavigationBar(
        navController: NavHostController,
        screens: List<CurrentScreen>
    ) {
        BottomNavigation(backgroundColor = Color.White) {
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