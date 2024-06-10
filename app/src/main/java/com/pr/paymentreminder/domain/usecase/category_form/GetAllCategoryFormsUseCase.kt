package com.pr.paymentreminder.domain.usecase.category_form

import com.pr.paymentreminder.data.model.Category

fun interface GetAllCategoryFormsUseCase {
    suspend operator fun invoke(): List<Category>?
}