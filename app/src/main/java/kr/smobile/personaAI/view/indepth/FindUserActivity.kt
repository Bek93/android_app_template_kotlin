package kr.smobile.personaAI.view.main

import android.os.Bundle
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityFindUserBinding
import javax.inject.Inject


class FindUserActivity : BaseActivity<ActivityFindUserBinding, MainViewModel>(), MainNavigator {

    lateinit var activityFindUserBinding: ActivityFindUserBinding
    @Inject
    lateinit var mainViewModel: MainViewModel


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_find_user
    }

    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }

    override fun hasActionBar(): Boolean {
        return true
    }

    override fun getActionBarTitleId(): Int {
        return R.string.follow_list_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFindUserBinding = getViewDataBinding()

        activityFindUserBinding.followers.isSelected = true
        activityFindUserBinding.followings.isSelected = false
        activityFindUserBinding.followers.setOnClickListener {
            var isSelected = activityFindUserBinding.followers.isSelected
            if (!isSelected) {
                activityFindUserBinding.followers.isSelected = !isSelected
                activityFindUserBinding.followings.isSelected = isSelected
            }
        }
        activityFindUserBinding.followings.setOnClickListener {
            var isSelected = activityFindUserBinding.followings.isSelected
            if (!isSelected) {
                activityFindUserBinding.followings.isSelected = !isSelected
                activityFindUserBinding.followers.isSelected = isSelected
            }
        }

        mainViewModel.getFollowers()


    }
}