package com.pr.paymentreminder.presentation.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.lifecycleScope
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.login.LoginActivity
import com.pr.paymentreminder.presentation.paymentreminder.PaymentReminderActivity
import com.pr.paymentreminder.presentation.viewModels.RegisterViewModel
import com.pr.paymentreminder.ui.theme.dimen1
import com.pr.paymentreminder.ui.theme.dimen16
import com.pr.paymentreminder.ui.theme.dimen4
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20
import com.pr.paymentreminder.ui.theme.spacing72
import com.pr.paymentreminder.ui.theme.spacing8
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier
                .padding(spacing16)
                // .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val emailText = remember { mutableStateOf(TextFieldValue()) }
            val passText = remember { mutableStateOf(TextFieldValue()) }
            val repeatPassText = remember { mutableStateOf(TextFieldValue()) }

            Spacer(modifier = Modifier.height(dimen16))

            EmailField(emailText)
            PassField(passText)
            PassRepeatField(passText, repeatPassText)

            Image(
                painter = painterResource(id = R.drawable.logo_no_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().padding(vertical = spacing72)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(stringResource(id = R.string.login))
                    }
                },
                modifier = Modifier.padding(bottom = spacing8).clickable {
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                },
                color = Color.Blue
            )

            RegisterButton(emailText, passText, repeatPassText)
            CheckRegister()
        }
    }

    @Composable
    private fun RegisterButton(
        emailText: MutableState<TextFieldValue>,
        passText: MutableState<TextFieldValue>,
        repeatPassText: MutableState<TextFieldValue>
    ) {
        Button(
            onClick = {
                if ((viewModel.validateEmail(emailText.value.text)) && (viewModel.validatePassword(passText.value.text)) && (viewModel.validatePasswordMatch(passText.value.text, repeatPassText.value.text))) {
                    lifecycleScope.launch {
                        viewModel.register(emailText.value.text, passText.value.text)
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, R.string.invalid_data, Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing16)
        ) {
            Text(text = stringResource(id = R.string.register))
        }
    }

    @Composable
    private fun EmailField(
        emailText: MutableState<TextFieldValue>
    ) {
        val emailHelper by viewModel.emailHelperText.observeAsState()
        val wasEmailFieldFocused = remember { mutableStateOf(false) }
        TextField(
            value = emailText.value,
            onValueChange = { emailText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing16, vertical = spacing8)
                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                .onFocusChanged {
                    if (wasEmailFieldFocused.value && !it.isFocused) {
                        viewModel.validateEmail(emailText.value.text)
                    }
                    wasEmailFieldFocused.value = it.isFocused
                },
            label = { Text(text = stringResource(id = R.string.email)) },
            isError = !emailHelper.isNullOrEmpty(),
            singleLine = true
        )

        if (!emailHelper.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.invalid_email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing20, end = spacing16, bottom = spacing8),
                color = Color.Red
            )
        }
    }

    @Composable
    private fun PassRepeatField(
        passText: MutableState<TextFieldValue>,
        repeatPassText: MutableState<TextFieldValue>
    ) {
        val passHelper by viewModel.repeatPassHelperText.observeAsState()
        val wasPassFieldFocused = remember { mutableStateOf(false) }
        val passwordVisibility = remember { mutableStateOf(false) }

        TextField(
            value = repeatPassText.value,
            onValueChange = { repeatPassText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing16, vertical = spacing8)
                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                .onFocusChanged {
                    if (wasPassFieldFocused.value && !it.isFocused) {
                        viewModel.validatePasswordMatch(passText.value.text, repeatPassText.value.text)
                    }
                    wasPassFieldFocused.value = it.isFocused
                },
            label = { Text(text = stringResource(id = R.string.repeat_password)) },
            isError = !passHelper.isNullOrEmpty(),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = emptyString()
                    )
                }
            }
        )

        if (!passHelper.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.passwords_do_not_match),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing20, end = spacing16, bottom = spacing16),
                color = Color.Red
            )
        }
    }

    @Composable
    private fun PassField(
        passText: MutableState<TextFieldValue>
    ) {
        val passHelper by viewModel.passHelperText.observeAsState()
        val wasPassFieldFocused = remember { mutableStateOf(false) }
        val passwordVisibility = remember { mutableStateOf(false) }

        TextField(
            value = passText.value,
            onValueChange = { passText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing16, vertical = spacing8)
                .border(dimen1, Color.Gray, RoundedCornerShape(dimen4))
                .onFocusChanged {
                    if (wasPassFieldFocused.value && !it.isFocused) {
                        viewModel.validatePassword(passText.value.text)
                    }
                    wasPassFieldFocused.value = it.isFocused
                },
            label = { Text(text = stringResource(id = R.string.password)) },
            isError = !passHelper.isNullOrEmpty(),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = emptyString()
                    )
                }
            }
        )

        if (!passHelper.isNullOrEmpty()) {
            Text(
                text = stringResource(id = R.string.invalid_pass),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = spacing20, end = spacing16, bottom = spacing16),
                color = Color.Red
            )
        }
    }

    @Composable
    private fun CheckRegister() {
        viewModel.isRegisterSuccessful.observeAsState().value?.let { isRegisterSuccessful ->
            if (isRegisterSuccessful) {
                startActivity(Intent(this@RegisterActivity, PaymentReminderActivity::class.java))
                finish()
            }
        }
    }
}