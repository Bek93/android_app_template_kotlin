package kr.smobile.personaAI.view.indepth

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityPostsListLikedByUserBinding
import kr.smobile.personaAI.model.User
import kr.smobile.personaAI.view.adapter.UserListAdapter
import javax.inject.Inject

class PostLikedUserListActivity : BaseActivity<ActivityPostsListLikedByUserBinding, IndepthViewModel>(),
    IndepthNavigator {

    lateinit var activityPostsListLikedByUserBinding: ActivityPostsListLikedByUserBinding
    @Inject
    lateinit var indepthViewModel: IndepthViewModel
    @Inject
    lateinit var userListAdapter: UserListAdapter

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getActionBarTitleId(): Int {
        return R.string.liked_title
    }

    override fun hasActionBar(): Boolean {
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_posts_list_liked_by_user
    }

    override fun getViewModel(): IndepthViewModel {
        return indepthViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indepthViewModel.setNavigator(this)
        activityPostsListLikedByUserBinding = getViewDataBinding()
        userListAdapter.userArrayList.add(User("Ali"))
        userListAdapter.userArrayList.add(User("Vali"))
        userListAdapter.userArrayList.add(User("Mamur"))
        userListAdapter.userArrayList.add(User("Sardor"))
        userListAdapter.userArrayList.add(User("Mansur"))
        activityPostsListLikedByUserBinding.userRecyclerview.layoutManager = LinearLayoutManager(this)
        activityPostsListLikedByUserBinding.userRecyclerview.adapter=userListAdapter
    }
}