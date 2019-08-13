package kr.smobile.personaAI.base

import android.content.Context
import kr.smobile.personaAI.model.AuthResponse
import kr.smobile.personaAI.model.User

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseDataManager @Inject
constructor(private val context: Context, private val preferencesManager: PreferencesManager) : BaseDataManagerImp {
    override fun getUserId(): String {
        return preferencesManager.getUserId()
    }

    override fun setUserId(userId: String?) {
        return preferencesManager.setUserId(userId)
    }

    override fun getAccessToken(): AuthResponse {
        return preferencesManager.accessToken
    }

    override fun setAccessToken(accessToken: String) {
        preferencesManager.setAccessToken(accessToken)
    }

    override fun isSignedIn(): Boolean? {
        return preferencesManager.isSignedIn
    }

    override fun setSignedIn(signedIn: Boolean) {
        preferencesManager.setSignedIn(signedIn)
    }

    override fun getUser(): User {
        return preferencesManager.user
    }

    override fun setUser(user: User) {
        preferencesManager.setUser(user)
    }

}
