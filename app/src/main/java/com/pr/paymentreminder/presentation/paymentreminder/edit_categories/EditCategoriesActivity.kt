package com.pr.paymentreminder.presentation.paymentreminder.edit_categories

import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.base.BaseActivity
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiIntent
import com.pr.paymentreminder.presentation.paymentreminder.edit_categories.EditCategoriesViewContract.UiState
import com.pr.paymentreminder.ui.theme.spacing16
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCategoriesActivity : BaseActivity() {
    private val viewModel: EditCategoriesViewModel by viewModels()

    @Composable
    override fun ComposableContent() {
        // addRepeatingJob(Lifecycle.State.STARTED) { viewModel.actions.collect { ::handleAction } }
        val state by viewModel.state.collectAsState(UiState())
        Content(state)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Content(state: UiState) {
        var categories by remember { mutableStateOf(state.categories) }

        LaunchedEffect(key1 = state.showDialog, key2 = state.categories) {
            categories = state.categories
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Surface {
                    TopAppBar(
                        title = { Text(stringResource(id = R.string.edit_categories_title)) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(start = spacing16, end = spacing16, bottom = spacing16),
                    verticalArrangement = Arrangement.Top
                ) {
                    categories.forEach {
                        Text(
                            it.name,
                            modifier = Modifier.clickable {
                                viewModel.sendIntent(
                                    UiIntent.OnCategoryClick(it.id)
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(spacing16))
                    }
                }
            }

            if (state.showDialog) {
                ShowDialog(state.selectedCategory.name)
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(spacing16),
                onClick = {
                    viewModel.sendIntent(UiIntent.OnFabPressed)
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
    fun ShowDialog(categoryName: String) {
        var text by remember { mutableStateOf(categoryName) }

        AlertDialog(
            onDismissRequest = {
                viewModel.sendIntent(UiIntent.OnDismissDialog)
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
                        if (categoryName.isEmpty()) viewModel.sendIntent(
                            UiIntent.CreateCategory(text)
                        )
                        else viewModel.sendIntent(UiIntent.EditCategory(text))
                    }
                ) {
                    Text("** Guardar")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        viewModel.sendIntent(UiIntent.OnDismissDialog)
                    }
                ) {
                    Text("** Cancelar")
                }
            }
        )
    }
}