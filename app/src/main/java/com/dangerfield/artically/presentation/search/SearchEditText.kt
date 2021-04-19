package com.dangerfield.artically.presentation.search

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

class SearchEditText : AppCompatEditText {

    private var keyImeChangeListener : KeyImgeChangeListener? = null

    interface KeyImgeChangeListener {
        fun onKeyIme(keyCode: Int, event: KeyEvent) : Boolean
    }

    fun setKeyImeChangeListener(keyImeChangeListener : KeyImgeChangeListener) {
        this.keyImeChangeListener = keyImeChangeListener
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context!!, attrs, defStyle) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    constructor(context: Context?) : super(context!!) {}

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        return keyImeChangeListener?.onKeyIme(keyCode, event) ?: super.onKeyPreIme(keyCode, event)
    }
}