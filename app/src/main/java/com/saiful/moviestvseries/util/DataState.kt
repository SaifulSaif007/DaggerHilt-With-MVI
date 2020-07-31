package com.saiful.moviestvseries.util

import java.lang.Exception

sealed class DataState <out R>{

    data class Success<out T>(val data : T) : DataState<T>()

    data class Error(val Exception : Exception) : DataState<Nothing>()

    object Loading : DataState<Nothing>()

    data class EndOfList<out T>(val msg : T) : DataState<Nothing>()

}