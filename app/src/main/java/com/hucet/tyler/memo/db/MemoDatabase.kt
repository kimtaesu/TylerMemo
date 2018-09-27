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


import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.hucet.tyler.memo.db.model.*
import com.hucet.tyler.memo.repository.checkitem.CheckItemDao
import com.hucet.tyler.memo.repository.colortheme.ColorThemeDao
import com.hucet.tyler.memo.repository.label.LabelDao
import com.hucet.tyler.memo.repository.memo.MemoDao
import com.hucet.tyler.memo.repository.memolabel.MemoLabelJoinDao
import java.util.concurrent.Executors


/**
 * Main database description.
 */
@Database(
        entities = [
            Memo::class,
            Label::class,
            MemoLabelJoin::class,
            CheckItem::class,
            ColorTheme::class
        ],
        version = 1,
        exportSchema = false
)
abstract class MemoDb : RoomDatabase() {

    abstract fun memoDao(): MemoDao

    abstract fun labelDao(): LabelDao

    abstract fun checkItemDao(): CheckItemDao

    abstract fun memoLabelJoinDao(): MemoLabelJoinDao

    abstract fun colorThemeDao(): ColorThemeDao

    companion object {

        private var INSTANCE: MemoDb? = null

        @Synchronized
        fun getInstance(context: Context): MemoDb {
            if (INSTANCE == null) {
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        @Synchronized
        fun getInstanceInMemory(context: Context): MemoDb {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext, MemoDb::class.java)
                    .allowMainThreadQueries()
                    .populate(context)
                    .build()
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): MemoDb {
            return Room.databaseBuilder(context,
                    MemoDb::class.java, "memo_db6")
                    .populate(context)
                    .build()
        }
    }
}

private fun <T : RoomDatabase> RoomDatabase.Builder<T>.populate(context: Context): RoomDatabase.Builder<T> {
    return this.addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadScheduledExecutor().execute {
                MemoDb.getInstance(context).colorThemeDao().insert(ColorTheme.initialPopulate())
            }
        }
    })
}
