package com.pr.paymentreminder.presentation.paymentreminder.edit_categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomDialog
import com.pr.paymentreminder.ui.theme.BaseText
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoriesFactory(state: EditCategoriesViewContract.UiState, action: EditCategoriesActions) {
    var categories by remember { mutableStateOf(state.categories) }
    var selectedCategory by remember { mutableStateOf(state.selectedCategory.name) }

    LaunchedEffect(key1 = state.showDialog, key2 = state.categories, key3 = state.showEditDialog) {
        categories = state.categories
        selectedCategory = state.selectedCategory.name
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Surface {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.edit_categories_title)) },
                    navigationIcon = {
                        IconButton(onClick = { action.onGoBack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                categories.forEach {
                    CategoryButton(
                        category = it.name,
                        onClick = {
                            action.onClickCategory(it.id)
                        },
                        onLongClick = {
                            action.onLongClickCategory(it.id)
                        }
                    )
                }
            }
        }

        if (state.showDialog) {
            ShowDialog(action, selectedCategory)
        }

        if (state.showEditDialog) {
            ShowEditDialog(action)
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(spacing16),
            onClick = {
                action.onClickFAB()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ShowEditDialog(action: EditCategoriesActions) {
    CustomDialog(
        titleText = "** ¿Eliminar categoría?",
        bodyText = "** La acción no se podrá deshacer",
        onAccept = { action.onDeleteCategory() },
        onCancel = { action.onCloseDialog() }
    )
}

@Composable
fun ShowDialog(action: EditCategoriesActions, categoryName: String) {
    var text by remember { mutableStateOf(categoryName) }

    AlertDialog(
        onDismissRequest = {
            action.onCloseDialog()
        },
        title = { Text(text = if (categoryName.isEmpty()) "** Crear categoría" else "** Editar categoría") },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("** Nombre de la categoría") }
            )
        },
        confirmButton = {
            OutlinedButton(
                onClick = {
                    if (categoryName.isEmpty()) action.onCreateCategory(text)
                    else action.onEditCategory(text)
                }
            ) {
                Text(stringResource(id = R.string.btn_save))
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = {
                    action.onCloseDialog()
                }
            ) {
                Text(stringResource(id = R.string.cancel_button))
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryButton(category: String, onClick: () -> Unit, onLongClick: () -> Unit) {
    Text(
        text = category,
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onLongClick()
                }
            )
            .padding(start = spacing20, top = spacing20, bottom = spacing20)
            .fillMaxWidth(),
        style = BaseText
    )
}
