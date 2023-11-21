package com.app.musicassign.di

import android.app.Application
import android.content.Context
import coil.ImageLoader
import com.app.musicassign.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoilModule {
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext app: Context): ImageLoader {
        return ImageLoader.Builder(app)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.white_background)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
    }
}