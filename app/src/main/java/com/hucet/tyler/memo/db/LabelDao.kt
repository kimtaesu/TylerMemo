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
import android.graphics.Color
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
        fun populate(): List<Label> = listOf(
                Label("RED", Color.parseColor("#F44336")),
                Label("PINK", Color.parseColor("#E91E63")),
                Label("PURPLE", Color.parseColor("#673AB7")),
                Label("INDIGO", Color.parseColor("#3F51B5")),
                Label("BLUE", Color.parseColor("#2196F3")),
                Label("CYAN", Color.parseColor("#00BCD4")),
                Label("TEAL", Color.parseColor("#009688")),
                Label("GREEN", Color.parseColor("#4CAF50")),
                Label("LIME", Color.parseColor("#CDDC39")),
                Label("YELLOW", Color.parseColor("#FFEB3B")),
                Label("BROWN", Color.parseColor("#795548")),
                Label("GRAY", Color.parseColor("#9E9E9E"))
        )
    }
}
