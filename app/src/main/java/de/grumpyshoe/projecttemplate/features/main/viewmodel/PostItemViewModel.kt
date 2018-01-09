package de.grumpyshoe.projecttemplate.features.main.viewmodel

import de.grumpyshoe.projecttemplate.common.recyclerview.ItemViewmodel
import de.grumpyshoe.projecttemplate.core.repository.model.Post

/**
 * Created by thomas on 31.12.17.
 */
class PostItemViewModel : ItemViewmodel<Post>() {

    private var item : Post? = null

    override fun setItem(newItem: Post) {
        this.item = newItem
        notifyChange()
    }

    fun getTitle(): String {
        return if(item == null) {
            ""
        }
        else {
            item!!.title
        }
    }

    fun getBody(): String {
        return if(item == null) {
            ""
        }
        else {
            item!!.body
        }
    }
}