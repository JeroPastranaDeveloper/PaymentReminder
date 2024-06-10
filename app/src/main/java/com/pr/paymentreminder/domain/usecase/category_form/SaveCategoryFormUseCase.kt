package com.pr.paymentreminder.domain.usecase.category_form

import com.pr.paymentreminder.data.model.Category

fun interface SaveCategoryFormUseCase {
    suspend operator fun invoke(form: Category)
}