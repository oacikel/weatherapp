package com.oacikel.baseapp.api

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    INVISIBLE_SUCCESS,
    ERROR,
    NULL,
    INVISIBLE_ERROR,
    ONLY_MESSAGE_ERROR,
    LOADING,
    OFFLINE

}