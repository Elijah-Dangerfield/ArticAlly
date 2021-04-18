package com.dangerfield.artically.presentation.topheadlines

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dangerfield.artically.R
import com.dangerfield.artically.databinding.FragmentArticleDetailsBinding
import com.dangerfield.artically.domain.model.Article
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

class ArticleDetailsFragment : Fragment() {

    private lateinit var binding : FragmentArticleDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val article = arguments?.getParcelable<Article>(articleKey)
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        initViewsWith(binding.root, article)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureToolBar()
    }

    private fun configureToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.animToolbar)
        binding.animToolbar.title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offSet ->
            //offset: 0 means fully expanded
            if(abs(offSet) > 200) {
                setTitle(true)
            }else{
                setTitle(false)
            }
        })
    }

    private fun setTitle(needsShown: Boolean) {
        //only set the title if it needs set
        if(needsShown && binding.collapsingToolbar.title.isNullOrEmpty()){
            binding.collapsingToolbar.title = binding.includedHeader.tvArticlePublisher.text
        }else if (!needsShown && binding.collapsingToolbar.title != ""){
            binding.collapsingToolbar.title = ""
        }
    }

    private fun initViewsWith(view: View, article: Article?) {
        view.run {
            binding.includedHeader.ivArticleHeader.setColorFilter(
                ContextCompat.getColor(view.context,
                    R.color.darkFilter),
                PorterDuff.Mode.SRC_ATOP
            )

            article?.let {
                binding.includedContent.tvArticleText.text = it.content
                binding.includedContent.tvArticleDescription.text = it.description
                binding.includedHeader.tvArticlePublisher.text = it.source?.name?.substringBefore(".")
                binding.includedContent.tvArticleDate.text = it.publishedAt
                binding.includedContent.tvArticleAuthor.text = (it.author ?: "Unknown Author")
                binding.includedContent.tvArticleLink.text = it.url
                binding.includedHeader.tvArticleHeader.text = it.title

                Glide
                    .with(this)
                    .load(it.urlToImage)
                    .centerCrop()
                    .into(binding.includedHeader.ivArticleHeader)
            }
        }
    }

    companion object {
        val articleKey = "1234_I_want_an_article"
    }
}