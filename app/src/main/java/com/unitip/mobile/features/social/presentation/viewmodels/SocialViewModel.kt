package com.unitip.mobile.features.social.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unitip.mobile.features.social.commons.SocialActivityCache
import com.unitip.mobile.features.social.data.repositories.SocialRepository
import com.unitip.mobile.features.social.presentation.state.SocialState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialViewModel @Inject constructor(
    private val socialRepository: SocialRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(SocialState())
    val uiState = _uiState.asStateFlow()

    fun getActivities(forceRefresh: Boolean = false) = viewModelScope.launch {
        if (forceRefresh) {
            SocialActivityCache.clear(context)
        }

        _uiState.update { it.copy(detail = SocialState.Detail.Loading) }

        socialRepository.getSocialActivities()
            .onLeft { failure ->
                _uiState.update {
                    it.copy(
                        detail = SocialState.Detail.Failure(
                            message = failure.message
                        )
                    )
                }
            }
            .onRight { activities ->
                _uiState.update {
                    it.copy(
                        detail = SocialState.Detail.Success(
                            activities = activities
                        )
                    )
                }
            }
    }
}