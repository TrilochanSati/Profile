package com.windapp.profile.di

import android.app.Application
import androidx.room.Room
import com.windapp.profile.data.ProfileDatabase
import com.windapp.profile.data.ProfileRepository
import com.windapp.profile.data.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideProfileDatabase(app:Application):ProfileDatabase{
        return Room.databaseBuilder(
            app,
            ProfileDatabase::class.java,
            "profile_db"
        ).build()

    }
    @Provides
    @Singleton
    fun provideProfileRepository(db:ProfileDatabase):ProfileRepository{
       return ProfileRepositoryImpl(db.dao)
    }
}