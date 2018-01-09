package de.grumpyshoe.projecttemplate.core.repository

import com.thepeaklab.onsitereportingapp.core.repository.src.network.error.ErrorType

/**
 * Created by grumpyshoe on 14.11.17.
 *
 * Callback used on async network tasks
 */
interface Callback<T> {

    fun onResult(result: T)
    fun onError(errorType: ErrorType = ErrorType.UNKOWN, throwable: Throwable)

}