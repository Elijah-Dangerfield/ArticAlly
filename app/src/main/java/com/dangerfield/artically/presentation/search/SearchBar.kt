package com.dangerfield.artically.presentation.search

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.dangerfield.artically.R
import com.dangerfield.artically.databinding.LayoutSearchBarBinding
import com.dangerfield.artically.presentation.util.*

class SearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var searchStateUpdater: SearchStateUpdater? = null
    private val binding = LayoutSearchBarBinding.bind(inflate(context, R.layout.layout_search_bar, this))


    private var isLocked = false
        set(value) {
            field = value
            binding.tvSearch.isEnabled = !value
        }

    private var state: State = State.OPENED()
        set(value) {
            if(!isLocked) {
                field = value
                onStateChanged(value)
            }
        }

    var currentState: State =  State.OPENED()

    init {
        setupView()
    }

    sealed class State(searchTerm: String?, val name: String) {
        class CLOSED : State(null, "closed")
        class OPENED : State(null, "opened")
        class TYPING(val term: String) : State(term, "Typing")
        class SEARCHED(val term: String) : State(term, "searched")
    }

    fun forceStateUpdate(state: State) {
        this.state = state
    }

    fun forceUiUpdate(state: State) {
        onStateChanged(state, false)
    }

    private fun setupView() {
        binding.tvSearch.addOnFocusedListener {
            state = State.OPENED()
        }
        binding.tvSearch.onSearchPressed { s ->
            state = State.SEARCHED(s)
        }
        binding.btnBack.setOnClickListener {
            state = if(state is State.TYPING || state is State.SEARCHED) {
                State.OPENED()
            } else {
                State.CLOSED()
            }
        }
        binding.btnClear.setOnClickListener {
            state = State.OPENED()
        }

        binding.tvSearch.onTextChanged {
            handleTextChanged(it)
        }

        binding.tvSearch.setKeyImeChangeListener( object: SearchEditText.KeyImgeChangeListener {
            override fun onKeyIme(keyCode: Int, event: KeyEvent): Boolean {
                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    forceUiUpdate(State.SEARCHED(binding.tvSearch.text.toString()))
                    return true // event handled by us, dont dispatch
                }
                return false // we didnt handle it, dispatch event
            }
        })
    }

    private fun handleTextChanged(it: String) {
        if (it.isEmpty() && state !is State.CLOSED) {
            state = State.OPENED()
        }
        // setting state to searched and clearing the text will trigger text change
        if (it.isNotEmpty() && state !is State.SEARCHED) state = State.TYPING(it)
    }

    private fun onStateChanged(state: State, notifyCallback: Boolean = true) {
        if(notifyCallback) searchStateUpdater?.updateSearchState(state)
        currentState = state
        when (state) {
            is State.CLOSED  -> {binding.tvSearch.closeKeyboard()}
            is State.OPENED   -> setOpenedState()
            is State.TYPING   -> setTypingState()
            is State.SEARCHED -> setSearchedState()
        }
    }

    private fun setTypingState() {
        binding.btnClear.visibility = View.VISIBLE
    }

    private fun setSearchedState() {
        binding.tvSearch.closeKeyboard()
        binding.btnClear.visibility = View.GONE
        binding.btnBack.visibility = View.VISIBLE
        ResourcesCompat.getColor(resources, R.color.lightGrey, null).let {
            binding.searchParent.setCardBackgroundColor(ColorStateList.valueOf(it))
        }
        binding.tvSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    private fun setOpenedState() {
        binding.tvSearch.text?.clear()
        binding.btnClear.goneIf(binding.tvSearch.text.isNullOrEmpty())
        binding.btnBack.visibility = View.VISIBLE
        ResourcesCompat.getColor(resources, R.color.lightGrey, null).let {
            binding.searchParent.setCardBackgroundColor(ColorStateList.valueOf(it))
        }
        binding.tvSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        binding.tvSearch.openKeyboard()
    }
}