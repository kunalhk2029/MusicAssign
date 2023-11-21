package com.app.musicassign.framework.presentation.ui

import com.app.musicassign.business.domain.model.MusicFileInfo


sealed class MusicListPreviewScreenStateEvents {
    object GetMusicFilesData:MusicListPreviewScreenStateEvents()
    data class UpdateSelectedTabIndex(val index: Int):MusicListPreviewScreenStateEvents()
    data class UpdateActiveMusicItem(val musicFileInfo: MusicFileInfo):MusicListPreviewScreenStateEvents()

}