package com.pr.paymentreminder.domain.usecase.category_form

fun interface ClearAllCategoryFormsUseCase {
    suspend operator fun invoke()
}