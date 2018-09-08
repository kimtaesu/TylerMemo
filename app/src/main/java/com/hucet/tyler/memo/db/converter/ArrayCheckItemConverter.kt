package com.hucet.tyler.memo.db.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hucet.tyler.memo.GsonDeserializer
import com.hucet.tyler.memo.db.model.CheckItem

object ArrayCheckItemConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun arrayCheckItemsToJsonString(checkItems: List<CheckItem>): String = gson.toJson(checkItems)

    @TypeConverter
    @JvmStatic
    fun jsonToArrayCheckItems(json: String): List<CheckItem> = GsonDeserializer.deserialize(gson, json)
}