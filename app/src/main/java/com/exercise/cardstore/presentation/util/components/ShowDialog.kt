package com.exercise.cardstore.presentation.util.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun <T> ShowDialog(
    item: T,
    state: MutableState<Boolean>,
    title: String? = null,
    message: String,
    confirmButtonText: String,
    dismissButtonText: @Composable (() -> Unit)? = null,
    confirmButton: (T) -> Unit,
    dismissButton: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {  state.value = false },
        title = {
            if (title != null) {
                Text(title)
            }
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(
                onClick = { confirmButton(item) }
            ) {
                Text(confirmButtonText)
            }
        },
        dismissButton = {
            if (dismissButton != null) {
                Button(
                    onClick = dismissButton
                ) {
                    dismissButtonText
                }
            }
        }
    )
}