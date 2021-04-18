package com.dangerfield.artically.domain.repo

import com.dangerfield.artically.domain.model.TopHeadlines
import kotlinx.coroutines.flow.Flow


/*
Repository modules handle data operations. They provide a clean API so that the rest of the app
can retrieve this data easily. They know where to get the data from and what API calls to make when
data is updated. You can consider repositories to be mediators between different data sources,
such as persistent models, web services, and caches.

https://developer.android.com/jetpack/guide

 */
interface ArticleRepository {
    suspend fun fetchNewTopHeadlines() : TopHeadlines
    suspend fun fetchCachedTopHeadlines() : Flow<TopHeadlines>
    suspend fun replaceCachedHeadlines(headlines: TopHeadlines)
}