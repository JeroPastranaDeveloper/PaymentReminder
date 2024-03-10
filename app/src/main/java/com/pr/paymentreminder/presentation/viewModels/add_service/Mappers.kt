package com.pr.paymentreminder.presentation.viewModels.add_service

import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.ui.theme.emptyTextField

data class ServiceTextField (
    var id: TextFieldValue = emptyTextField(),
    val category: TextFieldValue = emptyTextField(),
    val color: TextFieldValue = emptyTextField(),
    var date: TextFieldValue = emptyTextField(),
    var name: TextFieldValue = emptyTextField(),
    val price: TextFieldValue = emptyTextField(),
    val remember: TextFieldValue = emptyTextField(),
    val type: TextFieldValue = emptyTextField(),
    val image: TextFieldValue = emptyTextField(),
    val url: TextFieldValue? = emptyTextField()
)

fun Service.toServiceTextField() : ServiceTextField =
    ServiceTextField(
        id = TextFieldValue(this.id),
        category = TextFieldValue(this.category),
        color = TextFieldValue(this.color),
        date = TextFieldValue(this.date),
        name = TextFieldValue(this.name),
        price = TextFieldValue(this.price),
        remember = TextFieldValue(this.remember),
        type = TextFieldValue(this.type),
        image = TextFieldValue(this.image),
        url = this.url?.let { TextFieldValue(it) }
    )