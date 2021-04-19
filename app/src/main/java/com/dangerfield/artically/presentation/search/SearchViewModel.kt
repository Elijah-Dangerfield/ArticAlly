package com.dangerfield.artically.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.artically.domain.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel(), SearchStateUpdater {
    private var _previouslySearched = MutableLiveData<List<Unit>>()
    val previouslySearched: LiveData<List<Unit>>
        get() = _previouslySearched

    private var _searchResults = MutableLiveData<Pair<List<Article>, String>>()
    val searchResults: LiveData<Pair<List<Article>, String>>
        get() = _searchResults

    private var _searchBarState = MutableLiveData<SearchBar.State>()
    val searchBarState: LiveData<SearchBar.State>
        get() = _searchBarState

    fun fetchPreviouslySearched() {

    }

    fun fetchSearchResults(term: String?) {

    }

     fun saveArticleSearch(article: Article) {
    }

     fun removeArticleSearch(article: Article) {
    }

     fun clearSearchedShows() {
        _previouslySearched.postValue(emptyList())
    }

     override fun updateSearchState(state: SearchBar.State) {
        _searchBarState.postValue(state)
    }
}

