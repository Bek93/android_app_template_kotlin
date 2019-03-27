package net.wepla.campus_planet.di.module

import dagger.Module
import dagger.Provides
import net.wepla.campus_planet.BuildConfig
import net.wepla.campus_planet.di.ApiInfo

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

}