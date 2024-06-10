package com.pr.paymentreminder.presentation.paymentreminder.edit_categories

import com.pr.paymentreminder.base.BaseViewContract
import com.pr.paymentreminder.data.model.Category

class EditCategoriesViewContract : BaseViewContract() {
    data class UiState(
        val categories: List<Category> = emptyList(),
        val selectedCategory: Category = Category(),
        val showDialog: Boolean = false
    )

    sealed class UiIntent {
        data class CreateCategory(val categoryName: String) : UiIntent()
        data class EditCategory(val categoryName: String) : UiIntent()
        data class OnCategoryClick(val categoryId: Int) : UiIntent()
        data object OnDismissDialog : UiIntent()
        data object OnFabPressed : UiIntent()
    }

    sealed class UiAction {

    }
}