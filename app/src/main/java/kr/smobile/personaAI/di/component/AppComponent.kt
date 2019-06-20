package kr.smobile.personaAI.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import kr.smobile.personaAI.view.CampusApplication
import kr.smobile.personaAI.di.builder.ActivityBuilder
import kr.smobile.personaAI.di.module.AppModule
import javax.inject.Singleton

/**
 * Created by Bek 25/03/2019
 * */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class))
interface AppComponent {

    fun inject(app: CampusApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}