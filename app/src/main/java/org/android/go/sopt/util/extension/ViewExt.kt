package org.android.go.sopt.util.extension

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.makeSnackBar(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
    Snackbar.make(this, message, duration)
        .setBackgroundTint(Color.WHITE)
        .setTextColor(Color.BLACK)
        .show()
}

fun View.makeToast(message: String, isShort: Boolean = true) {
    val duration = if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(context, message, duration).show()
}