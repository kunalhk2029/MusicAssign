package com.app.musicassign.framework.presentation.ui

import com.app.musicassign.business.domain.core.ProgressBarState
import com.app.musicassign.business.domain.model.MusicFileInfo
import java.io.Serializable

data class MusicPlayerState(
    val musicFiles: List<MusicFileInfo>?=null
)

data class ActiveMusicInfo(
    val  activeMusicItem: MusicFileInfo,
    val isTopTrack:Boolean,
    val  activeMusicItemIndex: Int=-1,
):Serializable
