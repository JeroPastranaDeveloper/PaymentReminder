package com.pr.paymentreminder

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.domain.usecase.ServicesUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewModel
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiState
import com.pr.paymentreminder.presentation.paymentreminder.fragments.viewModels.home.HomeViewContract.UiIntent
import com.pr.paymentreminder.ui.theme.emptyString
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
class HomeViewModelTest {
    private lateinit var vm: HomeViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var servicesUseCase: ServicesUseCase

    @Mock
    private lateinit var alarmScheduler: AlarmScheduler

    private fun setUpViewModel() {
        vm = HomeViewModel(
            servicesUseCase = servicesUseCase,
            alarmScheduler = alarmScheduler
        )
    }

    @Test
    fun `WHEN a item is empty THEN has helper text`() {
        setUpViewModel()
        val stateChannel = Channel<UiState>()

        val job = vm.viewModelScope.launch {
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

        job.cancel()
    }
}