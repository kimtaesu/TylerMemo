package com.hucet.tyler.memo.repository.checkitem

import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.CheckItem
import javax.inject.Inject
import javax.inject.Singleton

interface CheckItemRepository {
    fun insertCheckItem(checkItem: CheckItem): Long?
    fun insertCheckItems(checkItems: List<CheckItem>): List<Long>
    fun updateCheckItem(checkItem: CheckItem)

    @Singleton
    @OpenForTesting
    class CheckItemRepositoryImpl @Inject constructor(private val dao: CheckItemDao) : CheckItemRepository {
        override fun updateCheckItem(checkItem: CheckItem) = dao.update(checkItem)
        override fun insertCheckItems(checkItems: List<CheckItem>) = dao.insert(checkItems)
        override fun insertCheckItem(checkItem: CheckItem): Long? = dao.insert(checkItem).first()
    }
}
