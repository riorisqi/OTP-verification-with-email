package com.sendmailtest

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import java.util.concurrent.TimeUnit

class CacheConfig {
    // init cache with 3 minutes data expiration time after inserted to cache
    val cache: LoadingCache<String, String> = CacheBuilder.newBuilder()
        .expireAfterWrite(3, TimeUnit.MINUTES)
        .build(
            CacheLoader.from { key: String -> key }
        )
}