package com.pr.paymentreminder

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.LoginUseCase
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiState
import com.pr.paymentreminder.presentation.login.LoginViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTests {
    private lateinit var vm: LoginViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var preferencesHandler: PreferencesHandler

    private fun setUpViewModel() {
        vm = LoginViewModel(
            loginUseCase = loginUseCase,
            preferencesHandler = preferencesHandler
        )
    }

     @Test
     fun `WHEN email is not valid THEN has helper text`() {
         setUpViewModel()
         val stateChannel = Channel<UiState>()

         val job = vm.viewModelScope.launch {
             vm.state.collect { state ->
                 stateChannel.send(state)
             }
         }

         vm.sendIntent(UiIntent.ValidateEmail(""))

         runBlocking {
             val state = stateChannel.receive()
             assertTrue(state.hasEmailHelperText)
         }

         job.cancel()
     }
}