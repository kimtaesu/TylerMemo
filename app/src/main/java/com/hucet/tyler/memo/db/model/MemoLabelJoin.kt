package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.hucet.tyler.memo.db.model.MemoLabelJoin.Companion.FOREIGN_LABEL_ID
import com.hucet.tyler.memo.db.model.MemoLabelJoin.Companion.FOREIGN_MEMO_ID
import com.hucet.tyler.memo.db.model.MemoLabelJoin.Companion.MEMO_LABEL_JOIN_TABLE

@Entity(tableName = MEMO_LABEL_JOIN_TABLE,
        primaryKeys = [FOREIGN_MEMO_ID, FOREIGN_LABEL_ID],
        foreignKeys = [
            (ForeignKey(
                    entity = Memo::class,
                    parentColumns = [(Memo.MEMO_ID)],
                    childColumns = [(FOREIGN_MEMO_ID)])),
            (ForeignKey(
                    entity = Label::class,
                    parentColumns = [(Label.LABEL_ID)],
                    childColumns = [(FOREIGN_LABEL_ID)])
                    )])
data class MemoLabelJoin(
        @ColumnInfo(name = FOREIGN_MEMO_ID)
        val memoId: Long,

        @ColumnInfo(name = FOREIGN_LABEL_ID)
        val labelId: Long
) {
    companion object {
        const val MEMO_LABEL_JOIN_TABLE = "memo_label_join"
        const val FOREIGN_MEMO_ID = "memo_id"
        const val FOREIGN_LABEL_ID = "label_id"
    }
}