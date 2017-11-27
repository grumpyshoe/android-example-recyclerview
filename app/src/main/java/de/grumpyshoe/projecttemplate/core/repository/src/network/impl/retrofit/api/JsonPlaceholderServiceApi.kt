package de.grumpyshoe.projecttemplate.core.repository.src.network.impl.retrofit.api

import de.grumpyshoe.projecttemplate.core.repository.src.network.dto.PostDto
import retrofit2.Call
import retrofit2.http.GET



/**
 * Created by grumpyshoe on 14.11.17.
 *
 * Retrofit Github API
 */
interface JsonPlaceholderServiceApi {

    /**
     * get all contributors
     *
     */
    @GET("posts")
    fun getPosts(): Call<List<PostDto>>
}