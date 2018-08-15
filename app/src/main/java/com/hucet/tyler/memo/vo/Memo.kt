package com.hucet.tyler.memo.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity()
data class Memo(
        val subject: String,
        val text: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val UNKNOWN_ID = -1
    }
}