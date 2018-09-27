package com.hucet.tyler.memo.di

import android.app.Application
import dagger.Provides
import javax.inject.Singleton
import com.hucet.tyler.memo.repository.label.LabelDao
import com.hucet.tyler.memo.repository.memo.MemoDao
import com.hucet.tyler.memo.db.MemoDb
import com.hucet.tyler.memo.repository.checkitem.CheckItemDao
import com.hucet.tyler.memo.repository.colortheme.ColorThemeDao
import com.hucet.tyler.memo.repository.memolabel.MemoLabelJoinDao
import dagger.Module


@Module
class RoomModule {
    @Singleton
    @Provides
    internal fun providesRoomDatabase(application: Application): MemoDb {
        return MemoDb.getInstance(application)
    }

    @Singleton
    @Provides
    internal fun providesMemoDao(dataBase: MemoDb): MemoDao {
        return dataBase.memoDao()
    }

    @Singleton
    @Provides
    internal fun providesLabelDao(dataBase: MemoDb): LabelDao {
        return dataBase.labelDao()
    }

    @Singleton
    @Provides
    internal fun providesCheckItemDao(dataBase: MemoDb): CheckItemDao {
        return dataBase.checkItemDao()
    }

    @Singleton
    @Provides
    internal fun providesMemoLabelJoinDao(dataBase: MemoDb): MemoLabelJoinDao {
        return dataBase.memoLabelJoinDao()
    }

    @Singleton
    @Provides
    internal fun providesColorThemeDao(dataBase: MemoDb): ColorThemeDao {
        return dataBase.colorThemeDao()
    }
}
