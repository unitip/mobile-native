package com.unitip.mobile.features.social.commons

import com.unitip.mobile.features.social.domain.models.SocialActivity

object SocialActivityCache {
    private data class CacheEntry(
        val data: List<SocialActivity>,
        val timestamp: Long
    )

    private var cache: CacheEntry? = null
    private const val CACHE_DURATION = 24 * 60 * 60 * 1000 // 24 jam dalam milidetik

    fun set(data: List<SocialActivity>) {
        cache = CacheEntry(data, System.currentTimeMillis())
    }

    fun get(): List<SocialActivity>? {
        val currentCache = cache ?: return null

        // Cek apakah cache masih valid (belum lebih dari 24 jam)
        return if (System.currentTimeMillis() - currentCache.timestamp < CACHE_DURATION) {
            currentCache.data
        } else {
            cache = null
            null
        }
    }

    fun clear() {
        cache = null
    }
}
