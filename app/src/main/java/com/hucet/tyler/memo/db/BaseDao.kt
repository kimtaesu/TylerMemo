package com.hucet.tyler.memo.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg repos: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(repositories: List<T>)
}