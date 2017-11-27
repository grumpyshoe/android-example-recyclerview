package de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import de.grumpyshoe.projecttemplate.core.repository.model.Post

/**
 * Created by grumpyshoe on 23.11.17.
 */
@Entity(tableName = "posts")
data class PostEntity(val userId: Int,  val id_e: Int, val title: String, val body: String) {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    /**
     * create Contributor from ContributorEntity
     *
     */
    fun toPost(): Post {
        return Post(userId, id_e, title, body)
    }


    /**
     * create DTO from Post
     *
     */
    companion object {
        fun fromPost(post: Post): PostEntity {
            return PostEntity(post.userId, post.id_e, post.title, post.body)
        }
    }

}