package kr.smobile.personaAI.view.onboarding

import android.os.Bundle
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityRegisterBinding
import javax.inject.Inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding, OnboardingViewModel>() {

    @Inject
    lateinit var onboardingViewModel: OnboardingViewModel
    lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun getViewModel(): OnboardingViewModel {
        return onboardingViewModel
    }

    override fun hasActionBar(): Boolean {
        return true;
    }

    override fun getActionBarTitleId(): Int {
        return R.string.empty
    }


    public override fun onStart() {
        super.onStart()
        onboardingViewModel.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardingViewModel.setNavigator(this)
        activityRegisterBinding = getViewDataBinding()
        activityRegisterBinding.email.addTextChangedListener(onboardingViewModel.getTextWatcherListener(1))
        activityRegisterBinding.password.addTextChangedListener(onboardingViewModel.getTextWatcherListener(2))
        activityRegisterBinding.password.addTextChangedListener(onboardingViewModel.getTextWatcherListener(3))
        activityRegisterBinding.confirmPassword.addTextChangedListener(onboardingViewModel.getTextWatcherListener(4))


        activityRegisterBinding.email.setOnFocusChangeListener { v, hasFocus ->

            if (!hasFocus) {
                onboardingViewModel.checkEmail()
            }

        }
    }
}