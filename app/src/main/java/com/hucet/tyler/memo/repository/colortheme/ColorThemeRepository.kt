package com.hucet.tyler.memo.repository.colortheme

import android.arch.lifecycle.LiveData
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.repository.label.LabelDao
import com.hucet.tyler.memo.repository.label.LabelRepository
import javax.inject.Inject
import javax.inject.Singleton


interface ColorThemeRepository {
    fun getAllColorThemes(): LiveData<List<ColorTheme>>
    fun insertColorTheme(colorTheme: ColorTheme): Long?
    fun insertColorThemes(colorThemes: List<ColorTheme>): List<Long>
    fun getColorThemeByMemoId(memoId: Long): LiveData<ColorTheme>

    @Singleton
    @OpenForTesting
    class ColorThemeRepositoryImpl @Inject constructor(private val dao: ColorThemeDao) : ColorThemeRepository {
        override fun getColorThemeByMemoId(memoId: Long) = dao.getColorThemeByMemoId(memoId)

        override fun getAllColorThemes(): LiveData<List<ColorTheme>> = dao.getAllColorThemes()

        override fun insertColorThemes(colorThemes: List<ColorTheme>): List<Long> = dao.insert(colorThemes)

        override fun insertColorTheme(colorTheme: ColorTheme): Long? = dao.insert(colorTheme).lastOrNull()
    }
}