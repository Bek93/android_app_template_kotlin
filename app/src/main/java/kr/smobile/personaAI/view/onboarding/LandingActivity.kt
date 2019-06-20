package kr.smobile.personaAI.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_landing.*
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityLandingBinding
import kr.smobile.personaAI.view.onboarding.adapter.SlideGuidePagerAdapter
import javax.inject.Inject

class LandingActivity : BaseActivity<ActivityLandingBinding, OnboardingViewModel>() {

    lateinit var activityLandingBinding: ActivityLandingBinding
    @Inject
    lateinit var onboardingViewModel: OnboardingViewModel
    var slideGuidePagerAdapter: SlideGuidePagerAdapter? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_landing
    }

    override fun getViewModel(): OnboardingViewModel {
        return onboardingViewModel
    }

    override fun hasActionBar(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLandingBinding = getViewDataBinding()
        slideGuidePagerAdapter = SlideGuidePagerAdapter(this)
        activityLandingBinding.viewPager.offscreenPageLimit = 3
        activityLandingBinding.viewPager.adapter = slideGuidePagerAdapter
        activityLandingBinding.indicator.setViewPager(viewPager)

        activityLandingBinding.startNow.setOnClickListener {
            var intent = Intent(this@LandingActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        activityLandingBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                activityLandingBinding.startNow.visibility = if (position == 2) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

        })
    }
}