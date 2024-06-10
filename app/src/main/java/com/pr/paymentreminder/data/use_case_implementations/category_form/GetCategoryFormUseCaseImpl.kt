package com.pr.paymentreminder.data.use_case_implementations.category_form

import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.category_form.GetCategoryFormUseCase
import javax.inject.Inject

class GetCategoryFormUseCaseImpl @Inject constructor(
    private val categoryDatabaseDataSource: CategoryDatabaseDataSource
) : GetCategoryFormUseCase {
    override suspend fun invoke(categoryId: Int): Category? =
        categoryDatabaseDataSource.getCategoryForm(categoryId)
}