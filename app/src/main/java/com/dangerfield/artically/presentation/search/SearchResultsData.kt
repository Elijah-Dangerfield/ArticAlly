package com.dangerfield.artically.presentation.search

import com.dangerfield.artically.domain.model.Article
import com.dangerfield.artically.presentation.topheadlines.TopHeadlineItem
import com.xwray.groupie.Section

data class SearchResultsData(
    val searchResults: List<Article> = listOf(),
    val searchTerm: String,
    val previousSearches: List<String> = listOf()
) {

    private fun searchResultsSection() : Section {
        val section = Section(SearchHeaderItem("\"${searchTerm}\""))
        section.update(this.searchResults.map { TopHeadlineItem(it) })
        return section
    }
}