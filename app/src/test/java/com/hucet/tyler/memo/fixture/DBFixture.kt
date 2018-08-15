package com.hucet.tyler.memo.fixture

import android.arch.persistence.room.Room
import com.hucet.tyler.memo.db.MemoDb
import org.robolectric.RuntimeEnvironment

object DBFixture {
    fun createDatabase(): MemoDb {
        return Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application,
                MemoDb::class.java)
                .allowMainThreadQueries()
                .build()
    }
}