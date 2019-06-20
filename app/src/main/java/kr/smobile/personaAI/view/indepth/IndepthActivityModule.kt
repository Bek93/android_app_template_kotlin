package kr.smobile.personaAI.view.indepth

import dagger.Module
import dagger.Provides
import kr.smobile.personaAI.data.MainDataManager
import kr.smobile.personaAI.rx.SchedulerProvider
import kr.smobile.personaAI.view.adapter.UserListAdapter

@Module
class IndepthActivityModule {


    @Provides
    fun providesIndepthActivityModule(
        mainDataManager: MainDataManager,
        schedulerProvider: SchedulerProvider
    ): IndepthViewModel {
        return IndepthViewModel(mainDataManager, schedulerProvider)
    }

    @Provides
    fun providesUserListAdapter(): UserListAdapter {
        return UserListAdapter()
    }
}