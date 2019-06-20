package kr.smobile.personaAI.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kr.smobile.personaAI.SplashActivity
import kr.smobile.personaAI.utils.MessageDialogFragment
import kr.smobile.personaAI.view.indepth.*
import kr.smobile.personaAI.view.main.*
import kr.smobile.personaAI.view.onboarding.*

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity


    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    abstract fun bindLandingActivity(): LandingActivity

    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [OnboardingModule::class])
    abstract fun bindForgotPasswordActivity(): ForgotPasswordActivity


    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [IndepthActivityModule::class])
    abstract fun bindFindUserActivity(): FindUserActivity

    @ContributesAndroidInjector(modules = [IndepthActivityModule::class])
    abstract fun bindTimelineIndepthActivity(): TimelineIndepthActivity

    @ContributesAndroidInjector(modules = [IndepthActivityModule::class])
    abstract fun bindPostLikedUserListActivity(): PostLikedUserListActivity

    @ContributesAndroidInjector(modules = [IndepthActivityModule::class])
    abstract fun bindUserProfileActivity(): UserProfileActivity


    //fragment

    @ContributesAndroidInjector
    abstract fun bindDialog(): MessageDialogFragment

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindChatFragment(): ChatFragment

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindTimelineFragment(): TimelineFragment

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindLibraryFragment(): LibraryFragment

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMyProfileFragment(): MyProfileFragment

}