package kr.smobile.personaAI.view.main

import android.os.Bundle
import androidx.core.content.ContextCompat
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseActivity
import kr.smobile.personaAI.databinding.ActivityMainBinding
import kr.smobile.personaAI.utils.Tab
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {


    private var chatFragment: ChatFragment? = null
    private var timelineFragment: TimelineFragment? = null
    private var libraryFragment: LibraryFragment? = null
    private var myProfileFragment: MyProfileFragment? = null
    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var activityMainBinding: ActivityMainBinding


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        return mainViewModel
    }


    override fun hasActionBar(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = getViewDataBinding()
        mainViewModel.setNavigator(this)
        activityMainBinding.navView.itemIconTintList = null
        activityMainBinding.navView.setOnNavigationItemSelectedListener(mainViewModel.navigationItemSelectedListener)
        mainViewModel.getBgColorObservable.get().let {
            window.statusBarColor = ContextCompat.getColor(this, it!!)
        }
        onNavigationTabSelected(Tab.CHATTING)
    }

    override fun onNavigationTabSelected(tab: Tab) {

        mainViewModel.getBgColorObservable.get().let {
            window.statusBarColor = ContextCompat.getColor(this, it!!)
        }
        when (tab) {
            Tab.CHATTING -> {
                chatFragment = supportFragmentManager.findFragmentByTag(tab.tag) as ChatFragment?
                if (chatFragment == null) {
                    chatFragment = ChatFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_container, chatFragment!!, tab.tag)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction().show(chatFragment!!).commit()
                }
            }
            Tab.TIMELINE -> {
                timelineFragment = supportFragmentManager.findFragmentByTag(tab.tag) as TimelineFragment?
                if (timelineFragment == null) {
                    timelineFragment = TimelineFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_container, timelineFragment!!, tab.tag)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction().show(timelineFragment!!).commit()
                }
            }
            Tab.LIBRARY -> {
                libraryFragment = supportFragmentManager.findFragmentByTag(tab.tag) as LibraryFragment?
                if (libraryFragment == null) {
                    libraryFragment = LibraryFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_container, libraryFragment!!, tab.tag)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction().show(libraryFragment!!).commit()
                }
            }
            Tab.PROFILE -> {
                myProfileFragment = supportFragmentManager.findFragmentByTag(tab.tag) as MyProfileFragment?
                if (myProfileFragment == null) {
                    myProfileFragment = MyProfileFragment()
                    supportFragmentManager.beginTransaction().add(R.id.frame_container, myProfileFragment!!, tab.tag)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction().show(myProfileFragment!!).commit()
                }
            }
        }
    }

    override fun hideCurrentTab(tab: Tab) {
        try {
            supportFragmentManager
                .beginTransaction()
                .hide(supportFragmentManager.findFragmentByTag(tab.tag)!!)
                .commit()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

}
