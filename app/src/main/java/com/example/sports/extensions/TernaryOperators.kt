package com.example.sports.extensions

inline infix fun <T> Boolean.True(trueOutput: T) = TernaryPart(this, trueOutput)

inline infix fun <T> TernaryPart<T>.False(falseOutput: T) = if (condition) trueOutput else falseOutput

class TernaryPart<T>(val condition: Boolean, val trueOutput: T)

infix fun <T> Boolean.True(trueBranch: () -> T) = TernaryPart(this, trueBranch)

inline infix fun <T, U: () -> T> TernaryPart<U>.False(falseBranch: U) = if (condition) trueOutput() else falseBranch()
