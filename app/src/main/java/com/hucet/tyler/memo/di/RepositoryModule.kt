package com.hucet.tyler.memo.di

import com.hucet.tyler.memo.repository.label.LabelRepository
import com.hucet.tyler.memo.repository.memo.MemoRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
public abstract class RepositoryModule {
    @Binds
    @Singleton
    internal abstract fun bindMemoRepository(repository: MemoRepository.MemoRepositoryImpl): MemoRepository

    @Binds
    @Singleton
    internal abstract fun bindLabelRepository(repository: LabelRepository.LabelRepositoryImpl): LabelRepository
}
