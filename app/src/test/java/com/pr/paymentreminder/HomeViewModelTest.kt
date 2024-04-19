package com.pr.paymentreminder

import com.pr.paymentreminder.base.BaseViewModel
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.ServiceFormUseCase
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.mothers.ServiceMother
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewModel
import com.pr.paymentreminder.ui.theme.emptyString
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomeViewModelTest {

    private lateinit var vm: HomeViewModel

    @Mock
    private lateinit var servicesUseCase: ServicesUseCase

    @Mock
    private lateinit var alarmScheduler: AlarmScheduler

    @Mock
    private lateinit var preferencesHandler: PreferencesHandler

    @Mock
    private lateinit var serviceForm: ServiceFormUseCase

    @get:Rule
    val coroutineScope = CoroutineTestRule()

    @Before
    fun setUpViewModel() {
        MockitoAnnotations.openMocks(this)
        vm = HomeViewModel(
            servicesUseCase = servicesUseCase,
            alarmScheduler = alarmScheduler,
            preferencesHandler = preferencesHandler,
            serviceForm = serviceForm
        )
    }

    @Test
    fun `when AddEditService intent is sent, AddEditService action is dispatched`() = runTest {
        val action = ButtonActions.EDIT.name
        val serviceId = "1234"
        val emittedAction = vm.actions.first()

        vm.sendIntent(UiIntent.AddEditService(serviceId, action))

        assertTrue(emittedAction is UiAction.AddEditService)
        assertEquals(serviceId, (emittedAction as UiAction.AddEditService).serviceId)
        assertEquals(action, emittedAction.action)
    }

    @Test
    fun checkSnackBar() = runTest {
        val state = vm.state.value

        BaseViewModel.SharedShowSnackBarType.updateSharedSnackBarType(CustomSnackBarType.UPDATE)
        vm.sendIntent(UiIntent.CheckSnackBarConfig)

        assertTrue(!state.showSnackBar)
        assertTrue(state.showSnackBarType == CustomSnackBarType.NONE)
    }

    @Test
    fun restoreService() = runTest {
        val state = vm.state.value
        val service = ServiceMother.buildService(
            "1234",
            "Hola",
            emptyString(),
            "27/05/2024",
            "prueba",
            "7",
            "2",
            "Amazon"
        )

        vm.sendIntent(UiIntent.RestoreDeletedService(service))

        assertTrue(state.services == listOf(service))
    }
}
