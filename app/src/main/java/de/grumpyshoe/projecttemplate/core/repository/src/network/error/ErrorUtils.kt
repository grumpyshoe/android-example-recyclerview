package com.thepeaklab.onsitereportingapp.core.repository.src.network.error

/**
 * Created by thomas on 13.12.17.
 */
class ErrorUtils {

    companion object {

        fun parse(e: Exception) : ErrorType{

            return ErrorType.UNKOWN

        }

    }

}