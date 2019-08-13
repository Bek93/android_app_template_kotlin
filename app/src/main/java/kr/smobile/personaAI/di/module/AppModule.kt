package kr.smobile.personaAI.di.module

import dagger.Module
import dagger.Provides
<<<<<<< HEAD:app/src/main/java/kr/smobile/personaAI/di/module/AppModule.kt
import kr.smobile.personaAI.BuildConfig
import kr.smobile.personaAI.base.BaseDataManager
import kr.smobile.personaAI.base.BaseDataManagerImp
import kr.smobile.personaAI.base.PreferencesHelper
import kr.smobile.personaAI.base.PreferencesManager
import kr.smobile.personaAI.di.ApiInfo
import kr.smobile.personaAI.di.PreferenceInfo
import kr.smobile.personaAI.rx.AppSchedulerProvider
import kr.smobile.personaAI.rx.SchedulerProvider
import javax.inject.Singleton
=======
import net.wepla.campus_planet.BuildConfig
import net.wepla.campus_planet.di.ApiInfo
>>>>>>> parent of 78c8240... Updated template:app/src/main/java/net/wepla/campus_planet/di/module/AppModule.kt

/**
 * @author Created by Bek on 25/03/2019
 * */
@Module
class AppModule {


    @Provides
    @ApiInfo
    fun provideApiKey(): String {
        return kr.smobile.personaAI.BuildConfig.API_KEY
    }

}