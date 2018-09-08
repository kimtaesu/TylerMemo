package com.hucet.tyler.memo.db.converter

import android.arch.persistence.room.TypeConverter

object LabelIdGroupSplitConverter {
    @TypeConverter
    @JvmStatic
    fun stringToIds(labelIds: String?): List<Long>? {
        return labelIds?.split(",")?.map {
            try {
                it.toLong()
            } catch (e: NumberFormatException) {
                null
            }
        }?.filterNotNull()
    }
}