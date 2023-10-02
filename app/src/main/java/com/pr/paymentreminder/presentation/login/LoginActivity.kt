package com.pr.paymentreminder.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    private fun Content() {
            viewModel = viewModel,
            onLoginClick = { email, password ->
                // Handle login and navigation here
                saveCredentials(email, password)
                //startActivity(Intent(this@LoginActivity, OnBoardingActivity::class.java))
                //finish()
            }
        )
    }

    @Composable
    fun LoginActivity(
        viewModel: LoginViewModel,
        onLoginClick: (email: String, password: String) -> Unit
    ) {
        val email by viewModel.emailHelperText.observeAsState()
        val password by viewModel.passHelperText.observeAsState()

        val emailText = remember { mutableStateOf(TextFieldValue()) }
        val passwordText = remember { mutableStateOf(TextFieldValue()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.rick_and_login),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.rick_morty_font)),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = emailText.value,
                onValueChange = { emailText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = stringResource(id = R.string.email)) },
                isError = !email.isNullOrEmpty(),
                singleLine = true
            )

            if (!email.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.invalid_email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordText.value,
                onValueChange = { passwordText.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                label = { Text(text = stringResource(id = R.string.pass)) },
                isError = !password.isNullOrEmpty(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            if (!password.isNullOrEmpty()) {
                Text(
                    text = stringResource(id = R.string.invalid_pass),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val emailValue = emailText.value.text
                    val passwordValue = passwordText.value.text
                    onLoginClick(emailValue, passwordValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }

    @Composable
    fun LoginScreen(
        viewModel: LoginViewModel,
        onLoginClick: (email: String, password: String) -> Unit
    ) {
        val context = LocalContext.current
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        val email = sharedPreferences.getString(
            context.getString(R.string.email),
            null
        )
        val password = sharedPreferences.getString(
            context.getString(R.string.pass),
            null
        )

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            onLoginClick(email, password)
        } else {
            LoginActivity(viewModel = viewModel, onLoginClick = onLoginClick)
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TextFields(
        helperText: Int?,
        //onEmailChanged: (String) -> Unit
    ) {
        var text by remember { mutableStateOf(emptyString()) }

        TextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.validateEmail(it)
            }
        )
    }
}