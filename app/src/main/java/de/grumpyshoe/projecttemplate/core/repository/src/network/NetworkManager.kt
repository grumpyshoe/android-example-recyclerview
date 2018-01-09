package de.grumpyshoe.projecttemplate.core.repository.src.network

import de.grumpyshoe.projecttemplate.core.repository.src.network.dto.PostDto
import de.grumpyshoe.projecttemplate.core.repository.Callback as RepoCallback

/**
 * Created by grumpyshoe on 13.11.17.
 *
 * Interface for NetworkManager
 */
interface NetworkManager {

    fun getPosts(): List<PostDto>

}