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
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.hucet.tyler.memo.OpenForTesting
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
    @Query("select * from label")
    abstract fun all(): LiveData<List<Label>>

    @Query("DELETE FROM label")
    abstract fun deleteAll()

    companion object {
        fun populate(): List<Label> = listOf(Label("1", "2"))
    }
}