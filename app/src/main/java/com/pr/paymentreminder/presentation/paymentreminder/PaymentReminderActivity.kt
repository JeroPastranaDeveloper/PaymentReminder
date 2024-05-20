package com.pr.paymentreminder.presentation.paymentreminder

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pr.paymentreminder.R
import com.pr.paymentreminder.android_versions.hasT33
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Permissions
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomDialog
import com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.graphic.GraphicViewModel
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewModel
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.payments_history.PaymentsHistoryViewModel
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsFragment
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentReminderActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val graphicViewModel: GraphicViewModel by viewModels()
    private val paymentsHistoryViewModel: PaymentsHistoryViewModel by viewModels()
    private val paymentReminderViewModel: PaymentReminderViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                paymentReminderViewModel.sendIntent(UiIntent.ShowCloseApp(true))
            }
        })

        setContent {
            val state by paymentReminderViewModel.state.collectAsState(UiState())
            paymentReminderViewModel.sendIntent(UiIntent.CheckNotifications)
            if (hasT33() && !state.notificationsGranted) CheckNotificationPermissions()
            Content()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    private fun CheckNotificationPermissions() {
        val context = LocalContext.current
        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.POST_NOTIFICATIONS] == true) {
                paymentReminderViewModel.sendIntent(
                    UiIntent.NotificationsGranted(Permissions.NOTIFICATIONS)
                )
                paymentReminderViewModel.sendIntent(
                    UiIntent.NotificationsGranted(Permissions.EXACT_ALARM)
                )
                Toast.makeText(context, context.getString(R.string.notifications_enabled), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, context.getString(R.string.cannot_notificate), Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            permissionLauncher.launch(arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.SCHEDULE_EXACT_ALARM
            ))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun Content() {
        val state by paymentReminderViewModel.state.collectAsState(UiState())
        val navController = rememberNavController()
        val screens = listOf(
            CurrentScreen.Home,
            CurrentScreen.Graphic,
            CurrentScreen.Settings
        )

        if (state.showDialog) {
            CustomDialog(
                titleText = stringResource(id = R.string.exit_question),
                bodyText = stringResource(id = R.string.exit_confirmation),
                onAccept = { finish() },
                onCancel = { paymentReminderViewModel.sendIntent(UiIntent.ShowCloseApp(false)) }
            )
        }
        
        Scaffold(
            topBar = {
                Surface {
                    TopAppBar(
                        title = { Text(stringResource(id = R.string.app_name)) }
                    )

                }
            },
            bottomBar = {
                BottomNavigationBar(navController, screens)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = CurrentScreen.Home.route
            ) {
                composable(CurrentScreen.Home.route) {
                    PagerContent(
                        homeViewModel,
                        paymentsHistoryViewModel
                    )
                }
                composable(CurrentScreen.Graphic.route) { GraphicFragment(graphicViewModel) }
                composable(Constants.PAYMENTS_HISTORY) {
                    PaymentsHistoryFragment(paymentsHistoryViewModel)
                }
                composable(CurrentScreen.Settings.route) { SettingsFragment(settingsViewModel) }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun PagerContent(
        homeViewModel: HomeViewModel,
        paymentsHistoryViewModel: PaymentsHistoryViewModel
    ) {
        val pagerState = rememberPagerState(pageCount = { 2 })

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    HomeFragment(homeViewModel)
                }

                1 -> {
                    PaymentsHistoryFragment(paymentsHistoryViewModel)
                }
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
        data object Home : CurrentScreen(Constants.HOME, Icons.Filled.Home)
        data object Graphic : CurrentScreen(Constants.GRAPHIC, Icons.Filled.Info)
        data object Settings : CurrentScreen(Constants.SETTINGS, Icons.Filled.Settings)
    }
}