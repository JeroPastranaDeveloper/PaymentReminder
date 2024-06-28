package com.pr.paymentreminder.presentation.paymentreminder.fragments.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pr.paymentreminder.R
import com.pr.paymentreminder.data.model.ALL_CATEGORIES
import com.pr.paymentreminder.data.model.ButtonActions
import com.pr.paymentreminder.data.model.CustomSnackBarConfig
import com.pr.paymentreminder.data.model.CustomSnackBarType
import com.pr.paymentreminder.data.model.Service
import com.pr.paymentreminder.presentation.paymentreminder.compose.CardPlaceHolder
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomChip
import com.pr.paymentreminder.presentation.paymentreminder.compose.CustomSnackBar
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceCard
import com.pr.paymentreminder.presentation.paymentreminder.compose.ServiceDialog
import com.pr.paymentreminder.presentation.paymentreminder.fragments.home.HomeViewContract.UiState
import com.pr.paymentreminder.ui.theme.Visible
import com.pr.paymentreminder.ui.theme.almond
import com.pr.paymentreminder.ui.theme.backGroundColor
import com.pr.paymentreminder.ui.theme.brokenWhite
import com.pr.paymentreminder.ui.theme.dimen0
import com.pr.paymentreminder.ui.theme.dimen56
import com.pr.paymentreminder.ui.theme.dimen72
import com.pr.paymentreminder.ui.theme.emptyString
import com.pr.paymentreminder.ui.theme.getBackgroundColors
import com.pr.paymentreminder.ui.theme.getPastelColors
import com.pr.paymentreminder.ui.theme.spacing0
import com.pr.paymentreminder.ui.theme.spacing144
import com.pr.paymentreminder.ui.theme.spacing16
import com.pr.paymentreminder.ui.theme.spacing40
import com.pr.paymentreminder.ui.theme.spacing56
import com.pr.paymentreminder.ui.theme.spacing64
import com.pr.paymentreminder.ui.theme.spacing72
import com.pr.paymentreminder.ui.theme.spacing8
import com.pr.paymentreminder.ui.theme.white

@Composable
fun HomeFactory(
    state: UiState,
    action: HomeActions
) {
    var selectedService by remember { mutableStateOf<Service?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val snackBarHeight = if (state.showSnackBar) dimen72 else dimen0
    val fabHeight = if (state.showSnackBar) spacing144 else spacing72
    val animatedSnackBarHeight by animateDpAsState(
        targetValue = snackBarHeight,
        label = emptyString()
    )
    val animatedFABHeight by animateDpAsState(
        targetValue = fabHeight,
        label = emptyString()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = if (state.isLoading) spacing64 else spacing0)
            .background(white)
    ) {
        if (state.isLoading) {
            Column(modifier = Modifier.fillMaxSize()) {
                CreateCardPlaceHolder()
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = spacing64)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(start = spacing8, end = spacing8)
                ) {
                    CustomChip(title = stringResource(id = R.string.all_categories), onClick = {
                        action.onFilterCategory(ALL_CATEGORIES)
                    }, selected = state.selectedCategory == ALL_CATEGORIES)
                    state.categories.forEach { category ->
                        CustomChip(title = category.name, onClick = {
                            action.onFilterCategory(category.name)
                        }, selected = state.selectedCategory == category.name)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacing40, bottom = spacing56)
                ) {
                    itemsIndexed(state.services) { index, service ->
                        val color = getPastelColors()[index % getPastelColors().size]
                        ServiceCard(
                            service = service,
                            onClick = {
                                selectedService = service
                                showDialog = true
                            },
                            color = color,
                            context = context
                        )
                    }
                }

                Spacer(modifier = Modifier.size(dimen56))

                Visible(state.showSnackBar) {
                    CustomSnackBar(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = animatedSnackBarHeight),
                        config = getSnackBarConfig(state.showSnackBarType),
                        onClick = { action.onRestoreDeletedService(state.serviceToRemove) },
                        onDismiss = { action.onDismissSnackBar() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(animatedSnackBarHeight))
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    bottom = animatedFABHeight,
                    end = spacing16
                ),
            onClick = {
                action.onClickFAB(null, ButtonActions.ADD.name)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }

        if (showDialog) {
            ServiceDialog(
                service = selectedService,
                onClick = {
                    selectedService = null
                    showDialog = false
                },
                onEdit = {
                    showDialog = false
                    action.onClickFAB(selectedService?.id.orEmpty(), ButtonActions.EDIT.name)
                },
                onRemove = {
                    showDialog = false
                    action.onRemoveService(selectedService ?: Service())
                }
            )
        }
    }
}

@Composable
private fun CreateCardPlaceHolder() {
    for (i in 0 until 10) {
        CardPlaceHolder()
    }
}

@Composable
private fun getSnackBarConfig(type: CustomSnackBarType): CustomSnackBarConfig {
    val (icon, message) = when (type) {
        CustomSnackBarType.CREATE -> Pair(
            R.drawable.add,
            R.string.service_created
        )

        CustomSnackBarType.UPDATE -> Pair(
            R.drawable.update_icon,
            R.string.service_updated
        )

        CustomSnackBarType.DELETE -> Pair(
            R.drawable.baseline_delete_24,
            R.string.service_removed
        )

        else -> Pair(R.drawable.add, R.string.service_created)
    }
    return CustomSnackBarConfig(icon, stringResource(id = message), type)
}
