package com.app.musicassign.framework.presentation

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.app.musicassign.business.domain.model.MusicFileInfo
import com.app.musicassign.framework.presentation.navigation.Screen
import com.app.musicassign.framework.presentation.ui.MusicListPreviewScreen
import com.app.musicassign.framework.presentation.ui.MusicListPreviewScreenStateEvents
import com.app.musicassign.framework.presentation.ui.MusicListPreviewScreenViewModel
import com.app.musicassign.framework.presentation.ui.MusicPlayerStateEvents
import com.app.musicassign.framework.presentation.ui.music_player.MusicPlayerScreen
import com.app.musicassign.framework.presentation.ui.music_player.MusicPlayerViewModel
import com.app.musicassign.framework.presentation.ui.theme.MusicAssignTheme
import com.app.musicassign.framework.utils.VibrateExtension
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var vibrateExtension: VibrateExtension

    var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicAssignTheme {

                val navController = rememberNavController()

                val viewModel = hiltViewModel<MusicListPreviewScreenViewModel>()

                NavHost(navController = navController,
                    startDestination = Screen.MusicListPreview.route,
                    builder = {
                        composable(Screen.MusicListPreview.route) { backStack ->
                            window.statusBarColor = android.graphics.Color.parseColor("#000000")
                            MusicListPreviewScreen(state = viewModel.state.value,
                                events = viewModel::onTriggerEvent,
                                imageLoader = imageLoader, vibrateExtension, exoPlayer) {
                                viewModel.onTriggerEvent(
                                    MusicListPreviewScreenStateEvents.UpdateActiveMusicItem(it.activeMusicItem)
                                )
                                navController.navigate(Screen.MusicPlayer.route)
                            }
                        }

                        composable(Screen.MusicPlayer.route) { backStack ->

                            val musicPlayerViewModel = hiltViewModel<MusicPlayerViewModel>()

                            val activeMusic =
                                viewModel.state.value.activeMusicInfo!!.activeMusicItem

                            val selectedPlayList =if (activeMusic.is_top_track)
                                viewModel.state.value.topTrackMusicfiles
                            else
                                viewModel.state.value.forYouMusicfiles


                            if (exoPlayer?.getMediaItemAt(0)?.mediaId  != selectedPlayList[0].id.toString()
                            ) {
                                setupExoPlayerMediaItems(
                                    selectedPlayList,
                                    viewModel.state.value.activeMusicInfo!!.activeMusicItemIndex
                                )
                            }

                            if (musicPlayerViewModel.state.value.musicFiles == null) {
                                musicPlayerViewModel.onEvent(MusicPlayerStateEvents.UpdateAvailableMusicFiles(
                                    selectedPlayList,
                                    viewModel.state.value.activeMusicInfo!!.activeMusicItemIndex
                                ))
                            }

                            window.statusBarColor =
                                android.graphics.Color.parseColor(viewModel.state.value.activeMusicInfo?.activeMusicItem?.bg_gradient)

                            MusicPlayerScreen(
                                musicPlayerViewModel.state.value,
                                viewModel.state.value,
                                musicPlayerViewModel::onEvent,
                                imageLoader = imageLoader, exoPlayer!!, vibrateExtension) {
                                viewModel.onTriggerEvent(
                                    MusicListPreviewScreenStateEvents.UpdateActiveMusicItem(
                                        musicPlayerViewModel.state.value.musicFiles!![it]
                                    )
                                )
                            }
                        }
                    })
            }
        }
    }

    fun getPlayerListener(): Player.Listener = object : Player.Listener {
        override fun onPlaybackStateChanged(state: Int) {
            super.onPlaybackStateChanged(state)
            when (state) {
                Player.STATE_READY -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                Player.STATE_ENDED -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }
    }


    private fun setupExoPlayerMediaItems(musicFiles: List<MusicFileInfo>, index: Int) {
        if (exoPlayer==null) exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.addListener(getPlayerListener())
        val mediaItems: MutableList<MediaItem> = mutableListOf()
        musicFiles.forEach { musicFile ->
            val mediaItem = MediaItem.Builder()
                .setMediaId(musicFile.id.toString())
                .setUri(Uri.parse(musicFile.url))
                .build()
            mediaItems.add(mediaItem)
        }
        exoPlayer?.setMediaItems(mediaItems)
        exoPlayer?.playWhenReady = true
        exoPlayer?.seekTo(index, 0L)
        exoPlayer?.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }
}
