package kr.smobile.personaAI.view.indepth

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityFindUserBinding
import kr.smobile.personaAI.view.adapter.UserListAdapter
import javax.inject.Inject


class FindUserActivity : BaseActivity<ActivityFindUserBinding, IndepthViewModel>(),
    IndepthNavigator {

    lateinit var activityFindUserBinding: ActivityFindUserBinding
    @Inject
    lateinit var indepthViewModel: IndepthViewModel
    @Inject
    lateinit var userListAdapter: UserListAdapter


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_find_user
    }

    override fun getViewModel(): IndepthViewModel {
        return indepthViewModel
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
        indepthViewModel.setNavigator(this)
        userListAdapter.TYPE = 1
        activityFindUserBinding.followers.isSelected = true
        activityFindUserBinding.followings.isSelected = false

        indepthViewModel.getFollowers()
        activityFindUserBinding.followers.setOnClickListener {
            var isSelected = activityFindUserBinding.followers.isSelected
            if (!isSelected) {
                activityFindUserBinding.followers.isSelected = !isSelected
                activityFindUserBinding.followings.isSelected = isSelected
                userListAdapter.followerOrfollowing = 0;
                indepthViewModel.getFollowers()

            }
        }
        activityFindUserBinding.followings.setOnClickListener {
            var isSelected = activityFindUserBinding.followings.isSelected
            if (!isSelected) {
                activityFindUserBinding.followings.isSelected = !isSelected
                activityFindUserBinding.followers.isSelected = isSelected
                userListAdapter.followerOrfollowing = 1
                indepthViewModel.getFollowings()
            }
        }


        activityFindUserBinding.followUserList.layoutManager = LinearLayoutManager(this)
        activityFindUserBinding.followUserList.adapter = userListAdapter
        indepthViewModel.followingArrayListLiveData.observe(this, Observer {

            userListAdapter.followUserArrayList.clear()
            if (it != null) {
                userListAdapter.followUserArrayList.addAll(it)
            }
            userListAdapter.notifyDataSetChanged()
        })


    }
}