package com.pr.paymentreminder.presentation.paymentreminder.edit_categories

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.domain.usecase.category_form.EditCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.GetAllCategoryFormsUseCase
import com.pr.paymentreminder.domain.usecase.category_form.GetCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.RemoveCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.SaveCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.service.GetServicesUseCase
import com.pr.paymentreminder.domain.usecase.service.UpdateServiceUseCase
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiState
import com.pr.paymentreminder.ui.theme.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoriesViewModel @Inject constructor(
    private val editCategoryForm: EditCategoryFormUseCase,
    private val getCategoriesUseCase: GetAllCategoryFormsUseCase,
    private val getCategoryForm: GetCategoryFormUseCase,
    private val getServices: GetServicesUseCase,
    private val removeCategory: RemoveCategoryFormUseCase,
    private val updateService: UpdateServiceUseCase,
    private val saveCategoryForm: SaveCategoryFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.CreateCategory -> createCategory(intent.categoryName)
            UiIntent.DeleteCategory -> deleteCategory()
            is UiIntent.EditCategory -> editCategory(intent.categoryName)
            is UiIntent.OnCategoryClick -> selectCategory(intent.categoryId)
            is UiIntent.OnDeleteCategory -> onDeleteCategory(intent.categoryId)
            UiIntent.OnDismissDialog -> onDismissDialog()

            UiIntent.OnFabPressed -> setState {
                copy(
                    showDialog = true,
                    selectedCategory = Category(name = emptyString())
                )
            }

            UiIntent.RefreshCategories -> getCategories()
        }
    }

    private fun onDismissDialog() {
        setState {
            copy(
                selectedCategory = Category(0, emptyString()),
                showDialog = false, showEditDialog = false
            )
        }
    }

    private fun onDeleteCategory(categoryId: Int) {
        viewModelScope.launch {
            val category = getCategoryForm(categoryId)
            setState { copy(selectedCategory = category ?: Category(), showEditDialog = true) }
        }
    }

    // TODO: TESTEAR, REASIGNAR CATEGORÍA CUANDO ELIMINO UNA LA CUAL ESTÁ ASIGNADA A UN SERVICIO
    // TODO: COMPROBAR POR QUÉ NO SE EDITA BIEN LA CATEGORÍA QUE ESTÁ SIENDO UTLIZADA
    private fun deleteCategory() {
        viewModelScope.launch {
            removeCategory(state.value.selectedCategory.id)
            getCategories()
            onDismissDialog()
        }
    }

    private fun editCategory(categoryName: String) {
        setState { copy(editedCategory = categoryName) }
        viewModelScope.launch {
            updateCategoryForm()
            updateServicesCategory()
            getCategories()
            UpdateServices.updateSharedUpdateServices(true)
            onDismissDialog()
        }

        setState { copy(showDialog = false) }
    }

    private fun updateCategoryForm() {
        viewModelScope.launch {
            editCategoryForm(Category(id = state.value.selectedCategory.id, name = state.value.editedCategory))
        }
    }

    private fun updateServicesCategory() {
        viewModelScope.launch {
            val services = getServices().firstOrNull()
            val servicesToUpdate = services?.filter { it.category == state.value.selectedCategory.name }
            servicesToUpdate?.forEach { service ->
                updateService(
                    service.id,
                    service.copy(category = state.value.editedCategory)
                )
            }
        }
    }

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categories = getCategoriesUseCase()
            setState { copy(categories = categories.orEmpty()) }
        }
    }

    private fun createCategory(categoryName: String) {
        viewModelScope.launch {
            val categories = getCategoriesUseCase()
            val existingCategory = categories?.find { it.name == categoryName }
            if (existingCategory == null) {
                saveCategoryForm(Category(name = categoryName))
            }
            getCategories()
            onDismissDialog()
        }
    }

    private fun selectCategory(categoryId: Int) {
        viewModelScope.launch {
            val category = getCategoryForm(categoryId)
            setState { copy(selectedCategory = category ?: Category(), showDialog = true) }
        }
    }
}
