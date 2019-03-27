package net.wepla.campus_planet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.android.AndroidInjection
import net.wepla.campus_planet.databinding.ActivityPlashBinding


class SplashActivity : AppCompatActivity() {

    lateinit var activityPlashBinding: ActivityPlashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        activityPlashBinding = DataBindingUtil.setContentView(this, R.layout.activity_plash)
        activityPlashBinding.executePendingBindings()
        activityPlashBinding.button.animate()
            .alpha(0.0f)
            .setDuration(3000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    activityPlashBinding.button.visibility = View.VISIBLE
                }
            })

        activityPlashBinding.textView.animate()
            .alpha(0.0f)
            .setDuration(3000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    activityPlashBinding.textView.visibility = View.VISIBLE
                }
            })

    }

}