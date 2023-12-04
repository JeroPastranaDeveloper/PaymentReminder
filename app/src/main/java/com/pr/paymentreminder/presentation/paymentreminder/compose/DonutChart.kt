package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.Service
import kotlin.random.Random

@Composable
fun DonutChart(services: List<Service>) {
    val context = LocalContext.current
    val servicesString = stringResource(id = R.string.services)
    val pieChart = remember {
        PieChart(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    LaunchedEffect(services) {
        with(pieChart) {
            val entries = services.map { PieEntry(it.price.toFloat(), it.name) }
            val dataSet = PieDataSet(entries, servicesString)
            dataSet.colors = services.map { randomColor() }
            dataSet.sliceSpace = 2f
            dataSet.valueTextColor = Color.WHITE
            dataSet.valueTextSize = 12f

            legend.isEnabled = false
            animateY(1000, Easing.EaseInOutCubic)
            setUsePercentValues(false)
            description.isEnabled = false
            isDrawHoleEnabled = true
            holeRadius = 35f
            transparentCircleRadius = 40f

            val data = PieData(dataSet)
            pieChart.data = data
        }
    }

    AndroidView({ pieChart }) { view ->
        view.invalidate()
    }
}

fun randomColor(): Int {
    val rnd = Random
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}