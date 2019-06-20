package kr.smobile.personaAI.view.indepth

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kr.smobile.personaAI.base.BaseViewModel
import kr.smobile.personaAI.data.MainDataManager
import kr.smobile.personaAI.model.Following
import kr.smobile.personaAI.model.User
import kr.smobile.personaAI.rx.SchedulerProvider
import kr.smobile.personaAI.utils.Function
import kr.smobile.personaAI.utils.Intented
import java.sql.Timestamp

class IndepthViewModel(var mainDataManager: MainDataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<IndepthNavigator>(schedulerProvider) {

    var database = FirebaseFirestore.getInstance()
    var followingObservableBoolean = ObservableBoolean(false)

    var userMutableLiveData = MutableLiveData<User>()
    var followingArrayListLiveData = MutableLiveData<ArrayList<Following>>()

    var userUid: String? = null
    fun openLikedPage() {
        getNavigator().openNextActivity(Intented.ToPostLikedByUserActivity)
    }

    fun getUserInfo(userId: String?) {
        userUid = userId
        getNavigator().showLoading()
        var docRef = database.document(userId!!)
        docRef.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->
            getNavigator().hideLoading()
            if (documentSnapshot != null && documentSnapshot.exists()) {
                docRef.get().addOnSuccessListener { documentSnapshot ->
                    val userInfo = documentSnapshot.toObject(User::class.java)
                    userMutableLiveData.value = userInfo

                    database.collection("following")
                        .whereEqualTo("following_user", database.document(userId))
                        .whereEqualTo(
                            "user", database.document(
                                "/users/${mainDataManager.userId}"
                            )
                        ).addSnapshotListener(EventListener<QuerySnapshot> { querySpanShot, e ->
                            getNavigator().hideLoading()
                            if (querySpanShot != null && !querySpanShot.isEmpty) {
                                followingObservableBoolean.set(true)
                            }
                        })

                }
            }

        })
    }

    fun followUser() {

        var timestamp = Timestamp(System.currentTimeMillis())
        var followingRef = database.collection("following")
        var following = Following()
        following.user = database.document(
            "/users/${mainDataManager.userId}"
        )
        following.following_user = database.document(userUid!!)
        following.timestamp = Function.nowAsISO()

        followingRef.add(following).addOnSuccessListener {
            followingObservableBoolean.set(true)
        }


    }

    fun getFollowers() {
        database.collection("following")
            .whereEqualTo(
                "following_user", database.document(
                    "/users/${mainDataManager.userId}"
                )
            ).addSnapshotListener(EventListener<QuerySnapshot> { querySpanShot, e ->
                getNavigator().hideLoading()
                if (querySpanShot != null && !querySpanShot.isEmpty) {
                    var followingList = ArrayList<Following>()
                    querySpanShot.documents.forEach {
                        followingList.add(it.toObject(Following::class.java)!!)
                    }
                    loadUserInfo(followingList, 0,1)
                } else {
                    followingArrayListLiveData.value = null
                }
            })
    }

    fun getFollowings() {
        database.collection("following")
            .whereEqualTo(
                "user", database.document(
                    "/users/${mainDataManager.userId}"
                )
            ).addSnapshotListener(EventListener<QuerySnapshot> { querySpanShot, e ->
                getNavigator().hideLoading()
                if (querySpanShot != null && !querySpanShot.isEmpty) {
                    var followingList = ArrayList<Following>()
                    querySpanShot.documents.forEach {
                        followingList.add(it.toObject(Following::class.java)!!)
                    }

                    loadUserInfo(followingList, 0, 0)
                } else {
                    followingArrayListLiveData.value = null
                }
            })
    }


    fun loadUserInfo(followingList: ArrayList<Following>, position: Int, type: Int) {
        if (position < followingList.size) {
            var following = followingList[position]
            if (following.following_user != null && type == 0) {
                var userId = following.following_user!!.path.replace("users/", "")
                var docRef = database.collection("users").document(userId)
                docRef.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        docRef.get().addOnSuccessListener { documentSnapshot ->
                            val userInfo = documentSnapshot.toObject(User::class.java)
                            following.userObject = userInfo
                            loadUserInfo(followingList, position + 1,type)
                        }
                    }

                })
            } else if (type == 1 && following.user != null) {

                var userId = following.following_user!!.path.replace("users/", "")
                var docRef = database.collection("users").document(userId)
                docRef.addSnapshotListener(EventListener<DocumentSnapshot> { documentSnapshot, e ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        docRef.get().addOnSuccessListener { documentSnapshot ->
                            val userInfo = documentSnapshot.toObject(User::class.java)
                            following.userObject = userInfo
                            loadUserInfo(followingList, position + 1, type)
                        }
                    }

                })

            } else {
                loadUserInfo(followingList, position + 1, type)
            }
        } else {
            getNavigator().hideLoading()
            followingArrayListLiveData.value = followingList
        }
    }
}