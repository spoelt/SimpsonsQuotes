package com.spoelt.simpsonsquotes.data.states

import com.spoelt.simpsonsquotes.data.model.Quote

sealed class NetworkState {
    data class Success(val data: ArrayList<Quote>) : NetworkState()
    data class Error(val error: String) : NetworkState()
    data class NetworkException(val error: String) : NetworkState()

    sealed class HttpErrors : NetworkState() {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}