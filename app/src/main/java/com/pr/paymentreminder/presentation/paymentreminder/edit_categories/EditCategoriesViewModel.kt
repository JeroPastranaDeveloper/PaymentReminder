package com.pr.paymentreminder.presentation.paymentreminder.edit_categories

import androidx.lifecycle.viewModelScope
import com.pr.paymentreminder.base.BaseComposeViewModelWithActions
import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.domain.usecase.category_form.EditCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.GetAllCategoryFormsUseCase
import com.pr.paymentreminder.domain.usecase.category_form.GetCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.SaveCategoryFormUseCase
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiAction
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiState
import com.pr.paymentreminder.ui.theme.emptyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetAllCategoryFormsUseCase,
    private val getCategoryFormUseCase: GetCategoryFormUseCase,
    private val saveCategoryFormUseCase: SaveCategoryFormUseCase,
    private val editCategoryFormUseCase: EditCategoryFormUseCase
) : BaseComposeViewModelWithActions<UiState, UiIntent, UiAction>() {
    override val initialViewState = UiState()

    override fun manageIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.CreateCategory -> createCategory(intent.categoryName)
            is UiIntent.EditCategory -> editCategory(intent.categoryName)
            is UiIntent.OnCategoryClick -> selectCategory(intent.categoryId)
            UiIntent.OnDismissDialog -> setState {
                copy(
                    selectedCategory = Category(0, emptyString()),
                    showDialog = false
                )
            }
            UiIntent.OnFabPressed -> setState { copy(showDialog = true) }
        }
    }

    private fun editCategory(categoryName: String) {
        viewModelScope.launch {
            editCategoryFormUseCase(Category(id = state.value.selectedCategory.id, name = categoryName))
        }
        getCategories()
        setState { copy(showDialog = false) }
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
                saveCategoryFormUseCase(Category(name = categoryName))
            }
        }
        getCategories()
        setState { copy(showDialog = false) }
    }

    private fun selectCategory(categoryId: Int) {
        viewModelScope.launch {
            val category = getCategoryFormUseCase(categoryId)
            setState { copy(selectedCategory = category ?: Category(), showDialog = true) }
        }
    }
}