package com.hucet.tyler.memo.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Color
import android.os.Parcelable
import android.support.annotation.ColorInt
import com.hucet.tyler.memo.db.model.ColorTheme.Companion.COLOR_THEME_TABLE
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(
        tableName = COLOR_THEME_TABLE
)
data class ColorTheme(
        val colorTitle: String,
        @ColorInt val color: Int,
        @ColorInt val textColor: Int,
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = COLOR_THEME_ID)
        val id: Long = 0
) : Parcelable {

    companion object {
        const val COLOR_THEME_TABLE = "color_themes"
        const val COLOR_THEME_ID = "color_theme_id"
        @JvmStatic
        val default = Theme.WHITE
        @JvmStatic
        val defaultId = 1L

        enum class Theme(val colorTheme: ColorTheme) {
            WHITE(ColorTheme("WHITE", Color.WHITE, Color.BLACK, defaultId)),
            RED(ColorTheme("RED", Color.parseColor("#F44336"), Color.WHITE, 2)),
            PINK(ColorTheme("PINK", Color.parseColor("#E91E63"), Color.WHITE, 3)),
            PURPLE(ColorTheme("PURPLE", Color.parseColor("#673AB7"), Color.WHITE, 4)),
            INDIGO(ColorTheme("INDIGO", Color.parseColor("#3F51B5"), Color.WHITE, 5)),
            BLUE(ColorTheme("BLUE", Color.parseColor("#2196F3"), Color.WHITE, 6)),
            CYAN(ColorTheme("CYAN", Color.parseColor("#00BCD4"), Color.WHITE, 7)),
            TEAL(ColorTheme("TEAL", Color.parseColor("#009688"), Color.WHITE, 8)),
            GREEN(ColorTheme("GREEN", Color.parseColor("#4CAF50"), Color.WHITE, 9)),
            LIME(ColorTheme("LIME", Color.parseColor("#CDDC39"), Color.WHITE, 10)),
            YELLOW(ColorTheme("YELLOW", Color.parseColor("#FFEB3B"), Color.WHITE, 11)),
            BROWN(ColorTheme("BROWN", Color.parseColor("#795548"), Color.WHITE, 12)),
            GRAY(ColorTheme("GRAY", Color.parseColor("#9E9E9E"), Color.WHITE, 13))
        }

        fun initialPopulate(): List<ColorTheme> {
            return Theme.values().map { it.colorTheme }
        }
    }
}
