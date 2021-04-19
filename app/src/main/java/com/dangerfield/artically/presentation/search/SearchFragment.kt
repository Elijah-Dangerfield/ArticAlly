package com.dangerfield.artically.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerfield.artically.R
import com.dangerfield.artically.databinding.FragmentSearchBinding
import com.dangerfield.artically.domain.model.Article
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var binding: FragmentSearchBinding
    private val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        observePreviouslySearched()
        observeSearchResults()
        observeSearchBarState()
    }
    private fun setupView() {
        observePreviouslySearched()
        observeSearchResults()
        observeSearchBarState()
        binding.rvSearch.layoutManager = LinearLayoutManager(view?.context)
        binding.rvSearch.adapter = adapter
        binding.searchBar.forceStateUpdate(SearchBar.State.OPENED())
        binding.searchBar.searchStateUpdater = searchViewModel
    }

    private fun observeSearchResults() {

    }

    private fun observePreviouslySearched() {

    }

    private fun observeSearchBarState() {
        searchViewModel.searchBarState.observe(viewLifecycleOwner,  {
            handleSearchBarState(it)
        })
    }

    private fun handleSearchBarState(it: SearchBar.State) {
        Log.d("ElijahSearch", "New State In View: ${it.name}")
        when (it) {
            is SearchBar.State.CLOSED -> handleClosedState()
            is SearchBar.State.OPENED -> handleOpenedState()
            is SearchBar.State.TYPING -> handleTypingState(it.term)
            is SearchBar.State.SEARCHED -> handleSearchedState()
        }
    }

    private fun handleSearchedState() {
        //no op? potential analytics taggin
    }

    private fun handleTypingState(term: String) {
//        if(rv_search.adapter != searchResults) rv_search.adapter = searchResults
//        rv_search.addOnScrolledListener {
//            if(searchBar.currentState !is SearchBar.State.SEARCHED) {
//                searchBar.forceUiUpdate(SearchBar.State.SEARCHED(tv_search.text?.trim().toString()))
//            }
//        }
//        debouncingQuery.onTextChange(term)
    }

    private fun handleOpenedState() {
//        rv_search.adapter = ConcatAdapter(previousSearchesAdapter, clearSearchesAdapter)
//        rv_search.addOnScrolledListener {
//            if(searchBar.currentState !is SearchBar.State.SEARCHED) {
//                searchBar.forceUiUpdate(SearchBar.State.SEARCHED(tv_search.text?.trim().toString()))
//            }
//        }
    }

    private fun handleClosedState() {
        NavHostFragment.findNavController(this).popBackStack()
    }



    fun onArticleClick(article: Article) {
//        searchBar.forceUiUpdate(SearchBar.State.SEARCHED(tv_search.text.toString()))
//        navigateToShow(show)
    }

    fun onRemoveSavedArticleClick(article: Article) {
       // searchViewModel.removeSearchedShow(show)
    }

    fun onSearchResultClicked(article: Article) {
       // searchBar.forceUiUpdate(SearchBar.State.SEARCHED(tv_search.text.toString()))
      //  searchViewModel.saveSearchedShow(result)
      //  navigateToShow(result.toSearchedShow())
    }
}