package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.hucet.tyler.memo.db.model.Label.Companion.LABEL_TABLE
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_ID

@Entity(
        tableName = LABEL_TABLE
)
data class Label(
        val label: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LABEL_ID)
    var id: Int = 0

    companion object {
        const val LABEL_TABLE = "labels"
        const val LABEL_ID = "label_id"
    }
}
