package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.hucet.tyler.memo.db.model.Label.Companion.LABEL_TABLE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
        tableName = LABEL_TABLE
)
data class Label(
        val label: String,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = LABEL_ID)
        override var id: Long = 0
) : Parcelable, HasId {


    companion object {
        const val LABEL_TABLE = "labels"
        const val LABEL_ID = "label_id"
    }
}
