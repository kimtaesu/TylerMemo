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
import android.graphics.Color
import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.dto.MemoView
import com.hucet.tyler.memo.vo.Label
import com.hucet.tyler.memo.vo.Memo
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

/**
 * Interface for database access on Repo related operations.
 */
@Dao
@OpenForTesting
abstract class LabelDao : BaseDao<Label> {
    @Query("select * from labels order by label asc")
    abstract fun all(): LiveData<List<Label>>

    @Transaction
    @Query("select * from labels where label LIKE  :keyword order by label asc")
    abstract fun searchLabels(keyword: String): LiveData<List<Label>>
}
