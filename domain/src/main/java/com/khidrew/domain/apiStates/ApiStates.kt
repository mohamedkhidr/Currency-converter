package com.khidrew.domain.apiStates

sealed class ApiStates {

    data object Loading : ApiStates()

    data class Success<T>(val data: T) : ApiStates()

    data class Failure(val error: Throwable) : ApiStates()

}