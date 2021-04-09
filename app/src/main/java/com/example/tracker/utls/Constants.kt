package com.example.tracker.utls

import android.provider.BaseColumns

object Constants {
    const val RUNNING_DATABASE_NAME = "running_db"
    object RunEntry : BaseColumns {
        const val COLUMN_NAME_ = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
}