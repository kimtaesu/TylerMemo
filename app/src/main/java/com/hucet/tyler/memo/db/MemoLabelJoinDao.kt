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

package com.hucet.tyler.memo.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.utils.SqlQuery
import com.hucet.tyler.memo.vo.CheckableLabelView
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.db.model.Memo
import com.hucet.tyler.memo.db.model.MemoLabelJoin

/**
 * Interface for database access on Repo related operations.
 */

@Dao
@OpenForTesting
abstract class MemoLabelJoinDao : BaseDao<MemoLabelJoin> {
    @Transaction
    @Query("""
        select label_id, label,
        (
           SELECT count(memo_id)
             FROM memos
            WHERE memo_id = :memoId
            limit 1
       ) as isChecked
            from labels
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

}
