package kr.smobile.personaAI.view.onboarding

import android.os.Bundle
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityForgotPasswordBinding
import javax.inject.Inject

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding, OnboardingViewModel>() {

    @Inject
    lateinit var onboardingViewModel: OnboardingViewModel
    lateinit var activityForgotPasswordBinding: ActivityForgotPasswordBinding

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_forgot_password
    }

    override fun getViewModel(): OnboardingViewModel {
        return onboardingViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardingViewModel.setNavigator(this)
        activityForgotPasswordBinding = getViewDataBinding()
        activityForgotPasswordBinding.email.addTextChangedListener(onboardingViewModel.getTextWatcherListener(1))

    }
}