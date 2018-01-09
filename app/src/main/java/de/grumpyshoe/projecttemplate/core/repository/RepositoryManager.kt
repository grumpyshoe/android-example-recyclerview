package de.grumpyshoe.projecttemplate.core.repository

import com.thepeaklab.onsitereportingapp.core.repository.src.network.error.ErrorUtils
import de.grumpyshoe.projecttemplate.core.dagger.Injector
import de.grumpyshoe.projecttemplate.core.repository.model.Post
import de.grumpyshoe.projecttemplate.core.repository.src.database.DatabaseManager
import de.grumpyshoe.projecttemplate.core.repository.src.network.NetworkManager
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

                if (result.isNotEmpty()) {
                    callback.onResult(result)
                } else {
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

            try {

                // ... request data again
                val posts = networkManager.getPosts()

                // remove old data
                databaseManager.removePosts()

                // 3. return result
                uiThread {
                    callback.onResult(posts.map { it.toPost() })
                }

                // insert data to db
                posts.forEach {

                    databaseManager.insertPost(it.toPost())
                }
            } catch (e: Exception) {
                uiThread {
                    callback.onError(ErrorUtils.parse(e), e)
                }
            }

        }

    }

}
