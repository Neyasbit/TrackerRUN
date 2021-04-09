package com.example.tracker.di

import android.content.Context
import androidx.room.Room
import com.example.tracker.db.RunningDatabase
import com.example.tracker.utls.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesRunningDatabase(
        @ApplicationContext app: Context) : RunningDatabase {
        return Room.databaseBuilder(
            app,
            RunningDatabase::class.java,
            RUNNING_DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesRunDAO(database: RunningDatabase) = database.getDAO()
}