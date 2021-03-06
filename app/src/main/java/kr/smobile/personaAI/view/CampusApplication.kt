package kr.smobile.personaAI.view

import android.app.Activity
import android.content.Context
import android.os.StrictMode
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.fabric.sdk.android.Fabric
import kr.smobile.personaAI.di.component.AppComponent
import kr.smobile.personaAI.di.component.DaggerAppComponent
import timber.log.Timber

import javax.inject.Inject



class CampusApplication : MultiDexApplication(), HasActivityInjector {


    private var context: Context? = null
    lateinit var component: AppComponent
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun attachBaseContext(base: Context) {
        MultiDex.install(base)
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        context = applicationContext
        Fabric.with(this, Crashlytics())
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {

        }

        FirebaseApp.initializeApp(this)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)
        component = kr.smobile.personaAI.di.component.DaggerAppComponent.builder()
            .application(this)
            .build()
        component.inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }
}
