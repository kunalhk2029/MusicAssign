package com.app.musicassign.framework.presentation.ui.music_player

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.app.musicassign.business.domain.model.MusicFileInfo
import com.app.musicassign.framework.presentation.ui.MusicPlayerState
import com.app.musicassign.framework.presentation.ui.MusicPlayerStateEvents
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicPlayerViewModel
@Inject constructor(
) : ViewModel() {

    private var _state: MutableState<MusicPlayerState> =
        mutableStateOf(MusicPlayerState())

    val state get() = _state

    fun onEvent(event: MusicPlayerStateEvents) {
        when (event) {

            is MusicPlayerStateEvents.UpdateAvailableMusicFiles -> {
                _state.value = _state.value.copy(musicFiles = event.files)
            }
            else -> {}
        }
    }
}