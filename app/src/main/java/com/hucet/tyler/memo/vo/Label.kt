package com.hucet.tyler.memo.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity()
data class Label(
        val label: String,
        val color: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
