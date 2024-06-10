package com.pr.paymentreminder

import com.nhaarman.mockitokotlin2.mock
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.login.LoginUseCase
import com.pr.paymentreminder.presentation.login.EmailValidator
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiAction
import com.pr.paymentreminder.presentation.login.LoginViewContract.UiIntent
import com.pr.paymentreminder.presentation.login.LoginViewModel
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTests: BaseViewModelTest() {

    @Test
    fun `WHEN email is valid THEN has not helper text`() {
        val loginUseCase = mock<LoginUseCase>()
        val preferencesHandler = mock<PreferencesHandler>()
        val emailValidator = mock<EmailValidator>()

        val vm = LoginViewModel(
            loginUseCase = loginUseCase,
            preferencesHandler = preferencesHandler,
            emailValidator
        )

        vm.sendIntent(UiIntent.ValidateEmail("cuentadepruebas@gmail.com"))

        assertTrue(!vm.state.value.hasPasswordHelperText)
    }

    // TODO: Fix this test
    @Test
    fun `WHEN email is not valid THEN has helper text`() {
        val loginUseCase = mock<LoginUseCase>()
        val preferencesHandler = mock<PreferencesHandler>()
        val emailValidator = mock<EmailValidator>()

        val vm = LoginViewModel(
            loginUseCase = loginUseCase,
            preferencesHandler = preferencesHandler,
            emailValidator
        )

        vm.sendIntent(UiIntent.ValidateEmail(""))

        assertTrue(vm.state.value.hasPasswordHelperText)
    }

    @Test
    fun `WHEN password is not valid THEN has helper text`() {
        val loginUseCase = mock<LoginUseCase>()
        val preferencesHandler = mock<PreferencesHandler>()
        val emailValidator = mock<EmailValidator>()

        val vm = LoginViewModel(
            loginUseCase = loginUseCase,
            preferencesHandler = preferencesHandler,
            emailValidator
        )

        vm.sendIntent(UiIntent.ValidatePassword(""))

        assertTrue(vm.state.value.hasPasswordHelperText)
    }

    @Test
    fun `WHEN password is valid THEN has not helper text`() {
        val loginUseCase = mock<LoginUseCase>()
        val preferencesHandler = mock<PreferencesHandler>()
        val emailValidator = mock<EmailValidator>()

        val vm = LoginViewModel(
            loginUseCase = loginUseCase,
            preferencesHandler = preferencesHandler,
            emailValidator
        )

        vm.sendIntent(UiIntent.ValidatePassword("123456Aa."))

        assertTrue(!vm.state.value.hasPasswordHelperText)
    }

    @Test
    fun `WHEN go to register THEN go to register`() {
        val loginUseCase = mock<LoginUseCase>()
        val preferencesHandler = mock<PreferencesHandler>()
        val emailValidator = mock<EmailValidator>()

        val vm = LoginViewModel(
            loginUseCase = loginUseCase,
            preferencesHandler = preferencesHandler,
            emailValidator
        )

        val actionObserver = vm.actions.observe()

        vm.sendIntent(UiIntent.GoRegister)

        actionObserver.assertLastEquals(UiAction.GoRegister)
    }
}
