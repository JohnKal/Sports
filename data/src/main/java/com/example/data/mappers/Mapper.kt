package com.example.data.mappers

interface Mapper<I, O> {
    fun map(input: I): O
}

inline fun <I, O> mapList(input: List<I>, mapSingle: (I) -> O): List<O> {
    return input.map { mapSingle(it) }
}

inline fun <I, O> mapNullInputList(input: List<I>?, mapSingle: (I) -> O): List<O> {
    return input?.map { mapSingle(it) } ?: emptyList()
}

inline fun <I, O> mapNullOutputList(input: List<I>, mapSingle: (I) -> O): List<O>? {
    return if (input.isEmpty()) null else input.map { mapSingle(it) }
}