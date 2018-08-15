package com.hucet.tyler.memo.di

import android.app.Application
import dagger.Provides
import javax.inject.Singleton
import android.arch.persistence.room.Room
import com.hucet.tyler.memo.db.MemoDao
import com.hucet.tyler.memo.db.MemoDb
import dagger.Module


@Module
class RoomModule {
    @Singleton
    @Provides
    internal fun providesRoomDatabase(application: Application): MemoDb {
        return Room
                .databaseBuilder(application, MemoDb::class.java!!, "memo_db")
                .build()
    }

    @Singleton
    @Provides
    internal fun providesMemoDao(dataBase: MemoDb): MemoDao {
        return dataBase.memoDao()
    }
}
