package com.unitip.mobile.features.activity.commons

import android.content.Context
import com.unitip.mobile.features.activity.domain.models.SocialActivity

object SocialActivityCache {
    private const val PREFS_NAME = "social_cache"
    private const val KEY_LAST_MODIFIED = "last_modified"

    private var cache: CacheEntry? = null
    private const val CACHE_DURATION = 24 * 60 * 60 * 1000 // 1 day

    data class CacheEntry(
        val data: List<SocialActivity>,
        val timestamp: Long,
        val lastModified: String
    )

    // Simpan cache ke memori dan SharedPreferences
    fun set(context: Context, data: List<SocialActivity>, lastModified: String) {
        cache = CacheEntry(data, System.currentTimeMillis(), lastModified)
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LAST_MODIFIED, lastModified)
            .apply()
    }

    // Ambil nilai last-modified: pertama dari cache in-memory, jika null ambil dari SharedPreferences
    fun getLastModified(context: Context): String? {
        return cache?.lastModified ?: context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_LAST_MODIFIED, null)
    }

    // Ambil data cache (in-memory) jika masih dalam durasi CACHE_DURATION
    fun get(): List<SocialActivity>? {
        val currentCache = cache ?: return null
        return if (System.currentTimeMillis() - currentCache.timestamp < CACHE_DURATION) {
            currentCache.data
        } else {
            cache = null
            null
        }
    }

    // Hapus cache baik dari memori maupun SharedPreferences
    fun clear(context: Context) {
        cache = null
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_LAST_MODIFIED)
            .apply()
    }
}



