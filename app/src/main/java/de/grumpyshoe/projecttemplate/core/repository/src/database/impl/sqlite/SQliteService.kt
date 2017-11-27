package de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite

import android.arch.persistence.room.Room
import android.content.Context
import de.grumpyshoe.projecttemplate.core.dagger.Injector
import de.grumpyshoe.projecttemplate.core.repository.model.Post
import de.grumpyshoe.projecttemplate.core.repository.src.database.DatabaseManager
import de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.Database
import de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.entities.PostEntity
import javax.inject.Inject

/**
 * Created by grumpyshoe on 13.11.17.
 *
 * DatabaseManager for SQlite DB
 */
class SQliteService : DatabaseManager {

    private val database: Database

    @Inject lateinit var context: Context

    init {

        Injector.INSTANCE.get().inject(this)

        database = Room
                .databaseBuilder(context, Database::class.java, "my-todo-db")
                .fallbackToDestructiveMigration()
                .build()
    }

    /**
     * get all posts
     *
     */
    override fun getAllPosts(): List<PostEntity> {
        return  database.postDao().getAllPosts()
    }

    /**
     * remove all posts
     *
     */
    override fun removePosts() {
        database.postDao().deleteAll()
    }


    /**
     * insert new post
     *
     */
    override fun insertPost(post: Post) {
        return database.postDao().insertPost(PostEntity.fromPost(post))
    }

}
