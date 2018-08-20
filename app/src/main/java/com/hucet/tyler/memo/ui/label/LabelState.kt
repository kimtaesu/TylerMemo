package com.hucet.tyler.memo.ui.color

import com.hucet.tyler.memo.vo.ColorTheme
import com.hucet.tyler.memo.vo.Label

data class LabelState(
        val labels: List<Label> = emptyList(),
        val isEmpty: Boolean = true
)
