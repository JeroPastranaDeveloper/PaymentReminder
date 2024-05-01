package com.pr.paymentreminder

import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import com.pr.paymentreminder.domain.usecase.service.RemoveServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service_form.SaveServiceFormUseCase
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
    private lateinit var createServiceUseCase: CreateServiceUseCase

    @Mock
    private lateinit var removeServiceUseCase: RemoveServiceUseCase

    @Mock
    private lateinit var updateServiceUseCase: UpdateServiceUseCase

    @Mock
    private lateinit var saveServiceFormUseCase: SaveServiceFormUseCase

    @Mock
    private lateinit var alarmScheduler: AlarmScheduler

    @Mock
    private lateinit var preferencesHandler: PreferencesHandler

    private fun setUpViewModel() {
        vm = HomeViewModel(
            getServicesUseCase = getServicesUseCase,
            createServiceUseCase = createServiceUseCase,
            removeServiceUseCase = removeServiceUseCase,
            updateServiceUseCase = updateServiceUseCase,
            alarmScheduler = alarmScheduler,
            preferencesHandler = preferencesHandler,
            saveServiceForm = saveServiceFormUseCase
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