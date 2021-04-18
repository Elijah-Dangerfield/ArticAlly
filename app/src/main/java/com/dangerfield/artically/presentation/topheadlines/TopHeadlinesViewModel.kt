package com.dangerfield.artically.presentation.topheadlines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dangerfield.artically.domain.model.TopHeadlines
import com.dangerfield.artically.domain.usecases.getTopHeadlines.GetTopHeadlines
import com.dangerfield.artically.domain.usecases.getTopHeadlines.GetTopHeadlinesError
import com.dangerfield.artically.domain.util.Resource
import com.dangerfield.artically.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val getTopHeadlines: GetTopHeadlines
) : ViewModel() {

    init {
        getTopHeadlines()
    }

    private val _topHeadlines : MutableLiveData<TopHeadlines> = MutableLiveData()
    val topHeadlines : MutableLiveData<TopHeadlines>
    get() = _topHeadlines

    private val _topHeadlinesLoading : MutableLiveData<Boolean> = MutableLiveData()
    val topHeadlinesLoading : MutableLiveData<Boolean>
        get() = _topHeadlinesLoading

    private val _topHeadlinesError : SingleLiveEvent<GetTopHeadlinesError> = SingleLiveEvent()
    val topHeadlinesError : SingleLiveEvent<GetTopHeadlinesError>
        get() = _topHeadlinesError


    fun getTopHeadlines(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getTopHeadlines.invoke(forceRefresh).collect {
                _topHeadlinesLoading.postValue(it is Resource.Loading)
                when(it) {
                    is Resource.Error -> {
                        _topHeadlinesError.postValue(it.error)
                        it.data?.let {data -> _topHeadlines.postValue(data) }

                    }
                    is Resource.Loading -> {
                        it.data?.let {data -> _topHeadlines.postValue(data) }
                    }
                    is Resource.Success -> {
                        _topHeadlines.postValue(it.data)
                    }
                }
            }
        }
    }
}