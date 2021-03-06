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

package com.hucet.tyler.memo.repository.label

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.Label
import com.hucet.tyler.memo.repository.BaseDao

/**
 * Interface for database access on Repo related operations.
 */

@Dao
@OpenForTesting
abstract class LabelDao : BaseDao<Label> {
    @Query("""
        select *
            from labels
            where label LIKE  :keyword
        """
    )
    abstract fun searchLabels(keyword: String): LiveData<List<Label>>

    @Query("""
        select *
            from labels
            where labels.label_id LIKE  :labelId
            limit 1
        """
    )
    abstract fun getLabelById(labelId: Long): Label

}
