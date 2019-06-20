package kr.smobile.personaAI.view.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseViewModel
import kr.smobile.personaAI.data.MainDataManager
import kr.smobile.personaAI.model.User
import kr.smobile.personaAI.rx.SchedulerProvider
import kr.smobile.personaAI.utils.Intented
import kr.smobile.personaAI.utils.Tab
import kr.smobile.personaAI.view.model.Timeline


class MainViewModel(var mainDataManager: MainDataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(schedulerProvider) {

    var database = FirebaseFirestore.getInstance()
    private var currentTab: Tab? = Tab.CHATTING
    var getBgColorObservable = ObservableField<Int>(R.color.chatting_bg)
    var rigthIconObservable = ObservableField<Int>(R.drawable.finduser)
    var addFollowerVisibleObservable = ObservableBoolean(false)
    var threeDotVisibleObservable = ObservableBoolean(false)
    var notifyVisibleObservable = ObservableBoolean(false)
    var addLibraryVisibleObservable = ObservableBoolean(false)
    var addTimelineVisibleObservable = ObservableBoolean(false)
    var timelineListLiveData = MutableLiveData<ArrayList<Timeline>>()


    val navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
        get() = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            getNavigator().hideCurrentTab(currentTab!!)


            var isSigned = mainDataManager.isSignedIn

            getNavigator().hideCurrentTab(currentTab!!)
            when (menuItem.itemId) {
                R.id.navigation_chatting -> {
                    getBgColorObservable.set(R.color.chatting_bg)
                    rigthIconObservable.set(R.drawable.finduser)
                    addFollowerVisibleObservable.set(true)
                    threeDotVisibleObservable.set(false)
                    notifyVisibleObservable.set(false)
                    addLibraryVisibleObservable.set(false)
                    addTimelineVisibleObservable.set(false)
                    currentTab = Tab.CHATTING

                }
                R.id.navigation_timeline -> {
                    currentTab = Tab.TIMELINE
                    getBgColorObservable.set(R.color.timeline_bg)
                    rigthIconObservable.set(R.drawable.add)
                    addTimelineVisibleObservable.set(true)
                    addFollowerVisibleObservable.set(false)
                    notifyVisibleObservable.set(false)
                    threeDotVisibleObservable.set(false)
                    addLibraryVisibleObservable.set(false)
                }
                R.id.navigation_library -> {
                    currentTab = Tab.LIBRARY
                    getBgColorObservable.set(R.color.library_bg)
                    rigthIconObservable.set(R.drawable.add)
                    addLibraryVisibleObservable.set(true)
                    addFollowerVisibleObservable.set(false)
                    notifyVisibleObservable.set(false)
                    threeDotVisibleObservable.set(false)
                    addTimelineVisibleObservable.set(false)
                }
                R.id.navigation_my_profile -> {
                    currentTab = Tab.PROFILE
                    threeDotVisibleObservable.set(true)
                    getBgColorObservable.set(R.color.myprofile_bg)
                    rigthIconObservable.set(R.drawable.detail_setting)
                    addFollowerVisibleObservable.set(false)
                    notifyVisibleObservable.set(true)
                    addLibraryVisibleObservable.set(false)
                    addTimelineVisibleObservable.set(false)
                }
            }

            getNavigator().onNavigationTabSelected(currentTab!!)
            true
        }


    fun settingsClick() {
        when (currentTab) {
            Tab.CHATTING -> {
                getNavigator().openNextActivity(Intented.ToFindUserActivity)
            }
            Tab.TIMELINE -> {

            }
            Tab.LIBRARY -> {

            }
            Tab.PROFILE -> {

            }

        }


    }

    fun getFollowers() {
        database.collection("following").whereEqualTo("user", database.document("/users/${mainDataManager.userId}"))
            .get().addOnCompleteListener {
                it.let {

                }
            }
    }

    fun getPosts() {
        getNavigator().showLoading()
        database.collection("posts")
            .get().addOnCompleteListener { it ->
                it.let {
                    var timelineList = ArrayList<Timeline>()
                    it.result!!.documents.forEach {
                        var timeline = it.toObject(Timeline::class.java)
                        timelineList.add(timeline!!)
                    }
                    var position = 0
                    loadUserInfo(timelineList, position)
                }
            }
    }

    fun loadUserInfo(timelineList: ArrayList<Timeline>, position: Int) {
        if (position < timelineList.size) {
            var timeline = timelineList[position]
            if (timeline.user!=null) {
                var userId = timeline.user!!.path.replace("users/", "")
                var docRef = database.collection("users").document(userId)
                docRef.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        docRef.get().addOnSuccessListener { documentSnapshot ->
                            val userInfo = documentSnapshot.toObject(User::class.java)
                            timeline.userObject = userInfo!!
                            loadUserInfo(timelineList, position + 1)
                        }
                    }

                })
            }else
            {
                loadUserInfo(timelineList, position + 1)
            }
        } else {
            getNavigator().hideLoading()
            timelineListLiveData.value = timelineList
        }
    }


}