package com.hucet.tyler.memo.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.hucet.tyler.memo.vo.Memo.Companion.MEMO_TABLE

@Entity(
        tableName = MEMO_TABLE
)
data class Memo(
        val subject: String,
        val text: String,
        @Embedded
        val colorTheme: ColorTheme = ColorTheme.default
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MEMO_ID)
    var id: Int = 0

    companion object {
        const val MEMO_TABLE = "memos"
        const val MEMO_ID = "memo_id"
    }

}
