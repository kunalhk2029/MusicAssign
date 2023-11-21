package com.app.musicassign.framework.datasource.network

import android.content.Context
import com.app.musicassign.business.domain.model.MusicFileInfo
import java.util.*

class MusicNetworkServiceImpl
constructor(private val retrofitFileService: RetrofitMusicService, private val context: Context) :
    MusicNetworkService {
    override suspend fun getMusicFiles(): List<MusicFileInfo> {
        return retrofitFileService.getMusicFiles().data.map { it.mapToMusicFileInfo() }
    }
}