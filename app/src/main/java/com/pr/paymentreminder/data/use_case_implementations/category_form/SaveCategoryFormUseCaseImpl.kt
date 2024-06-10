package com.pr.paymentreminder.data.use_case_implementations.category_form

import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.domain.usecase.category_form.SaveCategoryFormUseCase
import javax.inject.Inject
import kotlin.random.Random

class SaveCategoryFormUseCaseImpl @Inject constructor(
    private val categoryDatabaseDataSource: CategoryDatabaseDataSource
) : SaveCategoryFormUseCase {
    override suspend fun invoke(form: Category) {
        val id = if (form.id == 0) Random.nextInt(1, Int.MAX_VALUE) else form.id
        categoryDatabaseDataSource.saveCategoryForm(form.copy(id = id))
    }
}