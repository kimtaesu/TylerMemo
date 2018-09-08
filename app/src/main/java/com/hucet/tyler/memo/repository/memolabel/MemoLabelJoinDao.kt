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

package com.hucet.tyler.memo.repository.memolabel

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import android.arch.persistence.room.TypeConverters
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.converter.LabelIdGroupSplitConverter
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoLabelJoin
import com.hucet.tyler.memo.dto.CheckableLabelView
import com.hucet.tyler.memo.repository.BaseDao
import com.hucet.tyler.memo.repository.memo.MemoViewDto


/**
 * Interface for database access on Repo related operations.
 */

@Dao
@OpenForTesting
abstract class MemoLabelJoinDao : BaseDao<MemoLabelJoin> {
    @Query("""
        select l.label_id, l.label, (
            SELECT count(m.label_id)
            FROM memo_label_join as m
            WHERE m.memo_id = :memoId and l.label_id = m.label_id
            Limit 1
       ) as isChecked
            from labels as l
            where label LIKE  :keyword
        """
    )
    abstract fun searchCheckedLabels(keyword: String, memoId: Long): LiveData<List<CheckableLabelView>>

    @Query("""
        SELECT *
        FROM labels
        INNER JOIN memo_label_join
        ON labels.label_id = memo_label_join.label_id
        WHERE memo_label_join.memo_id = :memoId
    """)
    abstract fun getLabelByMemo(memoId: Long): LiveData<List<Label>>

    @Query("""
        SELECT *
        FROM memos
        INNER JOIN memo_label_join
        ON memos.memo_id = memo_label_join.memo_id
        WHERE memo_label_join.label_id = :labelId
    """)
    abstract fun getMemoByLabel(labelId: Long): LiveData<List<Memo>>

    @Query("delete from memo_label_join where memo_id = :memoId and label_id = :labelId")
    abstract fun deleteById(memoId: Long, labelId: Long)

    @Transaction
    @TypeConverters(LabelIdGroupSplitConverter::class)
    @Query("""
        SELECT *, (SELECT GROUP_CONCAT(labels.label_id)
        FROM memo_label_join
        LEFT JOIN labels
        ON labels.label_id = memo_label_join.label_id
        WHERE memo_label_join.memo_id = memos.memo_id) as labelIds
          FROM memos
        where memos.text LIKE  :keyword
    """)
    internal abstract fun searchMemoView(keyword: String): LiveData<List<MemoViewDto>>
}

