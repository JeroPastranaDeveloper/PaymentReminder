package com.pr.paymentreminder

import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.service_form.ClearAllServiceFormsUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    private lateinit var vm: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getServicesUseCase: GetServicesUseCase

    @Mock
    private lateinit var alarmScheduler: AlarmScheduler

    @Mock
    private lateinit var preferencesHandler: PreferencesHandler

    @Mock
    private lateinit var serviceForm: ClearAllServiceFormsUseCase

    private fun setUpViewModel() {
        vm = HomeViewModel(
            getServices = getServicesUseCase,
            alarmScheduler = alarmScheduler,
            preferencesHandler = preferencesHandler,
            serviceForm= serviceForm
        )
    }

    @Test
    fun `WHEN a item is empty THEN has helper text`() {
        setUpViewModel()
        val stateChannel = Channel<UiState>()

        /*val job = vm.viewModelScope.launch {
            vm.state.collect { state ->
                stateChannel.send(state)
            }
        }

        // vm.sendIntent(UiIntent.ValidateService(nameItem, ""))
        vm.sendIntent(UiIntent.CreateService(
            Service(
                "hola",
                emptyString(),
                emptyString(),
                emptyString(),
                emptyString(),
                emptyString(),
                emptyString(),
                emptyString(),
                emptyString(),
                emptyString()
            )))

        runBlocking {
            val state = stateChannel.receive()
            assertTrue(state.serviceNameHelperText)
        }

        job.cancel()*/
    }
}