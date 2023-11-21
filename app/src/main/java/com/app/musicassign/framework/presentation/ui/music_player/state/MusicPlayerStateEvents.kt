package com.app.musicassign.framework.presentation.ui

import com.app.musicassign.business.domain.model.MusicFileInfo


sealed class MusicPlayerStateEvents {
    data class UpdateAvailableMusicFiles(val files: List<MusicFileInfo>,val index:Int):MusicPlayerStateEvents()
}