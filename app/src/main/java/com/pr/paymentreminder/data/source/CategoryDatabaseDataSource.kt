package com.pr.paymentreminder.data.source

import com.pr.paymentreminder.data.model.Category

interface CategoryDatabaseDataSource {
    suspend fun clearAllCategoriesForm()
    suspend fun getAllCategoriesForm(): List<Category>?
    suspend fun getCategoryForm(categoryId: Int): Category?
    suspend fun editCategoryForm(category: Category)
    suspend fun removeCategoryForm(categoryId: Int)
    suspend fun saveCategoryForm(form: Category)
}