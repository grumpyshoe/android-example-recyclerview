package de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.entities.PostEntity

/**
 * Created by grumpyshoe on 23.11.17.
 */
@Dao
interface PostDao {

    @Query("select * from posts")
    fun getAllPosts(): List<PostEntity>

//    @Query("select * from repositories where id = :id")
//    fun findContributorById(id: Long): ContributorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(postEntity: PostEntity)

//    @Update(onConflict = REPLACE)
//    fun updateContributor(contributor: ContributorEntity)
//
//    @Delete
//    fun deleteTContributor(contributor: ContributorEntity)

    @Query("delete from posts ")
    fun deleteAll()

}