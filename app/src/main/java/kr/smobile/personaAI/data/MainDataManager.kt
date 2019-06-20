package kr.smobile.personaAI.data

import android.content.Context
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kr.smobile.personaAI.base.BaseDataManager
import kr.smobile.personaAI.base.PreferencesManager
import kr.smobile.personaAI.data.remote.MainApi
import kr.smobile.personaAI.model.User
import kr.smobile.personaAI.utils.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDataManager @Inject
constructor(context: Context, preferencesManager: PreferencesManager) : BaseDataManager(context, preferencesManager),
    MainDataManagerImp {
    override fun getUserInfo(userId: String?): User {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private val mainApi: MainApi = ApiService.provideApi(MainApi::class.java, context)



}
