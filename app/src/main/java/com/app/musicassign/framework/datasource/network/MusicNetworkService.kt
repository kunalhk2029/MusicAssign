package com.app.musicassign.framework.datasource.network

import com.app.musicassign.business.domain.model.MusicFileInfo

interface MusicNetworkService {
    suspend fun getMusicFiles(): List<MusicFileInfo>
}