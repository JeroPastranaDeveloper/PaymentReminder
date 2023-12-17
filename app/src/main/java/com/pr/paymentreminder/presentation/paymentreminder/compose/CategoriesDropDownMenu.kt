package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Categories
import com.pr.paymentreminder.ui.theme.emptyString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CategoriesDropDownMenu(
    categories: List<Categories>,
    initialSelectedCategory: String = emptyString(),
    textHelper: StateFlow<String?>,
    textHelperText: String,
    onCategorySelected: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(initialSelectedCategory) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val hasHelperText by textHelper.collectAsState(null)

    Text(
        text = stringResource(id = R.string.category, selectedCategory),
        modifier = Modifier.clickable { categoryExpanded = !categoryExpanded }
    )

    hasHelperText?.let {
        ServiceSeparator()
        HelperText(textHelperText)
    }

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

@Preview(showBackground = true)
@Composable
private fun CategoriesDropDownMenuPreview() {
    Column {
        CategoriesDropDownMenu(
            categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS),
            initialSelectedCategory = Categories.HOBBY.category,
            textHelper = MutableStateFlow<String?>(null),
            textHelperText = stringResource(id = R.string.invalid_service_category)
        ) { /* Nothing */ }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesDropDownMenuHelperTextPreview() {
    Column {
        CategoriesDropDownMenu(
            categories = listOf(Categories.AMAZON, Categories.HOBBY, Categories.PLATFORMS),
            textHelper = MutableStateFlow(emptyString()),
            textHelperText = stringResource(id = R.string.invalid_service_category)
        ) { /* Nothing */ }
    }
}