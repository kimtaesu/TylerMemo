/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hucet.tyler.memo.repository.memo

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.CheckItem
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoAttribute
import com.hucet.tyler.memo.repository.BaseDao
import com.hucet.tyler.memo.ui.add.EditMemoView

/**
 * Interface for database access on Repo related operations.
 */
@Dao
@OpenForTesting
abstract class MemoDao : BaseDao<Memo> {
    @Transaction
    @Query("""select *
        from memos
        where memo_id = :id
        """)
    abstract fun findMemoById(id: Long): LiveData<EditMemoView>

    @Query("""
        update memos
        set color_theme_id = :colorThemeId
        where memo_id = :memoId
        """)
    abstract fun updateColorTheme(memoId: Long, colorThemeId: Long)

    @Query("""
        update memos
        set text = :text
        where memo_id = :memoId
        """)
    abstract fun updateMemoText(memoId: Long, text: String)

    @Query("""
        update memos
        set text = :isPin
        where memo_id = :memoId
        """)
    abstract fun updateMemoAttr(memoId: Long, isPin: Boolean)

    @Query("""
        update memos
        set text = :text, isPin = :pin, color_theme_id = :colorThemeId
        where memo_id = :id
        """)
    abstract fun updateMemo(id: Long, text: String, colorThemeId: Long?, pin: Boolean)
}
