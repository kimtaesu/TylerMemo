package com.hucet.tyler.memo.vo

import android.graphics.Color
import android.support.annotation.ColorInt

data class ColorTheme(
        val label: String,
        @ColorInt val color: Int
) {
    companion object {
        val default = ColorTheme("White", Color.WHITE)

        fun generate(): List<ColorTheme> {
            return listOf(
                    ColorTheme("RED", Color.parseColor("#F44336")),
                    ColorTheme("PINK", Color.parseColor("#E91E63")),
                    ColorTheme("PURPLE", Color.parseColor("#673AB7")),
                    ColorTheme("INDIGO", Color.parseColor("#3F51B5")),
                    ColorTheme("BLUE", Color.parseColor("#2196F3")),
                    ColorTheme("CYAN", Color.parseColor("#00BCD4")),
                    ColorTheme("TEAL", Color.parseColor("#009688")),
                    ColorTheme("GREEN", Color.parseColor("#4CAF50")),
                    ColorTheme("LIME", Color.parseColor("#CDDC39")),
                    ColorTheme("YELLOW", Color.parseColor("#FFEB3B")),
                    ColorTheme("BROWN", Color.parseColor("#795548")),
                    ColorTheme("GRAY", Color.parseColor("#9E9E9E"))
            )

        }
    }
}
