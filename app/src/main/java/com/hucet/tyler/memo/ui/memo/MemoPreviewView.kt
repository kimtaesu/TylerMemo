package com.hucet.tyler.memo.ui.memo

import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo

data class MemoPreviewView(val memo: Memo,
                           val displayLabels: List<String>,
                           val checkItemCount: Int
)