package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_TABLE
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_TEXT_COLUMN
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
        tableName = MEMO_TABLE,
        indices = [Index(value = MEMO_TEXT_COLUMN)]
)
data class Memo(
        var text: String,
        @ColumnInfo(name = ColorTheme.COLOR_THEME_ID)
        var colorThemeId: Long = ColorTheme.defaultId,
        @Embedded
        val attr: MemoAttribute = MemoAttribute(false),
        val createAt: Long = System.currentTimeMillis(),
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = MEMO_ID)
        override var id: Long = 0
) : Parcelable, HasId {
    companion object {
        const val MEMO_TABLE = "memos"
        const val MEMO_ID = "memo_id"
        const val MEMO_TEXT_COLUMN = "text"
        fun empty(): Memo = Memo("")
    }
}

@Parcelize
data class MemoAttribute(val isPin: Boolean) : Parcelable