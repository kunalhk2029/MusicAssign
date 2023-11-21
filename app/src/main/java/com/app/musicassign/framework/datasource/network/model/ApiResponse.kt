package com.app.musicassign.framework.datasource.network.model

import com.app.musicassign.business.domain.model.MusicFileInfo
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val data: List<MusicFileDTO>,
)

data class MusicFileDTO(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("artist")
    val artist: String,

    @SerializedName("accent")
    val accent: String,

    @SerializedName("cover")
    val cover_pic_id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("top_track")
    val is_top_track: Boolean,
) {
    fun mapToMusicFileInfo(): MusicFileInfo {
        return MusicFileInfo(
            id = id,
            url = url, music_name =
            name, music_artist = artist, bg_gradient = accent,
            cover_pic_id = cover_pic_id, is_top_track = is_top_track
        )
    }
}
