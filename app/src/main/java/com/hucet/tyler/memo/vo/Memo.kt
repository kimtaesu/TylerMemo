package com.hucet.tyler.memo.vo

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.hucet.tyler.memo.UNKNOWN_ID
import com.hucet.tyler.memo.vo.Memo.Companion.MEMO_TABLE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
        tableName = MEMO_TABLE
)
data class Memo(
        val text: String,
        @Embedded
        val attr: MemoAttribute = MemoAttribute(false),
        @Embedded
        val colorTheme: ColorTheme = ColorTheme.default.colorTheme,
        val createAt: Long = System.currentTimeMillis()
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MEMO_ID)
    var id: Long = 0

    companion object {
        const val MEMO_TABLE = "memos"
        const val MEMO_ID = "memo_id"
        fun empty(): Memo = Memo("")

    }
}

@Parcelize
data class MemoAttribute(val isPin: Boolean) : Parcelable