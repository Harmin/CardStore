package com.exercise.cardstore.presentation.util.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.exercise.cardstore.R

@Composable
fun showErrorDialog(showDialog: MutableState<Boolean>, message: String) {
    ShowDialog(
        item = { },
        state = showDialog,
        title = stringResource(R.string.error),
        message = message,
        confirmButtonText = stringResource(R.string.ok),
        confirmButton = { showDialog.value = false },
    )
}