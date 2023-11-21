package com.app.musicassign.framework.presentation.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.musicassign.business.domain.core.DataState
import com.app.musicassign.business.domain.core.UIComponent
import com.app.musicassign.business.interactors.GetMusicFilesData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MusicListPreviewScreenViewModel
@Inject constructor(
    private val getMusicFilesData: GetMusicFilesData,
) : ViewModel() {

    private val _state = mutableStateOf(MusicListPreviewScreenViewState())

    val state get() = _state

    init {
        onTriggerEvent(MusicListPreviewScreenStateEvents.GetMusicFilesData)
    }

    fun onTriggerEvent(event: MusicListPreviewScreenStateEvents) {
        when (event) {
            MusicListPreviewScreenStateEvents.GetMusicFilesData -> {
                getFilesData()
            }

            is MusicListPreviewScreenStateEvents.UpdateSelectedTabIndex -> {
                _state.value = _state.value.copy(selectedTabIndex = event.index)
            }

            is MusicListPreviewScreenStateEvents.UpdateActiveMusicItem -> {
                _state.value = _state.value.copy(activeMusicInfo =
                ActiveMusicInfo(
                    event.musicFileInfo,
                    event.musicFileInfo.is_top_track,
                    if (event.musicFileInfo.is_top_track)
                        state.value.topTrackMusicfiles.indexOf(event.musicFileInfo)
                    else
                        state.value.forYouMusicfiles.indexOf(event.musicFileInfo)
                ))
            }
            else -> {}
        }
    }

    private fun getFilesData() {
        getMusicFilesData.execute().onEach {
            when (it) {
                is DataState.Loading -> {
                    _state.value = _state.value.copy(
                        progressBarState = it.progressBarState
                    )
                }
                is DataState.Data -> {
                    _state.value = _state.value.copy(
                        topTrackMusicfiles = it.data?.filter { it.is_top_track == true } ?: listOf(),
                        forYouMusicfiles = it.data?.filter { it.is_top_track == false } ?: listOf(),
                    )
                }
                is DataState.Response -> {
                    if (it.uiComponent is UIComponent.Dialog) {
                    } else {
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}