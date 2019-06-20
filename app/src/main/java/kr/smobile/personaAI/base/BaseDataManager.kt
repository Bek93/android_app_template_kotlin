package net.wepla.campus_planet.base

import android.content.Context
import net.wepla.campus_planet.model.AuthResponse
import net.wepla.campus_planet.model.Profile

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseDataManager @Inject
constructor(private val context: Context, private val preferencesManager: PreferencesManager) : BaseDataManagerImp {

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

    override fun getUser(): Profile {
        return preferencesManager.user
    }

    override fun setUser(user: Profile) {
        preferencesManager.setUser(user)
    }

}
