package kr.smobile.personaAI.view.indepth

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityUserProfileBinding
import javax.inject.Inject


class UserProfileActivity : BaseActivity<ActivityUserProfileBinding, IndepthViewModel>(), IndepthNavigator {

    lateinit var activityUserProfileBinding: ActivityUserProfileBinding
    @Inject
    lateinit var indepthViewModel: IndepthViewModel

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_user_profile
    }

    override fun hasActionBar(): Boolean {
        return true
    }

    override fun getActionBarTitleId(): Int {
        return R.string.empty
    }

    override fun getViewModel(): IndepthViewModel {
        return indepthViewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indepthViewModel.setNavigator(this)
        activityUserProfileBinding = getViewDataBinding()
        var colorActionbar = intent.getIntExtra("color", R.color.colorPrimary)
        var userId = intent.getStringExtra("userId")
        when (colorActionbar) {
            1 -> {
                activityUserProfileBinding.actionBar.setBackgroundColor(resources.getColor(R.color.chatting_bg))
            }
            2 -> {
                activityUserProfileBinding.actionBar.setBackgroundColor(resources.getColor(R.color.timeline_bg))
            }
            3 -> {
                activityUserProfileBinding.actionBar.setBackgroundColor(resources.getColor(R.color.library_bg))
            }
            4 -> {
                activityUserProfileBinding.actionBar.setBackgroundColor(resources.getColor(R.color.myprofile_bg))
            }
        }
        activityUserProfileBinding.menu.setOnClickListener {
            showPopup(it)
        }
        indepthViewModel.getUserInfo(userId)
        indepthViewModel.userMutableLiveData.observe(this, Observer {
            activityUserProfileBinding.user = it
            activityUserProfileBinding.executePendingBindings()
            setActionBarTitle(it.name!!)
        })

        indepthViewModel.followingObservableBoolean.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                activityUserProfileBinding.followButton.isSelected = indepthViewModel.followingObservableBoolean.get()
            }

        })
    }


    fun showPopup(v: View) {
        val popup = PopupMenu(this, v)
        val inflater = popup.getMenuInflater()
        inflater.inflate(R.menu.friends_menu, popup.getMenu())
        popup.show()
    }

}