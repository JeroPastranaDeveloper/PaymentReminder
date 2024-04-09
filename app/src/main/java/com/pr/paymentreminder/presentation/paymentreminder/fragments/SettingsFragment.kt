package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.settings.SettingsViewModel
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing4

class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Log.d("ESTOY EN", "SETTINGS")
                val context = LocalContext.current

                fun handleAction(action: UiAction) {
                    when (action) {
                        is UiAction.SignOut -> {
                            signOutDialog(context = context, viewModel = viewModel)
                        }
                    }
                }

                LaunchedEffect(viewModel) {
                    viewModel.actions.collect { action ->
                        handleAction(action)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = spacing16)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Spacer(modifier = Modifier.height(dimen64))

                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = {
                            viewModel.sendIntent(UiIntent.ShowSignOutDialog)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = spacing4)
                    )
                    {
                        Text(text = stringResource(id = R.string.logout), color = Color.Red)
                    }

                    Spacer(modifier = Modifier.height(dimen56))
                }
            }
        }
    }

    private fun signOutDialog(
        context: Context,
        viewModel: SettingsViewModel
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.logout)
            .setMessage(R.string.logout_question)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.sendIntent(UiIntent.SignOut)
                (context as Activity).finish()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
            .setNegativeButton(R.string.no, null)
            .create()
        dialog.show()
    }
}