package com.dangerfield.artically.domain.util

sealed class Resource <T,R>() {
    class Success<T,R>(val data : T) : Resource<T,R>()
    class Loading<T,R>(val data : T?) : Resource<T,R>()
    class Error<T,R>(val data: T?, val error: R) : Resource<T,R>()
}