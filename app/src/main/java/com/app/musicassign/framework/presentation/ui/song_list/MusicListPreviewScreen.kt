package com.app.musicassign.framework.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.app.musicassign.business.domain.core.ProgressBarState
import com.app.musicassign.framework.presentation.ui.song_list.components.MinimizedPlayerView
import com.app.musicassign.framework.presentation.ui.song_list.components.MusicItem
import com.app.musicassign.framework.presentation.ui.song_list.components.tabItemsList
import com.app.musicassign.framework.presentation.ui.theme.Black
import com.app.musicassign.framework.utils.VibrateExtension
import com.google.android.exoplayer2.Player

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MusicListPreviewScreen(
    state: MusicListPreviewScreenViewState,
    events: (MusicListPreviewScreenStateEvents) -> Unit,
    imageLoader: ImageLoader,
    vibrateExtension: VibrateExtension,
    player: Player?,
    musicItemClicked: (activeMusicInfo: ActiveMusicInfo) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize().background(Black)) {
        Column(modifier = Modifier.fillMaxWidth()) {

            val horizontalPagerState = rememberPagerState {
                tabItemsList.size
            }

            LaunchedEffect(key1 = state.selectedTabIndex, block = {
                vibrateExtension.vibrate()
                horizontalPagerState.animateScrollToPage(state.selectedTabIndex)
            })

            LaunchedEffect(key1 = horizontalPagerState.currentPage,
                horizontalPagerState.isScrollInProgress,
                block = {
                    if (!horizontalPagerState.isScrollInProgress) {
                        events(MusicListPreviewScreenStateEvents.UpdateSelectedTabIndex(
                            horizontalPagerState.currentPage))
                    }
                })

            HorizontalPager(state = horizontalPagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.Top) { index ->
                if (index == 0) {
                    LazyColumn(modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        items(state.forYouMusicfiles) { musicFile ->
                            MusicItem(imageLoader = imageLoader,
                                musicFileInfo = musicFile,
                                onItemClicked = {
                                    musicItemClicked.invoke(ActiveMusicInfo(it,
                                        it.is_top_track,
                                        state.forYouMusicfiles.indexOf(it)))
                                })
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        items(state.topTrackMusicfiles) { musicFile ->
                            MusicItem(imageLoader = imageLoader,
                                musicFileInfo = musicFile,
                                onItemClicked = {
                                    musicItemClicked.invoke(ActiveMusicInfo(it,
                                        it.is_top_track,
                                        state.topTrackMusicfiles.indexOf(it)))
                                })
                        }
                    }
                }
            }


            if (player?.isPlaying == true) {
                state.activeMusicInfo?.let {
                    MinimizedPlayerView(activeMusicInfo = it,
                        imageLoader = imageLoader,
                        player = player) {
                        musicItemClicked.invoke(state.activeMusicInfo)
                    }
                }
            }

            TabRow(selectedTabIndex = state.selectedTabIndex) {
                tabItemsList.forEachIndexed { index, i ->
                    Tab(selected = state.selectedTabIndex == index, onClick = {
                        events(MusicListPreviewScreenStateEvents.UpdateSelectedTabIndex(index))
                    }, text = { Text(text = tabItemsList[index].title) }, icon = {
                        if (state.selectedTabIndex == index) {
                            Icon(painter = painterResource(id = tabItemsList[index].selectedIcon),
                                contentDescription = null,
                                modifier = Modifier.height(15.dp))
                        }
                    })
                }
            }
        }

        if (state.progressBarState is ProgressBarState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center),
                color = Color.White)
        }
    }
}