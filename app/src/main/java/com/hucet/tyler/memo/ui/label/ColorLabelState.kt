package com.hucet.tyler.memo.ui.label

import com.hucet.tyler.memo.vo.Label

data class ColorLabelState(
        var labels: List<Label> = emptyList(),
        val isNeedUpdate: Boolean = false
)
