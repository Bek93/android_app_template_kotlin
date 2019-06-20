package kr.smobile.personaAI.view.indepth

import android.graphics.Color
import android.os.Bundle
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityIndepthTimelineBinding
import javax.inject.Inject

class TimelineIndepthActivity : BaseActivity<ActivityIndepthTimelineBinding, IndepthViewModel>(), IndepthNavigator {

    lateinit var activityIndepthTimelineBinding: ActivityIndepthTimelineBinding
    @Inject
    lateinit var indepthViewModel: IndepthViewModel

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_indepth_timeline
    }

    override fun hasActionBar(): Boolean {
        return true
    }

    override fun getActionBarTitleId(): Int {
        return R.string.indepth_title
    }

    override fun getViewModel(): IndepthViewModel {
        return indepthViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indepthViewModel.setNavigator(this)
        activityIndepthTimelineBinding = getViewDataBinding()

    }


}