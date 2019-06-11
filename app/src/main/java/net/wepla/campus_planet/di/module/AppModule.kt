package net.wepla.campus_planet.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import net.wepla.campus_planet.BuildConfig
import net.wepla.campus_planet.base.BaseDataManager
import net.wepla.campus_planet.base.BaseDataManagerImp
import net.wepla.campus_planet.base.PreferencesHelper
import net.wepla.campus_planet.base.PreferencesManager
import net.wepla.campus_planet.di.ApiInfo
import net.wepla.campus_planet.di.PreferenceInfo
import net.wepla.campus_planet.rx.AppSchedulerProvider
import net.wepla.campus_planet.rx.SchedulerProvider
import javax.inject.Singleton

/**
 * @author Created by Bek on 25/03/2019
 * */
@Module
class AppModule {


    @Provides
    @ApiInfo
    fun provideApiKey(): String {
        return BuildConfig.API_KEY
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