package kr.smobile.personaAI.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
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


    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: BaseDataManager): BaseDataManagerImp {
        return appDataManager
    }


    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return PreferencesManager.PREF_NAME
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(appPreferencesHelper: PreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}