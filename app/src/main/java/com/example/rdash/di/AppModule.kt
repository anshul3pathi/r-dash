package com.example.rdash.di

import android.content.Context
import com.example.rdash.data.api.DesignFilesApi
import com.example.rdash.data.file_downloader.FileDownloader
import com.example.rdash.data.file_downloader.WorkManagerFileDownloader
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule  {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideDesignFilesApi(okHttpClient: OkHttpClient): DesignFilesApi {
        return Retrofit.Builder()
            .baseUrl(DesignFilesApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFileDownloader(@ApplicationContext context: Context): FileDownloader {
        return WorkManagerFileDownloader(context = context)
    }
}