package kr.smobile.personaAI

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjection
import kr.smobile.personaAI.base.PreferencesManager
import kr.smobile.personaAI.databinding.ActivityPlashBinding
import kr.smobile.personaAI.view.main.MainActivity
import kr.smobile.personaAI.view.onboarding.LoginActivity


class SplashActivity : AppCompatActivity() {

    lateinit var activityPlashBinding: ActivityPlashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)

        supportActionBar!!.hide()
        activityPlashBinding = DataBindingUtil.setContentView(this, R.layout.activity_plash)
        activityPlashBinding.executePendingBindings()


        Handler().postDelayed({
            var intent = Intent(
                this@SplashActivity,
                if (PreferencesManager.getInstance(this@SplashActivity).isSignedIn) {
                    MainActivity::class.java
                } else {
                    LoginActivity::class.java
                }
            )
            startActivity(intent)
        }, 2000)

    }


}