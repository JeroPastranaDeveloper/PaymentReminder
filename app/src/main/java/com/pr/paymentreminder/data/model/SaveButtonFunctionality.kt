package com.pr.paymentreminder.data.model

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.ui.theme.emptyString

data class SaveButtonFunctionality(
    val serviceName: TextFieldValue = TextFieldValue(),
    val selectedCategory: String = emptyString(),
    val serviceDate: String = emptyString(),
    val selectedPaymentType: String = emptyString(),
    val servicePrice: TextFieldValue = TextFieldValue(),
    val serviceId: String? = emptyString(),
    val selectedRemember: String = emptyString(),
    val comments: String = emptyString(),
    val imageUri: TextFieldValue = TextFieldValue(),
    val serviceUrl: TextFieldValue = TextFieldValue(),
    val service: Service? = Service(),
    val action: String = emptyString(),
    val context: Context? = null
)