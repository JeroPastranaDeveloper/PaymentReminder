package com.pr.paymentreminder.domain.usecase.category_form

import com.pr.paymentreminder.data.model.Category

fun interface GetCategoryFormUseCase {
    suspend operator fun invoke(categoryId: Int): Category?
}