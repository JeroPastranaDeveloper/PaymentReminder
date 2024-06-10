package com.pr.paymentreminder.data.use_case_implementations.category_form

import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.category_form.GetAllCategoryFormsUseCase
import javax.inject.Inject

class GetAllCategoryFormsUseCaseImpl @Inject constructor(
    private val categoryDatabaseDataSource: CategoryDatabaseDataSource
) : GetAllCategoryFormsUseCase {
    override suspend fun invoke(): List<Category>? =
        categoryDatabaseDataSource.getAllCategoriesForm()
}