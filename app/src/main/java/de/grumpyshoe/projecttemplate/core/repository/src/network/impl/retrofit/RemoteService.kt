package de.grumpyshoe.projecttemplate.core.repository.src.network.impl.retrofit

import android.accounts.NetworkErrorException
import com.thepeaklab.onsitereportingapp.core.repository.src.network.error.NetworkRequestException
import com.thepeaklab.onsitereportingapp.core.repository.src.network.error.NetworkResponseException
import de.grumpyshoe.projecttemplate.core.repository.src.network.NetworkManager
import de.grumpyshoe.projecttemplate.core.repository.src.network.dto.PostDto
import de.grumpyshoe.projecttemplate.core.repository.src.network.impl.retrofit.api.JsonPlaceholderServiceApi
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
    override fun getPosts(): List<PostDto> {

        try {

            var liste = mutableListOf<PostDto>()

            (0..20).forEach {
                liste.add(PostDto(it, it, "ttitle_$it","body_$it"))
            }
            return liste

            val response = jsonPlaceholderApi.getPosts().execute()
            return checkResponse<List<PostDto>>(response)

        } catch (e: Exception) {
            throw NetworkRequestException(e)
        }
    }


    /**
     * private method to check for a successfully response
     *
     */
    private fun <C> checkResponse(response: Response<C>?): C {
        if (response != null) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body()
            } else {
                throw NetworkResponseException(response.code(), response.errorBody())
            }
        } else {
            throw NetworkErrorException()
        }
    }
}