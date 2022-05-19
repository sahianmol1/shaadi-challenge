package com.example.shaadichallenge.di

import android.app.Application
import androidx.room.Room
import com.example.shaadichallenge.data.local.ShaadiDao
import com.example.shaadichallenge.data.local.ShaadiDatabase
import com.example.shaadichallenge.data.remote.api.ShaadiApi
import com.example.shaadichallenge.repository.ShaadiRepository
import com.example.shaadichallenge.repository.ShaadiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {
        @Provides
        @Singleton
        fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun shaadiApi(retrofit: Retrofit) = retrofit.create(ShaadiApi::class.java)

        @Provides
        @Singleton
        fun roomDatabase(application: Application): ShaadiDatabase =
            Room
                .databaseBuilder(
                    application.applicationContext,
                    ShaadiDatabase::class.java,
                    "shaadi_dn"
                )
                .build()

        @Provides
        @Singleton
        fun shaadiDao(database: ShaadiDatabase): ShaadiDao = database.shaadiDao()
    }

    @Binds
    abstract fun shaadiRepository(shaadiRepositoryImpl: ShaadiRepositoryImpl): ShaadiRepository
}