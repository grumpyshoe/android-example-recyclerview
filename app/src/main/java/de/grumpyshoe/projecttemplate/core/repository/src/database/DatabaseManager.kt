package de.grumpyshoe.projecttemplate.core.repository.src.database

import de.grumpyshoe.projecttemplate.core.repository.model.Post
import de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.entities.PostEntity

/**
 * Created by grumpyshoe on 13.11.17.
 *
 * Interface for DatabaseManager
 */
interface DatabaseManager {

    /**
     * get all posts
     *
     */
    fun getAllPosts() : List<PostEntity>


    /**
     * insert new contributor
     *
     */
    fun insertPost(post: Post)


    /**
     * remove all posts
     *
     */
    fun removePosts()
}