package com.pr.paymentreminder.presentation.paymentreminder.fragments.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomDialog
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesActivity
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.settings.SettingsViewContract.UiState
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing4

@Composable
fun SettingsFragment(viewModel: SettingsViewModel) {
    val state by viewModel.state.collectAsState(UiState())
    val context = LocalContext.current

    fun handleAction(action: UiAction) {
        when (action) {
            UiAction.EditCategories -> editCategories(context)
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

        OutlinedButton(
            onClick = {
                viewModel.sendIntent(UiIntent.EditCategories)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.edit_categories))
        }

        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = {
                viewModel.sendIntent(UiIntent.ShowSignOutDialog(true))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing4)
        ) {
            Text(text = stringResource(id = R.string.logout), color = Color.Red)
        }

        Spacer(modifier = Modifier.height(dimen56))
    }

    if (state.signOut) {
        CustomDialog(
            titleText = stringResource(id = R.string.logout),
            bodyText = stringResource(id = R.string.logout_question),
            onAccept = {
                viewModel.sendIntent(UiIntent.SignOut)
                closeActivity(context)

            },
            onCancel = { viewModel.sendIntent(UiIntent.ShowSignOutDialog(false)) }
        )
    }
}

private fun editCategories(context: Context) {
    val intent = Intent(context, EditCategoriesActivity::class.java)
    context.startActivity(intent)
}

private fun closeActivity(context: Context) {
    (context as Activity).finish()
    val intent = Intent(context, LoginActivity::class.java)
    context.startActivity(intent)
}
