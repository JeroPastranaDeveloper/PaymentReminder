package com.pr.paymentreminder.data.use_case_implementations.category_form

import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.category_form.ClearAllCategoryFormsUseCase
import javax.inject.Inject

class ClearAllCategoryFormsUseCaseImpl @Inject constructor(
    private val categoryDatabaseDataSource: CategoryDatabaseDataSource
) : ClearAllCategoryFormsUseCase {
    override suspend fun invoke() =
        categoryDatabaseDataSource.clearAllCategoriesForm()
}