package com.pr.paymentreminder.data.data_source_impls

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.model.Category
import com.pr.paymentreminder.data.model.toDomain
import com.pr.paymentreminder.data.model.toEntity
import com.pr.paymentreminder.data.room.category.CategoryDao
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class CategoryDatabaseDataSourceImpl(
    private val categoryDao: CategoryDao,
    @CoroutineIO private val coroutineContext: CoroutineContext
) : CategoryDatabaseDataSource {
    override suspend fun clearAllCategoriesForm() =
        withContext(coroutineContext) {
            val allForms = categoryDao.getAllForms()
            allForms.orEmpty().forEach {
                categoryDao.deleteForm(it)
            }
        }

    override suspend fun getAllCategoriesForm(): List<Category>? =
        withContext(coroutineContext) {
            categoryDao.getAllForms()?.map { it.toDomain() }
        }

    override suspend fun getCategoryForm(categoryId: Int): Category? =
        withContext(coroutineContext) {
            categoryDao.getCategoryForm(categoryId)?.toDomain()
        }

    override suspend fun editCategoryForm(category: Category) =
        withContext(coroutineContext) {
            categoryDao.editCategoryForm(category.toEntity())
        }

    override suspend fun removeCategoryForm(categoryId: Int) =
        withContext(coroutineContext) {
            val category = categoryDao.getCategoryForm(categoryId)
            categoryDao.deleteForm(category)
        }

    override suspend fun saveCategoryForm(form: Category) =
        withContext(coroutineContext) {
            categoryDao.saveCategoryRoom(form.toEntity())
        }
}