package com.hucet.tyler.memo.repository.label

import com.hucet.tyler.memo.OpenForTesting
import com.hucet.tyler.memo.db.model.Label
import javax.inject.Inject
import javax.inject.Singleton


interface LabelRepository {
    fun insertLabel(label: Label): Long?
    fun getLabelById(labelId: Long): Label
    fun insertLabels(labels: List<Label>): List<Long>

    @Singleton
    @OpenForTesting
    class LabelRepositoryImpl @Inject constructor(private val dao: LabelDao) : LabelRepository {
        override fun getLabelById(labelId: Long): Label = dao.getLabelById(labelId)

        override fun insertLabel(label: Label): Long? = dao.insert(label).lastOrNull()

        override fun insertLabels(labels: List<Label>): List<Long> = dao.insert(labels)
    }
}
