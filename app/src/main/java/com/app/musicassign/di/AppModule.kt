package com.app.musicassign.di

import android.content.Context
import com.app.musicassign.business.data.network.EndPoints.BASE_URL
import com.app.musicassign.business.data.network.MusicService
import com.app.musicassign.business.data.network.MusicServiceImpl
import com.app.musicassign.framework.datasource.network.MusicNetworkService
import com.app.musicassign.framework.datasource.network.MusicNetworkServiceImpl
import com.app.musicassign.framework.datasource.network.RetrofitMusicService
import com.google.android.exoplayer2.ExoPlayer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitNetworkService(): RetrofitMusicService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(RetrofitMusicService::class.java)
    }

    @Provides
    @Singleton
    fun provideFileNetworkService(
        @ApplicationContext context: Context,
        retrofitMusicService: RetrofitMusicService,
    ): MusicNetworkService {
        return MusicNetworkServiceImpl(retrofitMusicService, context)
    }


    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideFileServiceImpl(musicNetworkService: MusicNetworkService): MusicService {
        return MusicServiceImpl(musicNetworkService)
    }
}