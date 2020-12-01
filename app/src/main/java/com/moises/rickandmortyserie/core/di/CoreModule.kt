package com.moises.rickandmortyserie.core.di

import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun providesHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder().addInterceptor(interceptor).addInterceptor {
            val request = it.request()
            val builder = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type","application/json")
                .method(request.method, request.body)
            it.proceed(builder.build())
        }.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(httpClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesDispatcher() : DispatcherProvider = DispatcherProviderImpl()

    companion object {
        const val URL_BASE = "https://rickandmortyapi.com/api/"
    }
}