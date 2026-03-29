package me.bitlinker.compose800.ui.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class AppNavigator {
    private val mutableState = MutableStateFlow(AppNavigationState(dialog = null))

    val state: StateFlow<AppNavigationState> = mutableState

    fun pushDialog(dialog: AppDialog) {
        mutableState.value = mutableState.value.copy(dialog = dialog)
    }

    fun dismissDialog() {
        mutableState.value = mutableState.value.copy(dialog = null)
    }
}