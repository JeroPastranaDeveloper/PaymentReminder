package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import com.pr.paymentreminder.data.consts.Constants
import com.pr.paymentreminder.data.model.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Service.getDate(): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)
    return LocalDate.parse(this.date, formatter)
}