package com.pr.paymentreminder.presentation.paymentreminder.edit_categories

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCategoriesActivity : BaseActivity() {
    private val viewModel: EditCategoriesViewModel by viewModels()

    @Composable
    override fun ComposableContent() {
        val state by viewModel.state.collectAsState(UiState())
        Content(state)
    }

    @Composable
    private fun Content(state: UiState) {
        EditCategoriesFactory(
            state = state,
            action = EditCategoriesActions(
                onClickCategory = {
                    viewModel.sendIntent(UiIntent.OnCategoryClick(it))
                },
                onClickFAB = {
                    viewModel.sendIntent(UiIntent.OnFabPressed)
                },
                onCloseDialog = {
                    viewModel.sendIntent(UiIntent.OnDismissDialog)
                },
                onCreateCategory = {
                    viewModel.sendIntent(UiIntent.CreateCategory(it))
                },
                onDeleteCategory = {
                    viewModel.sendIntent(UiIntent.DeleteCategory)
                },
                onEditCategory = {
                    viewModel.sendIntent(UiIntent.EditCategory(it))
                },
                onGoBack = {
                    finish()
                },
                onLongClickCategory = {
                    viewModel.sendIntent(UiIntent.OnDeleteCategory(it))
                }
            )
        )
    }
}

data class EditCategoriesActions(
    val onClickCategory: (id: Int) -> Unit,
    val onClickFAB: () -> Unit,
    val onCloseDialog: () -> Unit,
    val onCreateCategory: (name: String) -> Unit,
    val onDeleteCategory: () -> Unit,
    val onEditCategory: (name: String) -> Unit,
    val onGoBack: () -> Unit,
    val onLongClickCategory: (id: Int) -> Unit,
)
