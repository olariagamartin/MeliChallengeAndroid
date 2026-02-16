@file:OptIn(ExperimentalContracts::class)

package com.themarto.core.data.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val error: String) : Result<T>()

}

fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
    contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.AT_MOST_ONCE)
    }
    if (this is Result.Success) {
        block(data)
    }
    return this
}

fun <T> Result<T>.onError(block: (String) -> Unit): Result<T> {
    contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.AT_MOST_ONCE)
    }
    if (this is Result.Error) {
        block(error)
    }
    return this
}