package com.pr.paymentreminder.presentation.paymentreminder.compose

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.emptyString
import kotlinx.coroutines.delay
import kotlinx.parcelize.Parcelize
import java.util.UUID

interface ToastOwnerInApp {
    fun showToastInApp(data: CustomToastInfo, modifier: Modifier = Modifier)
    fun hideToastInApp() = Unit
}

sealed class DelayTime(open val time: Long) {
    data object DelayShort : DelayTime(2 * 1000L)
    data object DelayLong : DelayTime(5 * 1000L)
    data class DelayCustom(override val time: Long) : DelayTime(time)
}

@Parcelize
enum class ToastType: Parcelable {
    Success,
    Error
}

data class CustomToastInfo(
    private val id: String = UUID.randomUUID().toString(),
    val message: String = emptyString(),
    @DrawableRes val icon: Int = R.drawable.logo,
    val type: ToastType = ToastType.Success,
    val delay: DelayTime = DelayTime.DelayShort
)

fun ComposeView.showCustomToast(customToastInfo: CustomToastInfo, modifier: Modifier = Modifier) {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    setContent {
        ShowCustomToast(customToastInfo, MutableTransitionState(true), modifier)
    }
}

@Composable
fun ShowCustomToast(
    data: CustomToastInfo,
    visibleState: MutableTransitionState<Boolean>,
    modifier: Modifier = Modifier,
    key: String = UUID.randomUUID().toString()
) {
    LaunchedEffect(key1 = key) {
        delay(data.delay.time)
        visibleState.targetState = false
    }

    val density = LocalDensity.current

    AnimatedVisibility(
        visibleState = visibleState,
        enter = slideInVertically { with(density) { -40.dp.roundToPx() } } +
                expandVertically(expandFrom = Alignment.Top) +
                fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        TopToast(
            modifier = modifier,
            alertConfig = TopToastAlertConfig(
                icon = data.icon,
                type = data.type,
                text = data.message
            )
        )
    }
}