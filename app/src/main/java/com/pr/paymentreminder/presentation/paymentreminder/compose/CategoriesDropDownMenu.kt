package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.clickable
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.ui.theme.emptyString

@Composable
fun CategoriesDropDownMenu(
    categories: List<Categories>,
    onCategorySelected: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(emptyString()) }
    var categoryExpanded by remember { mutableStateOf(false) }

    Text(
        text = stringResource(id = R.string.category, selectedCategory),
        modifier = Modifier.clickable { categoryExpanded = !categoryExpanded }
    )

    DropdownMenu(
        expanded = categoryExpanded,
        onDismissRequest = {
            categoryExpanded = false
        }
    ) {
        categories.forEach { category ->
            DropdownMenuItem(onClick = {
                selectedCategory = category.category
                onCategorySelected(selectedCategory)
                categoryExpanded = false
            }) {
                Text(text = category.category)
            }
        }
    }
}