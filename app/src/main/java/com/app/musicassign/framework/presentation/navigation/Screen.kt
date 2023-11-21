package com.app.musicassign.framework.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route:String,val args:List<NamedNavArgument>){

    object MusicListPreview:Screen(route = "music_list_preview",listOf())

    object MusicPlayer:Screen(route = "music_player",
        listOf(navArgument(name = "music_index"){
            type= NavType.IntType
        })
    )
}
