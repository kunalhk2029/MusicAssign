package com.app.musicassign.business.interactors

import com.app.musicassign.business.data.network.MusicService
import com.app.musicassign.business.data.network.NetworkResponseHandler
import com.app.musicassign.business.data.network.safeApiCall
import com.app.musicassign.business.domain.core.DataState
import com.app.musicassign.business.domain.core.ProgressBarState
import com.app.musicassign.business.domain.model.MusicFileInfo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMusicFilesData @Inject constructor(private val musicService: MusicService) {
    fun execute(): Flow<DataState<List<MusicFileInfo>?>> {
        return flow {

            emit(DataState.Loading(ProgressBarState.Loading))

            val result =
                safeApiCall(IO) {
                    musicService.getMusicFiles()
                }
            emit(NetworkResponseHandler(
                response = result
            ).getResult())

            emit(DataState.Loading(ProgressBarState.Idle))
        }
    }
}