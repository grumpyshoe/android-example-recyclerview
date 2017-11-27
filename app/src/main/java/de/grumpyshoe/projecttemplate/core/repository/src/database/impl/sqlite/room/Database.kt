package de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.dao.PostDao
import de.grumpyshoe.projecttemplate.core.repository.src.database.impl.sqlite.room.entities.PostEntity

/**
 * Created by grumpyshoe on 14.11.17.
 *
 * Room database definition
 */
@Database(entities = arrayOf(PostEntity::class), version = 2, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun postDao(): PostDao


}