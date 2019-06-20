package kr.smobile.personaAI.view.main

import dagger.Module
import dagger.Provides
import kr.smobile.personaAI.data.MainDataManager
import kr.smobile.personaAI.rx.SchedulerProvider
import kr.smobile.personaAI.view.adapter.TimelineListAdapter

@Module
class MainActivityModule {


    @Provides
    fun providesMainActivityModule(
        mainDataManager: MainDataManager,
        schedulerProvider: SchedulerProvider
    ): MainViewModel {
        return MainViewModel(mainDataManager, schedulerProvider)
    }

    @Provides
    fun providesTimelineListAdapter(): TimelineListAdapter {
        return TimelineListAdapter()
    }
}