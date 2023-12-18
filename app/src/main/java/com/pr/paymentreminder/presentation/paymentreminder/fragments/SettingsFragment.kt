package com.pr.paymentreminder.presentation.paymentreminder.fragments

import android.app.Activity
import android.app.AlertDialog
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.SettingsViewModel
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen64
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing4

@Composable
fun SettingsFragment(viewModel: SettingsViewModel) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = spacing16)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Spacer(modifier = Modifier.height(dimen64))
        /* Content here */
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = {
                val dialog = AlertDialog.Builder(context)
                    .setTitle(R.string.logout)
                    .setMessage(R.string.logout_question)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        viewModel.signOut()
                        sharedPreferences.edit().clear().apply()
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }
                    .setNegativeButton(R.string.no, null)
                    .create()
                dialog.show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = spacing4))
        {
            Text(text = stringResource(id = R.string.logout), color = Color.Red)
        }

        Spacer(modifier = Modifier.height(dimen56))
    }
}