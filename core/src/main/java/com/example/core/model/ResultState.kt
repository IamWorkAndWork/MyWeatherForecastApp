package com.example.core.model

sealed class ResultState<out R> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}

inline fun <T, R> ResultState<T>.map(crossinline transform: (input: T) -> R): ResultState<R> =
    when (this) {
        is ResultState.Success -> ResultState.Success(transform(this.data))
        is ResultState.Error -> ResultState.Error(this.exception)
        is ResultState.Loading -> this
    }
