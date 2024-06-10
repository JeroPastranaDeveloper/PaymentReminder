package com.pr.paymentreminder.di.category_form

import com.pr.paymentreminder.base.CoroutineIO
import com.pr.paymentreminder.data.data_source_impls.CategoryDatabaseDataSourceImpl
import com.pr.paymentreminder.data.source.AppDatabase
import com.pr.paymentreminder.data.source.CategoryDatabaseDataSource
import com.pr.paymentreminder.data.use_case_implementations.category_form.ClearAllCategoryFormsUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.category_form.EditCategoryFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.category_form.GetAllCategoryFormsUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.category_form.GetCategoryFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.category_form.RemoveCategoryFormUseCaseImpl
import com.pr.paymentreminder.data.use_case_implementations.category_form.SaveCategoryFormUseCaseImpl
import com.pr.paymentreminder.domain.usecase.category_form.ClearAllCategoryFormsUseCase
import com.pr.paymentreminder.domain.usecase.category_form.EditCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.GetAllCategoryFormsUseCase
import com.pr.paymentreminder.domain.usecase.category_form.GetCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.RemoveCategoryFormUseCase
import com.pr.paymentreminder.domain.usecase.category_form.SaveCategoryFormUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
class CategoryFormModule {
    @Singleton
    @Provides
    fun providesClearAllCategoryFormsUseCase(clearAllCategoryFormsUseCaseImpl: ClearAllCategoryFormsUseCaseImpl): ClearAllCategoryFormsUseCase =
        clearAllCategoryFormsUseCaseImpl

    @Singleton
    @Provides
    fun providesGetAllCategoryFormsUseCase(getAllCategoryFormsUseCaseImpl: GetAllCategoryFormsUseCaseImpl): GetAllCategoryFormsUseCase =
        getAllCategoryFormsUseCaseImpl

    @Singleton
    @Provides
    fun providesRemoveCategoryFormsUseCase(removeCategoryFormUseCaseImpl: RemoveCategoryFormUseCaseImpl): RemoveCategoryFormUseCase =
        removeCategoryFormUseCaseImpl

    @Singleton
    @Provides
    fun providesSaveCategoryFormUseCase(saveCategoryFormUseCaseImpl: SaveCategoryFormUseCaseImpl): SaveCategoryFormUseCase =
        saveCategoryFormUseCaseImpl

    @Singleton
    @Provides
    fun providesGetCategoryFormUseCase(getCategoryFormUseCaseImpl: GetCategoryFormUseCaseImpl): GetCategoryFormUseCase =
        getCategoryFormUseCaseImpl

    @Singleton
    @Provides
    fun providesEditCategoryFormUseCase(editCategoryFormUseCaseImpl: EditCategoryFormUseCaseImpl): EditCategoryFormUseCase =
        editCategoryFormUseCaseImpl

    @Provides
    fun providesCategoryDatabaseDataSource(
        appDatabase: AppDatabase,
        @CoroutineIO coroutineContext: CoroutineContext
    ) : CategoryDatabaseDataSource = CategoryDatabaseDataSourceImpl(
        appDatabase.categoryDao(),
        coroutineContext
    )
}