package com.app.musicassign.framework.presentation.ui.music_player

import android.view.View
import android.widget.ImageView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.app.musicassign.framework.presentation.ui.MusicListPreviewScreenStateEvents
import com.app.musicassign.framework.presentation.ui.MusicListPreviewScreenViewState
import com.app.musicassign.framework.presentation.ui.MusicPlayerState
import com.app.musicassign.framework.presentation.ui.MusicPlayerStateEvents
import com.app.musicassign.framework.presentation.ui.theme.Black
import com.app.musicassign.framework.utils.VibrateExtension
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun MusicPlayerScreen(
    state: MusicPlayerState,
    musicListPreviewState: MusicListPreviewScreenViewState,
    event: (MusicPlayerStateEvents) -> Unit,
    imageLoader: ImageLoader,
    player: Player,
    vibrateExtension: VibrateExtension,
    updateActiveMusicItem : (index:Int) -> Unit,
) {

    var initializingPager by remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier
        .fillMaxSize().background(Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {


        val horizontalPagerState = rememberPagerState {
            state.musicFiles!!.size
        }

        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit, block = {
            coroutineScope.launch {
                horizontalPagerState.scrollToPage(musicListPreviewState.activeMusicInfo!!.activeMusicItemIndex)
                delay(500L)
                initializingPager = false
            }
        })

        LaunchedEffect(key1 = horizontalPagerState.currentPage,
            horizontalPagerState.isScrollInProgress,
            block = {
                if (initializingPager && player.currentMediaItem?.mediaId != musicListPreviewState.activeMusicInfo!!.activeMusicItem.id.toString())
                    player.seekTo(musicListPreviewState.activeMusicInfo.activeMusicItemIndex, 0L)
                if (initializingPager) return@LaunchedEffect
                if (!horizontalPagerState.isScrollInProgress && horizontalPagerState.targetPage != musicListPreviewState.activeMusicInfo?.activeMusicItemIndex) {
                    updateActiveMusicItem.invoke(horizontalPagerState.targetPage)
                    player.playWhenReady = true
                    player.seekTo(horizontalPagerState.targetPage, 0L)
                }
            })

        HorizontalPager(state = horizontalPagerState, modifier = Modifier
            .fillMaxWidth()
            .weight(0.65f)
            .background(brush =
            Brush.verticalGradient(colors = listOf(
                Color(android.graphics.Color.parseColor(musicListPreviewState.activeMusicInfo!!.activeMusicItem.bg_gradient)),
                Color(android.graphics.Color.parseColor(musicListPreviewState.activeMusicInfo.activeMusicItem.bg_gradient.replace(
                    "#",
                    "#55")))
            ))), pageSpacing = 25.dp
        ) { index ->
            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                val imagePainter =
                    rememberImagePainter(data = "https://cms.samespace.com/assets/${state.musicFiles!![index].cover_pic_id}",
                        imageLoader = imageLoader)

                Image(painter = imagePainter, contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(250.dp), contentScale = ContentScale.Crop)

                Spacer(modifier = Modifier.height(15.dp))

                Text(modifier = Modifier,
                    text = state.musicFiles[index].music_name,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold)

                Text(text = state.musicFiles[index].music_artist,
                    fontSize = 17.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light)
            }
        }


        AndroidView(factory = { context ->
            View.inflate(context, com.app.musicassign.R.layout.player_view, null)
        }, modifier = Modifier
            .fillMaxWidth()
            .weight(0.35f), update = {
            val playerView =
                it.findViewById<StyledPlayerControlView>(com.app.musicassign.R.id.styledPlayerControlView)

            playerView.findViewById<ImageView>(com.app.musicassign.R.id.exo_next)
                .setOnClickListener {
                    if (player.hasNextMediaItem()) {
                        coroutineScope.launch {
                            player.playWhenReady = true
                            vibrateExtension.vibrate()
                            horizontalPagerState.scrollToPage(musicListPreviewState.activeMusicInfo!!.activeMusicItemIndex + 1)
                            player.seekToNextMediaItem()
                        }
                    }
                }
            playerView.findViewById<ImageView>(com.app.musicassign.R.id.exo_prev)
                .setOnClickListener {
                    if (player.hasPreviousMediaItem()) {
                        coroutineScope.launch {
                            player.playWhenReady = true
                            vibrateExtension.vibrate()
                            horizontalPagerState.scrollToPage(musicListPreviewState.activeMusicInfo!!.activeMusicItemIndex - 1)
                            player.seekToPreviousMediaItem()
                        }
                    }
                }
            playerView.player = player
        })
    }
}