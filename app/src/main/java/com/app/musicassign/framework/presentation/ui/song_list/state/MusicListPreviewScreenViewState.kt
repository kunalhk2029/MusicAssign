package com.app.musicassign.framework.presentation.ui

import com.app.musicassign.business.domain.core.ProgressBarState
import com.app.musicassign.business.domain.model.MusicFileInfo

data class MusicListPreviewScreenViewState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val forYouMusicfiles: List<MusicFileInfo> = listOf(),
    val topTrackMusicfiles: List<MusicFileInfo> = listOf(),
    val selectedTabIndex: Int = 0,
    val activeMusicInfo: ActiveMusicInfo? = null,
)