package com.pr.paymentreminder.domain.usecase.category_form

fun interface RemoveCategoryFormUseCase {
    suspend operator fun invoke(categoryId: Int)
}