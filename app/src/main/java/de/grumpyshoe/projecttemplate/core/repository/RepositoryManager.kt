package de.grumpyshoe.projecttemplate.core.repository

import de.grumpyshoe.projecttemplate.core.dagger.Injector
import de.grumpyshoe.projecttemplate.core.repository.model.Post
import de.grumpyshoe.projecttemplate.core.repository.src.database.DatabaseManager
import de.grumpyshoe.projecttemplate.core.repository.src.network.NetworkManager
import de.grumpyshoe.projecttemplate.core.repository.src.network.dto.PostDto
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject
import de.grumpyshoe.projecttemplate.core.repository.Callback as RepoCallback

/**
 * Created by grumpyshoe on 13.11.17.
 *
 * RepositoryManager contains all logic for getting data from a webservice
 * and storing it to a local database.
 */
class RepositoryManager {

    @Inject protected lateinit var databaseManager: DatabaseManager
    @Inject protected lateinit var networkManager: NetworkManager


    /**
     * inject dependencies
     *
     */
    init {
        Injector.INSTANCE.get().inject(this)
    }


    /**
     * get all posts
     *
     *  - check if db data should be deleted (forceDbRefresh = true)
     *    - request data from webservice
     *      - on success
     *        - remove old database data...
     *        - ...trigger ui callback with new data...
     *        - ...and insert data to database
     *      - on error
     *        - trigger ui callback with error
     *
     *  - return data from db (forceDbRefresh = false)
     *
     */
    fun getPosts(forceDbRefresh: Boolean, callback: RepoCallback<List<Post>>): Unit {

        doAsync {

            // if db content should be deleted ...
            if (forceDbRefresh) {

                postFromRemote(callback)

            } else {
                val result = databaseManager.getAllPosts()
                        .map { it.toPost() }

                if(result.isNotEmpty()){
                    callback.onResult(result)
                }
                else {
                    postFromRemote(callback)
                }
            }
        }
    }


    /**
     * get posts from remote
     *
     */
    private fun postFromRemote(callback: RepoCallback<List<Post>>) {

        doAsync {

            // ... request data again
            networkManager.getPosts(object : RepoCallback<List<PostDto>> {

                override fun onResult(result: List<PostDto>) {

                    doAsync {

                        // remove old data
                        databaseManager.removePosts()

                        // 3. return result
                        uiThread {
                            callback.onResult(result.map { it.toPost() })
                        }

                        // insert data to db
                        result.forEach {

                            databaseManager.insertPost(it.toPost())
                        }
                    }

                }

                override fun onError(throwable: Throwable?, code: Int, errorBody: ResponseBody?) {
                    uiThread {
                        callback.onError(throwable, code, errorBody)
                    }
                }

            })
        }

    }

}
