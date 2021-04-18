package com.dangerfield.artically.presentation.topheadlines

import android.view.View
import com.bumptech.glide.Glide
import com.dangerfield.artically.R
import com.dangerfield.artically.databinding.ItemTopHeadlineBinding
import com.dangerfield.artically.domain.model.Article
import com.dangerfield.artically.presentation.util.visibleIf
import com.xwray.groupie.viewbinding.BindableItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TopHeadlineItem(private val data: Article) : BindableItem<ItemTopHeadlineBinding>() {
    override fun bind(viewBinding: ItemTopHeadlineBinding, position: Int) {
        viewBinding.apply {
            articleTitle.text = data.title ?: "Untitled"
            articleDetails.text = data.publishedAt?.toReadableDate() ?: "No date"
            articlePreview.text = data.description ?: "Click to read more"

            imageCard.visibleIf(data.urlToImage != null)

            data.urlToImage?.let {
                Glide.with(articleImage.context)
                    .load(data.urlToImage)
                    .centerCrop()
                    .into(articleImage)
            }
        }
    }


    override fun getLayout(): Int {
        return R.layout.item_top_headline
    }

    override fun initializeViewBinding(view: View): ItemTopHeadlineBinding {
        return ItemTopHeadlineBinding.bind(view)
    }

    /**
     * converts time stamp given by api to a readable format
     */
    private fun String.toReadableDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = formatter.parse(this)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date.toString().dropLast(18)
    }
}