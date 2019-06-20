package kr.smobile.personaAI.data.remote

import kr.smobile.personaAI.model.User


/**
 * Main Tab page Api
 */
interface MainApi {


    fun getUserInfo(userId: String?): User


}
