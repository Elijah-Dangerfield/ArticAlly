package com.dangerfield.artically.presentation.search

interface SearchStateUpdater {
    fun updateSearchState(state: SearchBar.State)
}