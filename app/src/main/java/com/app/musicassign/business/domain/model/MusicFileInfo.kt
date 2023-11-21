package com.app.musicassign.business.domain.model

import java.io.Serializable

data class MusicFileInfo(
    val id: Int,
    val url: String,
    val music_name: String,
    val music_artist: String,
    val bg_gradient: String,
    val cover_pic_id: String,
    val is_top_track: Boolean,
) : Serializable