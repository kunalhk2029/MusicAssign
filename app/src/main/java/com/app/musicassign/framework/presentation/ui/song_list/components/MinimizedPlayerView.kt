package com.app.musicassign.framework.presentation.ui.song_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.app.musicassign.business.domain.model.MusicFileInfo
import com.app.musicassign.framework.presentation.ui.ActiveMusicInfo
import com.google.android.exoplayer2.Player

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MinimizedPlayerView(
    activeMusicInfo: ActiveMusicInfo, imageLoader: ImageLoader,
    player: Player,
    onClicked:()->Unit
) {

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .clickable {
            onClicked.invoke()
        }
        .background(
            Color(android.graphics.Color.parseColor(activeMusicInfo.activeMusicItem.bg_gradient))
        )
        .fillMaxWidth()) {
        val imagePainter =
            rememberImagePainter(data = "https://cms.samespace.com/assets/${activeMusicInfo.activeMusicItem.cover_pic_id}",
                imageLoader = imageLoader)

        Image(painter = imagePainter, contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .padding(5.dp), contentScale = ContentScale.Crop)


        Spacer(modifier = Modifier.width(15.dp))

        Text(text = activeMusicInfo.activeMusicItem.music_name,
            modifier = Modifier.weight(1f),
            color = Color.White,
            fontSize = 14.sp)

        var playIcon by remember {
            mutableStateOf(Icons.Default.Pause)
        }
        IconButton(onClick = {
            if (player.isPlaying) {
                playIcon = Icons.Default.PlayArrow
                player.pause()
            } else {
                playIcon = Icons.Default.Pause
                player.play()
            }
        }) {
            Icon(imageVector = playIcon, contentDescription = null, tint = Color.White)
        }
    }
}