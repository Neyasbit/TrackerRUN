package com.example.tracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.tracker.db.RunningDatabase
import com.example.tracker.utls.Constants.RUNNING_DATABASE_NAME
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_FIRST_TIME_TOGGLE
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_NAME
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_WEIGHT
import com.example.tracker.utls.Constants.SHARED_PREF_NAME
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

    @Singleton
    @Provides
    fun providesSharedPreferences(@ApplicationContext app: Context) = app.getSharedPreferences(
        SHARED_PREF_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun providesName(sharedPreferences: SharedPreferences) = sharedPreferences.getString(
        SHARED_PREF_KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun providesWeight(sharedPreferences: SharedPreferences) = sharedPreferences.getFloat(
        SHARED_PREF_KEY_WEIGHT, 80f
    )

    @Singleton
    @Provides
    fun providesFirstTimeToggle(sharedPreferences: SharedPreferences) = sharedPreferences.getBoolean(
        SHARED_PREF_KEY_FIRST_TIME_TOGGLE, true
    )
}