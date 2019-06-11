package net.wepla.campus_planet.data

import android.content.Context
import net.wepla.campus_planet.base.BaseDataManager
import net.wepla.campus_planet.base.PreferencesManager
import net.wepla.campus_planet.data.remote.MainApi
import net.wepla.campus_planet.utils.ApiService
import net.wepla.campus_planet.view.main.data.MainDataManagerImp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainDataManager @Inject
constructor(context: Context, preferencesManager: PreferencesManager) : BaseDataManager(context, preferencesManager),
    MainDataManagerImp {



    private val mainApi: MainApi = ApiService.provideApi(MainApi::class.java, context)



}
