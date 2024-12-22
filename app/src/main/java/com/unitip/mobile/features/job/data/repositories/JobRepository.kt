package com.unitip.mobile.features.job.data.repositories

import com.unitip.mobile.shared.data.managers.SessionManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JobRepository @Inject constructor(
    private val sessionManager: SessionManager,
) {
}