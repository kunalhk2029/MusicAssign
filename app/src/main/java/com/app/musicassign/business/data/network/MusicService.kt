package com.app.musicassign.business.data.network

import com.app.musicassign.business.domain.model.MusicFileInfo


interface MusicService {
    suspend fun getMusicFiles():List<MusicFileInfo>
}