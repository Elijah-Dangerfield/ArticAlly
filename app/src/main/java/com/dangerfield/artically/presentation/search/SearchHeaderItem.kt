package com.dangerfield.artically.presentation.search

import android.view.View
import com.dangerfield.artically.R
import com.dangerfield.artically.databinding.LayoutSimpleHeaderBinding
import com.xwray.groupie.viewbinding.BindableItem

class SearchHeaderItem(private val title: String) : BindableItem<LayoutSimpleHeaderBinding>() {
    override fun bind(viewBinding: LayoutSimpleHeaderBinding, position: Int) {
        viewBinding.headerTitle.text = title
    }

    override fun getLayout() = R.layout.layout_simple_header

    override fun initializeViewBinding(view: View): LayoutSimpleHeaderBinding {
        return LayoutSimpleHeaderBinding.bind(view)
    }
}