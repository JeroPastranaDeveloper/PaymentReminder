package com.pr.paymentreminder.data.room.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE category_id = :categoryId")
    fun getCategoryForm(categoryId: Int): CategoryRoom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCategoryRoom(form: CategoryRoom)

    @Update
    fun editCategoryForm(form: CategoryRoom)

    @Query("SELECT * from categories")
    fun getAllForms(): List<CategoryRoom>?

    @Delete
    fun deleteForm(form: CategoryRoom)
}