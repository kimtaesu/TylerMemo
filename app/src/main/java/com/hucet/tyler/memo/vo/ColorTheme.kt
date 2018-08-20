package com.hucet.tyler.memo.vo

import android.graphics.Color
import android.support.annotation.ColorInt

data class ColorTheme(
        val label: String,
        @ColorInt val color: Int
) {

    companion object {
        val default = Theme.WHITE

        enum class Theme(val colorTheme: ColorTheme) {
            WHITE(ColorTheme("White", Color.WHITE)),
            RED(ColorTheme("RED", Color.parseColor("#F44336"))),
            PINK(ColorTheme("PINK", Color.parseColor("#E91E63"))),
            PURPLE(ColorTheme("PURPLE", Color.parseColor("#673AB7"))),
            INDIGO(ColorTheme("INDIGO", Color.parseColor("#3F51B5"))),
            BLUE(ColorTheme("BLUE", Color.parseColor("#2196F3"))),
            CYAN(ColorTheme("CYAN", Color.parseColor("#00BCD4"))),
            TEAL(ColorTheme("TEAL", Color.parseColor("#009688"))),
            GREEN(ColorTheme("GREEN", Color.parseColor("#4CAF50"))),
            LIME(ColorTheme("LIME", Color.parseColor("#CDDC39"))),
            YELLOW(ColorTheme("YELLOW", Color.parseColor("#FFEB3B"))),
            BROWN(ColorTheme("BROWN", Color.parseColor("#795548"))),
            GRAY(ColorTheme("GRAY", Color.parseColor("#9E9E9E")))
        }

        fun generate(): List<ColorTheme> {
            return Theme.values().map { it.colorTheme }
        }
    }
}
