package com.hucet.tyler.memo.repository.memo

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Ignore
import com.hucet.tyler.memo.db.model.Memo

internal data class MemoViewDto(
        @Embedded
        var memo: Memo = Memo.empty(),
        var labelIds: String?
) {
    @Ignore
    val listLabelIds = labelIds?.split(",")?.map {
        try {
            it.toLong()
        } catch (e: NumberFormatException) {
            null
        }
    }?.filterNotNull()
}

