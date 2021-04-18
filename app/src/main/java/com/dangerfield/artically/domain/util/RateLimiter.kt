package com.dangerfield.artically.domain.util

interface RateLimiter {
    fun shouldFetch(key: String) : Boolean
    fun reset(key: String)
}