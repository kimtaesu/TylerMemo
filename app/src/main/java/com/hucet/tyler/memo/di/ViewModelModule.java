package com.hucet.tyler.memo.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.hucet.tyler.memo.common.ViewModelFactory;
import com.hucet.tyler.memo.ui.add.AddMemoViewModel;
import com.hucet.tyler.memo.ui.label.MakeLabelViewModel;
import com.hucet.tyler.memo.ui.memo.MemoViewModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;
import kotlin.annotation.MustBeDocumented;

@Module
public abstract class ViewModelModule {
    @Binds
    @Singleton
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MemoViewModel.class)
    abstract ViewModel bindMemoViewModel(MemoViewModel memoViewModel);

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(MakeLabelViewModel.class)
    abstract ViewModel bindMakeLabelViewModel(MakeLabelViewModel makeLabelViewModel);

    @Binds
    @IntoMap
    @Singleton
    @ViewModelKey(AddMemoViewModel.class)
    abstract ViewModel bindMakeAddMemoViewModel(AddMemoViewModel makeLabelViewModel);

    @MustBeDocumented
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    public @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

}