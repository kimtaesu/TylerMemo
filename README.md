# TylerMemo

## Languages, libraries and tools used

* [Kotlin](https://kotlinlang.org/)
* [RxKotlin](https://github.com/ReactiveX/RxKotlin)
* [Databinding](https://developer.android.com/topic/libraries/data-binding/?hl=ko)
* [Jetpack](https://developer.android.com/jetpack/)
* [Dagger2](https://github.com/google/dagger)
* [Retrofit](https://github.com/square/retrofit)
* [Firebase](https://firebase.google.com/docs/android/setup?hl=ko)
* [Mockito](https://github.com/mockito/mockito)
* [Timber](https://github.com/JakeWharton/timber)

## Database
![](document/memo.png)

### Queries
#### CheckableLabelView
![](document/checkedLabel.png)
```kotlin
@Query("""
        select l.label_id, l.label, (
            SELECT count(m.label_id)
            FROM memo_label_join as m
            WHERE m.memo_id = :memoId and l.label_id = m.label_id
            Limit 1
       ) as isChecked
            from labels as l
            where label LIKE  :keyword
        """
    )
    abstract fun searchCheckedLabels(keyword: String, memoId: Long): LiveData<List<CheckableLabelView>>
```