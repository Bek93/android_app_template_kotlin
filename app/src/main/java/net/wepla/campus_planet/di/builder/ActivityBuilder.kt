package net.wepla.campus_planet.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.wepla.campus_planet.SplashActivity

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity

}