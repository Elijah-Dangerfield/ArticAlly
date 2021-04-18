package com.dangerfield.artically.presentation.util

import android.view.View

fun View.visibleIf(predicate: Boolean) {
    this.visibility = if(predicate) View.VISIBLE else View.GONE
}