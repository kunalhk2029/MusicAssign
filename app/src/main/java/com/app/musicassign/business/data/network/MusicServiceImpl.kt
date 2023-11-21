package com.app.musicassign.business.data.network

import com.app.musicassign.business.domain.model.MusicFileInfo
import com.app.musicassign.framework.datasource.network.MusicNetworkService

class MusicServiceImpl
constructor(private val musicNetworkService: MusicNetworkService) : MusicService {
    override suspend fun getMusicFiles(): List<MusicFileInfo> {
        return musicNetworkService.getMusicFiles()
    }
}