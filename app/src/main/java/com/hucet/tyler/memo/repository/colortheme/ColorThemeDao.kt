package com.hucet.tyler.memo.repository.colortheme

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.ColorTheme
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.repository.BaseDao

@Dao
@OpenForTesting
abstract class ColorThemeDao : BaseDao<ColorTheme> {
    @Query("""
        select *
            from color_themes
        """
    )
    abstract fun getAllColorThemes(): LiveData<List<ColorTheme>>

    @Query("""
        select *
            from color_themes
          INNER JOIN memos
          ON memos.memo_id = :memoId
        """
    )
    abstract fun getColorThemeByMemoId(memoId: Long): LiveData<ColorTheme>
}
