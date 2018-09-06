package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Bundle
import android.os.Parcelable
import com.hucet.tyler.memo.ArgKeys
import com.hucet.tyler.memo.db.model.Memo.Companion.MEMO_TABLE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
        tableName = MEMO_TABLE
)
data class Memo(
        val text: String,
        @Embedded
        val attr: MemoAttribute = MemoAttribute(false),
        val createAt: Long = System.currentTimeMillis(),
        @Embedded
        var colorTheme: ColorTheme = ColorTheme.default.colorTheme,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = MEMO_ID)
        override var id: Long = 0
) : Parcelable, HasId {
    companion object {
        const val MEMO_TABLE = "memos"
        const val MEMO_ID = "memo_id"
        fun empty(): Memo = Memo("")

    }
}

@Parcelize
data class MemoAttribute(val isPin: Boolean) : Parcelable