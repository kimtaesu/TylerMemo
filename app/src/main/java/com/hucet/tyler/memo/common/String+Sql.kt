package com.hucet.tyler.memo.common

fun String.fullTextSql(): String {
    return "%${this}%"
}