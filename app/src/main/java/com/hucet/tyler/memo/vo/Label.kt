package com.hucet.tyler.memo.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.ColorInt

@Entity()
data class Label(
        val label: String,
        @ColorInt val color: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
