package com.hucet.tyler.memo.repository.memo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.TypeConverters
import com.hucet.tyler.memo.db.model.Memo

internal data class MemoViewDto(
        @Embedded
        var memo: Memo = Memo.empty(),

        @TypeConverters()
        var labelIds: List<String>? = null
)

