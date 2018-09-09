package com.hucet.tyler.memo.db.model

import android.graphics.Color
import android.os.Parcelable
import android.support.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ColorTheme(
        val colorTitle: String,
        @ColorInt val color: Int,
        @ColorInt val textColor: Int
) : Parcelable {

    companion object {
        @JvmStatic
        val default = Theme.WHITE

        enum class Theme(val colorTheme: ColorTheme) {
            WHITE(ColorTheme("WHITE", Color.WHITE, Color.BLACK)),
            RED(ColorTheme("RED", Color.parseColor("#F44336"), Color.WHITE)),
            PINK(ColorTheme("PINK", Color.parseColor("#E91E63"), Color.WHITE)),
            PURPLE(ColorTheme("PURPLE", Color.parseColor("#673AB7"), Color.WHITE)),
            INDIGO(ColorTheme("INDIGO", Color.parseColor("#3F51B5"), Color.WHITE)),
            BLUE(ColorTheme("BLUE", Color.parseColor("#2196F3"), Color.WHITE)),
            CYAN(ColorTheme("CYAN", Color.parseColor("#00BCD4"), Color.WHITE)),
            TEAL(ColorTheme("TEAL", Color.parseColor("#009688"), Color.WHITE)),
            GREEN(ColorTheme("GREEN", Color.parseColor("#4CAF50"), Color.WHITE)),
            LIME(ColorTheme("LIME", Color.parseColor("#CDDC39"), Color.WHITE)),
            YELLOW(ColorTheme("YELLOW", Color.parseColor("#FFEB3B"), Color.WHITE)),
            BROWN(ColorTheme("BROWN", Color.parseColor("#795548"), Color.WHITE)),
            GRAY(ColorTheme("GRAY", Color.parseColor("#9E9E9E"), Color.WHITE))
        }

        fun generate(): List<ColorTheme> {
            return Theme.values().map { it.colorTheme }
        }
    }
}
