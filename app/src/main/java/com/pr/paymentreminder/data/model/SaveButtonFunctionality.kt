package com.pr.paymentreminder.data.model

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue
import com.pr.paymentreminder.presentation.paymentreminder.fragments.ButtonActions

data class SaveButtonFunctionality(
    val serviceName: TextFieldValue,
    val selectedCategory: String,
    val serviceDate: String,
    val selectedPaymentType: String,
    val servicePrice: TextFieldValue,
    val serviceId: String?,
    val selectedRemember: String,
    val imageUri: TextFieldValue,
    val serviceUrl: TextFieldValue,
    val service: Service?,
    val action: String,
    val context: Context
)