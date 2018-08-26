package com.hucet.tyler.memo.db

import android.arch.persistence.room.*
import com.hucet.tyler.memo.vo.Memo

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg items: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg obj: T)
}