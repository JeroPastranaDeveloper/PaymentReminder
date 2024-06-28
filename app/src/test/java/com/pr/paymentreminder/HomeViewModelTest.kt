package com.pr.paymentreminder

import com.nhaarman.mockitokotlin2.mock
import com.pr.paymentreminder.data.preferences.PreferencesHandler
import com.pr.paymentreminder.domain.usecase.category_form.GetAllCategoryFormsUseCase
import com.pr.paymentreminder.domain.usecase.category_form.SaveCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.service.CreateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import com.pr.paymentreminder.domain.usecase.service.RemoveServiceUseCase
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import com.pr.paymentreminder.domain.usecase.service_form.SaveServiceFormUseCase
import com.pr.paymentreminder.notifications.AlarmScheduler
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : BaseViewModelTest() {

    @Test
    fun `WHEN I send OnDismissSnackBar THEN I hide snackbar`() {
        val getServicesUseCase = mock<GetServicesUseCase>()
        val createServiceUseCase = mock<CreateServiceUseCase>()
        val removeServiceUseCase = mock<RemoveServiceUseCase>()
        val updateServiceUseCase = mock<UpdateServiceUseCase>()
        val saveServiceFormUseCase = mock<SaveServiceFormUseCase>()
        val alarmScheduler = mock<AlarmScheduler>()
        val preferencesHandler = mock<PreferencesHandler>()
        val getCategoriesFormUseCase = mock<GetAllCategoryFormsUseCase>()
        val saveCategoryFormUseCase = mock<SaveCategoryFormUseCase>()

        val vm = HomeViewModel(
            getServicesUseCase = getServicesUseCase,
            createServiceUseCase = createServiceUseCase,
            removeServiceUseCase = removeServiceUseCase,
            updateServiceUseCase = updateServiceUseCase,
            alarmScheduler = alarmScheduler,
            preferencesHandler = preferencesHandler,
            saveServiceForm = saveServiceFormUseCase,
            getCategoriesUseCase = getCategoriesFormUseCase,
            saveCategoryFormUseCase = saveCategoryFormUseCase
        )

        vm.sendIntent(HomeViewContract.UiIntent.OnDismissSnackBar)

        assertTrue(!vm.state.value.showSnackBar)
    }

    // TODO: Fix this test
    @Test
    fun `GIVEN a service WHEN I remove that service THEN that service is removed`() = runTest {
        val getServicesUseCase = mock<GetServicesUseCase>()
        val createServiceUseCase = mock<CreateServiceUseCase>()
        val removeServiceUseCase = mock<RemoveServiceUseCase>()
        val updateServiceUseCase = mock<UpdateServiceUseCase>()
        val saveServiceFormUseCase = mock<SaveServiceFormUseCase>()
        val alarmScheduler = mock<AlarmScheduler>()
        val preferencesHandler = mock<PreferencesHandler>()
        val getCategoriesFormUseCase = mock<GetAllCategoryFormsUseCase>()
        val saveCategoryFormUseCase = mock<SaveCategoryFormUseCase>()

        val vm = HomeViewModel(
            getServicesUseCase = getServicesUseCase,
            createServiceUseCase = createServiceUseCase,
            removeServiceUseCase = removeServiceUseCase,
            updateServiceUseCase = updateServiceUseCase,
            alarmScheduler = alarmScheduler,
            preferencesHandler = preferencesHandler,
            saveServiceForm = saveServiceFormUseCase,
            getCategoriesUseCase = getCategoriesFormUseCase,
            saveCategoryFormUseCase = saveCategoryFormUseCase
        )

        /*removeServiceUseCase.stub {
            onBlocking { invoke(service.id) }
        }

        vm.sendIntent(HomeViewContract.UiIntent.RemoveService(service))

        assertTrue(vm.state.value.serviceToRemove == service && vm.state.value.showSnackBar && vm.state.value.showSnackBarType == CustomSnackBarType.DELETE)
    */}

    @Test
    fun `WHEN I send AddEditService THEN I go to AddEditServiceActivity`() {
        val getServicesUseCase = mock<GetServicesUseCase>()
        val createServiceUseCase = mock<CreateServiceUseCase>()
        val removeServiceUseCase = mock<RemoveServiceUseCase>()
        val updateServiceUseCase = mock<UpdateServiceUseCase>()
        val saveServiceFormUseCase = mock<SaveServiceFormUseCase>()
        val alarmScheduler = mock<AlarmScheduler>()
        val preferencesHandler = mock<PreferencesHandler>()
        val getCategoriesFormUseCase = mock<GetAllCategoryFormsUseCase>()
        val saveCategoryFormUseCase = mock<SaveCategoryFormUseCase>()

        val vm = HomeViewModel(
            getServicesUseCase = getServicesUseCase,
            createServiceUseCase = createServiceUseCase,
            removeServiceUseCase = removeServiceUseCase,
            updateServiceUseCase = updateServiceUseCase,
            alarmScheduler = alarmScheduler,
            preferencesHandler = preferencesHandler,
            saveServiceForm = saveServiceFormUseCase,
            getCategoriesUseCase = getCategoriesFormUseCase,
            saveCategoryFormUseCase = saveCategoryFormUseCase
        )
        val actionObserver = vm.actions.observe()
        vm.sendIntent(HomeViewContract.UiIntent.AddEditService("123ABC", "EDIT"))

        actionObserver.assertLastEquals(HomeViewContract.UiAction.AddEditService("123ABC", "EDIT"))
    }
}