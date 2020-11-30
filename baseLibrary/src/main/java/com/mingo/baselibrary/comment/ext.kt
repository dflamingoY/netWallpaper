package com.mingo.baselibrary.comment

//遍历获取数据
fun <T> List<T>.loop(function: (item: T) -> Boolean): T? {
    if (size == 0) {
        return null
    }
    forEach {
        if (function(it)) {
            return it
        }
    }
    return null
}