package com.dangerfield.artically.domain.usecases.getTopHeadlines

import com.dangerfield.artically.domain.model.TopHeadlines
import com.dangerfield.artically.domain.repo.ArticleRepository
import com.dangerfield.artically.domain.util.RateLimiter
import com.dangerfield.artically.domain.util.Resource
import kotlinx.coroutines.flow.*
import java.lang.Exception

class GetTopHeadlines (
    private val articleRepository: ArticleRepository,
    private val rateLimiter: RateLimiter
) {
    private val requestKey = "get_top_headlines"
    /*
    The problem with this is its just one huge try catch. I have no way to know which step went wrong
    Ill need a better system for granual error catching
     */
    fun invoke(forceRefresh: Boolean = false): Flow<Resource<TopHeadlines, GetTopHeadlinesError>> = flow {
        try {
            val cachedHeadlines = articleRepository.fetchCachedTopHeadlines().first()
            if(forceRefresh || shouldFetchNewTopHeadlines(cachedHeadlines)) {
                emit(Resource.Loading(cachedHeadlines))
                val newHeadlines = articleRepository.fetchNewTopHeadlines()
                articleRepository.replaceCachedHeadlines(newHeadlines)
                val cacheResults = articleRepository.fetchCachedTopHeadlines()
                val results :Flow<Resource<TopHeadlines, GetTopHeadlinesError>> = cacheResults.map{ Resource.Success(it) }
                emitAll(results)
            }else {
                val cacheResults = articleRepository.fetchCachedTopHeadlines()
                val results :Flow<Resource<TopHeadlines, GetTopHeadlinesError>> = cacheResults.map{ Resource.Success(it) }
                emitAll(results)
            }
        } catch (e: Exception) {
            val cacheResults = articleRepository.fetchCachedTopHeadlines()
            val results :Flow<Resource<TopHeadlines, GetTopHeadlinesError>> = cacheResults.map{ Resource.Error(it,error = GetTopHeadlinesError.UNKNOWN) }
            emitAll(results)
        }
    }

    private fun shouldFetchNewTopHeadlines(cachedHeadlines: TopHeadlines): Boolean {
        return (rateLimiter.shouldFetch(requestKey) || cachedHeadlines.articles.isEmpty() )
    }
}