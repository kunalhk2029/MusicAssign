package com.app.musicassign.framework.datasource.network

import com.app.musicassign.business.data.network.EndPoints.MUSIC_FILES_INFO
import com.app.musicassign.framework.datasource.network.model.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RetrofitMusicService {
    @GET
    suspend fun getMusicFiles(@Url url: String = MUSIC_FILES_INFO): ApiResponse
}