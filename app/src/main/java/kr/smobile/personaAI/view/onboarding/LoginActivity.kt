package kr.smobile.personaAI.view.onboarding

import android.content.Intent
import android.os.Bundle
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity :
    BaseActivity<ActivityLoginBinding, OnboardingViewModel>() {


    lateinit var activityLoginBinding: ActivityLoginBinding
    @Inject
    lateinit var onboardingViewModel: OnboardingViewModel

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): OnboardingViewModel {
        return onboardingViewModel
    }


    override fun getActionBarTitleId(): Int {
        return R.string.empty
    }

    override fun hasActionBar(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onboardingViewModel.setNavigator(this)
        activityLoginBinding = getViewDataBinding()
        activityLoginBinding.moveToRegister.setOnClickListener {
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        activityLoginBinding.email.addTextChangedListener(onboardingViewModel.getTextWatcherListener(1))
        activityLoginBinding.password.addTextChangedListener(onboardingViewModel.getTextWatcherListener(3))
        activityLoginBinding.forgotPassword.setOnClickListener {
            var intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

}