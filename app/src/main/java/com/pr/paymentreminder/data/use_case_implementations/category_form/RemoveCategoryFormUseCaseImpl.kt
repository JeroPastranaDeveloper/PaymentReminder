package com.pr.paymentreminder.data.use_case_implementations.category_form

import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.category_form.RemoveCategoryFormUseCase
import javax.inject.Inject

class RemoveCategoryFormUseCaseImpl @Inject constructor(
    private val categoryDatabaseDataSource: CategoryDatabaseDataSource
) : RemoveCategoryFormUseCase {
    override suspend fun invoke(categoryId: Int) =
        categoryDatabaseDataSource.removeCategoryForm(categoryId)
}