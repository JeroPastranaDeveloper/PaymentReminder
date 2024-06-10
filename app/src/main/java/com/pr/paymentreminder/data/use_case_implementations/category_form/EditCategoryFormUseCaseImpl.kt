package com.pr.paymentreminder.data.use_case_implementations.category_form

import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.category_form.EditCategoryFormUseCase
import javax.inject.Inject

class EditCategoryFormUseCaseImpl @Inject constructor(
    private val categoryDatabaseDataSource: CategoryDatabaseDataSource
) : EditCategoryFormUseCase {
    override suspend fun invoke(form: Category) {
        categoryDatabaseDataSource.editCategoryForm(form)
    }
}