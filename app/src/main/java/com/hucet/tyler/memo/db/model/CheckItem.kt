package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.hucet.tyler.memo.db.model.CheckItem.Companion.CHECK_ITEM_TABLE
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_ID

@Entity(
        tableName = CHECK_ITEM_TABLE,
        foreignKeys = [(ForeignKey(
                entity = Memo::class,
                parentColumns = [(MEMO_ID)],
                childColumns = [(MEMO_ID)]
        ))]
)
data class CheckItem(
        val name: String,
        val done: Boolean,
        @ColumnInfo(name = Memo.MEMO_ID)
        val memoId: Long,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = CHECK_ITEM_ID)
        override var id: Long = 0
) : HasId {
    companion object {
        const val CHECK_ITEM_TABLE = "check_items"
        const val CHECK_ITEM_ID = "check_item_id"
    }
}
