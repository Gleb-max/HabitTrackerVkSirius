package com.habit.tracker.di

import com.google.gson.GsonBuilder
import com.habit.tracker.data.repository.RequestRepositoryImpl
import com.habit.tracker.data.source.remote.api.RequestsApi
import com.habit.tracker.data.source.remote.interceptor.AuthHeaderInterceptor
import com.habit.tracker.domain.repository.RequestRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://backend"

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindRequestRepository(impl: RequestRepositoryImpl): RequestRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideAuthHeaderInterceptor(): AuthHeaderInterceptor {
            return AuthHeaderInterceptor()
        }

        @Provides
        @ApplicationScope
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        }

        @Provides
        @ApplicationScope
        fun provideGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create(GsonBuilder().apply {
                setLenient()
            }.create())
        }

        @Provides
        @ApplicationScope
        fun provideOkHttpClient(
            authHeaderInterceptor: AuthHeaderInterceptor,
            httpLoggingInterceptor: HttpLoggingInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder().apply {
                readTimeout(30L, TimeUnit.SECONDS)
                connectTimeout(60L, TimeUnit.SECONDS)
                writeTimeout(120L, TimeUnit.SECONDS)
                retryOnConnectionFailure(true)
                with(interceptors()) {
                    addInterceptor(authHeaderInterceptor)
                    addInterceptor(httpLoggingInterceptor)
                }
            }.build()
        }

        @Provides
        @ApplicationScope
        fun provideRetrofit(
            client: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .build()
        }

        @Provides
        @ApplicationScope
        fun provideRequestApi(retrofit: Retrofit): RequestsApi {
            return retrofit.create(RequestsApi::class.java)
        }
    }
}