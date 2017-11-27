package de.grumpyshoe.projecttemplate.core.repository.src.network.dto

import de.grumpyshoe.projecttemplate.core.repository.model.Post

/**
 * Created by grumpyshoe on 23.11.17.
 *
 * Dto representing webservice model
 */
data class PostDto(val userId: Int, val id: Int, val title: String, val body: String) {

    /**
     * create Contributor from ContributorDto
     *
     */
    fun toPost() : Post {
        return Post(userId, id, title, body)
    }

}