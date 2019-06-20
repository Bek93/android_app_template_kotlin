package kr.smobile.personaAI.view.onboarding

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kr.smobile.personaAI.base.BaseNavigator
import kr.smobile.personaAI.base.BaseViewModel
import kr.smobile.personaAI.data.MainDataManager
import kr.smobile.personaAI.model.User
import kr.smobile.personaAI.rx.SchedulerProvider
import kr.smobile.personaAI.utils.Function
import kr.smobile.personaAI.utils.Intented
import timber.log.Timber

class OnboardingViewModel(private var mainDataManager: MainDataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<BaseNavigator>(schedulerProvider) {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var TAG = "OnboardingViewModel"
    var database = FirebaseFirestore.getInstance()
    var currentUserLiveData = MutableLiveData<FirebaseUser>()
    var isEmailAvailable = ObservableBoolean(false)
    var isEmailCorrect = ObservableBoolean(false)
    var email = ""
    var password = ""
    var nickname = ""
    var confirmPassword = ""

    fun onStart() {
        val currentUser = auth.currentUser
        currentUserLiveData.value = currentUser
    }


    fun createUser() {


        if (TextUtils.isEmpty(email)) {
            return
        }
        if (!Function.isValidEmail(email)) {
            return
        }
        if (TextUtils.isEmpty(nickname)) {
            return
        }
        if (TextUtils.isEmpty(password)) {
            return
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            return
        }
        if (password != confirmPassword) {
            return
        }
        getNavigator().showLoading()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                getNavigator().hideLoading()
                Timber.d("createUserWithEmail:success")
                val user = auth.currentUser
                currentUserLiveData.value = user
                var userOriginal = User()
                userOriginal.email = email
                userOriginal.name = nickname
                mainDataManager.setSignedIn(true)
                mainDataManager.userId = user!!.uid
                database.collection("users").document(user!!.uid).set(userOriginal)

            } else {
                getNavigator().hideLoading()
                Timber.d("createUserWithEmail:failure ${it.exception}")
                getNavigator().showDialogMessage("Authentication failed.")
                currentUserLiveData.value = null
            }
        }
    }

    fun login() {

        if (TextUtils.isEmpty(email)) {
            return
        }
        if (TextUtils.isEmpty(password)) {
            return
        }
        getNavigator().showLoading()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                getNavigator().hideLoading()
                Timber.d("createUserWithEmail:success")
                val user = auth.currentUser
                currentUserLiveData.value = user
                mainDataManager.setSignedIn(true)
                mainDataManager.userId = user!!.uid
                getNavigator().openNextActivityClearTop(Intented.ToMainActivity)
            } else {
                getNavigator().hideLoading()
                Timber.d("createUserWithEmail:failure ${it.exception}")
                getNavigator().showDialogMessage("Authentication failed.")
                currentUserLiveData.value = null
            }
        }
    }


    internal fun getTextWatcherListener(type: Int): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {

                when (type) {
                    1 -> {
                        email = editable.toString().trim()
                        isEmailCorrect.set(Function.isValidEmail(email))
                    }
                    2 -> {
                        nickname = editable.toString().trim()
                    }
                    3 -> {
                        password = editable.toString().trim()
                    }
                    4 -> {
                        confirmPassword = editable.toString().trim()
                    }
                }
            }
        }
    }

    fun checkEmail() {
        if (TextUtils.isEmpty(email)) {
            return
        }
        getNavigator().showLoading()
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
            getNavigator().hideLoading()
            if (it.isSuccessful) {
                var providerResult = it.result
                val signInMethods = providerResult?.signInMethods ?: emptyList<String>()
                var isNewAccount = signInMethods.isNotEmpty()
                isEmailAvailable.set(isNewAccount)
                if (isNewAccount) {
                    getNavigator().showDialogMessage("이미 사용중인 이메일입니다.")
                }
            }
        }
    }

    fun resetPassword() {
        if (TextUtils.isEmpty(email)) {
            return
        }
        getNavigator().showLoading()
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            getNavigator().hideLoading()
            if (it.isSuccessful) {
                getNavigator().finishActivityFromViewModel()
            } else {
                var errorCode = (it.exception as FirebaseAuthInvalidUserException).errorCode
                if (errorCode == "ERROR_USER_NOT_FOUND") {
                    getNavigator().showDialogMessage("가입된 이메일이 아닙니다.")
                }
            }
        }
    }

}

