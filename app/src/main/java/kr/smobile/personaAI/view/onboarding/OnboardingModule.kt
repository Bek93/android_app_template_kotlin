package kr.smobile.personaAI.view.onboarding

import dagger.Module
import dagger.Provides
import kr.smobile.personaAI.data.MainDataManager
import kr.smobile.personaAI.rx.SchedulerProvider
import kr.smobile.personaAI.view.onboarding.adapter.SlideGuidePagerAdapter

@Module
class OnboardingModule {


    @Provides
    fun provideOnboardingViewModel(
        mainDataManager: MainDataManager,
        schedulerProvider: SchedulerProvider
    ): OnboardingViewModel {
        return OnboardingViewModel(mainDataManager, schedulerProvider)
    }
}