package com.pr.paymentreminder.presentation.paymentreminder.compose

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.onReleaseClick
import com.pr.paymentreminder.ui.theme.orElse
import com.pr.paymentreminder.ui.theme.spacing16

@Composable
@Stable
fun SimpleDropDownSelection(
    startValue: String?,
    list: List<String>,
    modifier: Modifier = Modifier,
    labelText: String = emptyString(),
    isMandatory: Boolean = false,
    trailingIconConfiguration: TrailingIconConfiguration = TrailingIconConfiguration(),
    onValueChange: (Int) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var focused by remember { mutableStateOf(false) }
    var selectedValue by remember { mutableStateOf(startValue) }
    var menuExpanded by remember { mutableStateOf(focused) }

    focused = menuExpanded

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .then(modifier)
    ) {
        DefaultEditText(
            value = selectedValue.orEmpty(),
            labelText = labelText.getMandatoryText(isMandatory),
            focusRequester = focusRequester,
            isFocused = focused,
            isEditable = false,
            trailingIcon = {
                TrailingIcon(trailingIconConfiguration, menuExpanded) {
                    menuExpanded = !menuExpanded
                    focused = true
                }
            },
            error = null,
            onValueChange = {},
            interactionSource = onReleaseClick {
                menuExpanded = !menuExpanded; focused = true
            }
        ) { focused = it }

        DropdownMenu(
            expanded = menuExpanded,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing16),
            onDismissRequest = { menuExpanded = false }
        ) {
            list.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedValue = s
                    menuExpanded = false
                    onValueChange(index)
                }) {
                    Text(s)
                }
            }
        }
    }
}

@Composable
@Stable
fun TrailingIcon(
    config: TrailingIconConfiguration,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f)

    val modifier = Modifier.clickable(onClick = onClick)
        .let { if (config.animation) it.rotate(rotation) else it }

    Image(
        painter = painterResource(id = config.iconDrawable),
        contentDescription = null,
        modifier = modifier
    )
}

data class TrailingIconConfiguration(
    val animation: Boolean = false,
    @DrawableRes val iconDrawable: Int = R.drawable.baseline_keyboard_arrow_down_24
)

fun String.getMandatoryText(isMandatory: Boolean): String =
    if (this.isBlank()) emptyString()
    else this.takeIf { !isMandatory }.orElse { "*$this" }