package com.thepeaklab.onsitereportingapp.core.repository.src.network.error

import okhttp3.ResponseBody

/**
 * Created by thomas on 11.12.17.
 */
class NetworkResponseException(val httpCode : Int,
                               val responseBody: ResponseBody) : Exception(httpCode.toString())