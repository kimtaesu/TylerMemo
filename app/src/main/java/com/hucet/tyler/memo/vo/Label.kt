package com.hucet.tyler.memo.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.ColorInt
import com.hucet.tyler.memo.vo.Label.Companion.LABEL_TABLE
import com.hucet.tyler.memo.vo.Memo.Companion.MEMO_ID

@Entity(
        tableName = LABEL_TABLE,
        foreignKeys = [(ForeignKey(
                entity = Memo::class,
                parentColumns = [MEMO_ID],
                childColumns = [MEMO_ID]
        ))]
)
data class Label(
        val label: String,
        @ColumnInfo(name = MEMO_ID)
        val memoId: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LABEL_ID)
    var id: Int = 0

    companion object {
        const val LABEL_TABLE = "colorThemes"
        const val LABEL_ID = "label_id"
    }
}
