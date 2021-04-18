package com.dangerfield.artically.presentation.topheadlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerfield.artically.R
import com.dangerfield.artically.databinding.FragmentTopHeadlinesBinding
import com.dangerfield.artically.domain.model.TopHeadlines
import com.dangerfield.artically.domain.usecases.getTopHeadlines.GetTopHeadlinesError
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment(R.layout.fragment_top_headlines) {

    private val viewModel : TopHeadlinesViewModel by viewModels()
    private lateinit var binding : FragmentTopHeadlinesBinding
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.topHeadlines.observe(viewLifecycleOwner, {
            updateTopHeadlines(it)
        })

        viewModel.topHeadlinesError.observe(viewLifecycleOwner, {
            showTopHeadlinesError(it)
        })

        viewModel.topHeadlinesLoading.observe(viewLifecycleOwner, {
            showTopHeadlinesLoading(it)
        })
    }

    private fun setupView() {
        setupRecyclerView()
        setupRefresher()
    }

    private fun setupRecyclerView() {
        binding.articlesRecyclerView.layoutManager = LinearLayoutManager(view?.context)
        binding.articlesRecyclerView.adapter = adapter
        adapter.setOnItemClickListener { item, view ->
            val bundle = Bundle()
            (item as? TopHeadlineItem)?.let {
                bundle.putParcelable(ArticleDetailsFragment.articleKey,it.data)
                Navigation.findNavController(view).navigate(R.id.action_topHeadlinesFragment_to_articleDetailsFragment,bundle)
            }
        }
    }

    private fun setupRefresher() {
        binding.swipeRefreshLayout.setColorSchemeResources( R.color.black, android.R.color.holo_blue_light
            , android.R.color.holo_blue_dark)

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.getTopHeadlines(forceRefresh = true) }
    }


    private fun updateTopHeadlines(topHeadlines: TopHeadlines) {
        adapter.update(topHeadlines.articles.map { TopHeadlineItem(it) })
    }

    private fun showTopHeadlinesError(error: GetTopHeadlinesError) {
        Toast.makeText(context, "Got some error", Toast.LENGTH_SHORT).show()
    }

    private fun showTopHeadlinesLoading(loading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = loading
    }
}