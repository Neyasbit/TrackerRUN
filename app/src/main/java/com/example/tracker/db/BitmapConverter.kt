package com.example.tracker.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class BitmapConverter {
    @TypeConverter
    fun toBitmap(bytes: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
    @TypeConverter
    fun fromBitmap(img: Bitmap) : ByteArray {
        val outPutStream = ByteArrayOutputStream()
        img.compress(Bitmap.CompressFormat.PNG, 100, outPutStream)
        return outPutStream.toByteArray()
    }
}