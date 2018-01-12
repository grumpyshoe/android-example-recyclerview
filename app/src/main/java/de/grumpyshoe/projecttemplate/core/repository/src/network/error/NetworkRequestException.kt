package com.thepeaklab.onsitereportingapp.core.repository.src.network.error

/**
 * Created by thomas on 11.12.17.
 */
class NetworkRequestException(val exception : Exception) : Exception(exception)