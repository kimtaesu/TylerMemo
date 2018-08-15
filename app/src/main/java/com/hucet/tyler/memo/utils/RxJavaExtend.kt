package com.hucet.tyler.memo.utils

import android.arch.lifecycle.LiveData

inline fun <reified T> LiveData<T>.toObservable() = RxJavaConvert.toObservable(this)

inline fun <reified T> LiveData<T>.toObservableAllowNull(valueIfNull: T) =
        RxJavaConvert.toObservableAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toFlowable() = RxJavaConvert.toFlowable(this)

inline fun <reified T> LiveData<T>.toFlowableAllowNull(valueIfNull: T) =
        RxJavaConvert.toFlowableAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toSingle() = RxJavaConvert.toSingle(this)

inline fun <reified T> LiveData<T>.toSingleAllowNull(valueIfNull: T) =
        RxJavaConvert.toSingleAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toMaybe() = RxJavaConvert.toMaybe(this)

inline fun <reified T> LiveData<T>.toMaybeAllowNull(valueIfNull: T) =
        RxJavaConvert.toMaybeAllowNull(this, valueIfNull)

inline fun <reified T> LiveData<T>.toCompletable() = RxJavaConvert.toCompletable(this)

inline fun <reified T> LiveData<T>.toCompletableAllowNull() = RxJavaConvert.toCompletableAllowNull(this)