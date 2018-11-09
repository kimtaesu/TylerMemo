package com.hucet.tyler.memo.repository

import android.arch.persistence.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg items: T): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(items: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg obj: T)
}