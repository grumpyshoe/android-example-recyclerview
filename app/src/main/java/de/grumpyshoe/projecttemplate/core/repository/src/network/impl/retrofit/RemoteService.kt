package de.grumpyshoe.projecttemplate.core.repository.src.network.impl.retrofit

import de.grumpyshoe.projecttemplate.core.repository.src.network.NetworkManager
import de.grumpyshoe.projecttemplate.core.repository.src.network.dto.PostDto
import de.grumpyshoe.projecttemplate.core.repository.src.network.impl.retrofit.api.JsonPlaceholderServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import de.grumpyshoe.projecttemplate.core.repository.Callback as RepoCallback

/**
 * Created by grumpyshoe on 14.11.17.
 *
 * NetworkManager using retrofit
 */
class RemoteService : NetworkManager {

    private var jsonPlaceholderApi = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(JsonPlaceholderServiceApi::class.java)


    /**
     * get posts from
     *
     */
    override fun getPosts(callback: RepoCallback<List<PostDto>>) {
        jsonPlaceholderApi.getPosts().enqueue(object : Callback<List<PostDto>> {

            override fun onResponse(call: Call<List<PostDto>>?, response: Response<List<PostDto>>?) {
                checkResponse(response, callback)
            }


            override fun onFailure(call: Call<List<PostDto>>?, t: Throwable?) {
                callback.onError(t)
            }


        })
    }


    /**
     * private method to check for a successfully response
     *
     */
    private fun <C> checkResponse(response: Response<C>?, callback: RepoCallback<C>){
        if (response != null) {
            if (response.isSuccessful && response.body() != null) {
                callback.onResult(response.body())
            } else {
                callback.onError(code = response.code(), errorBody = response.errorBody())
            }
        } else {
            callback.onError(code = 404)
        }
    }
}