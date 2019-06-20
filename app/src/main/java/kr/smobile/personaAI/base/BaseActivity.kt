package kr.smobile.personaAI.base

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kr.smobile.personaAI.R
import kr.smobile.personaAI.databinding.ActionBarBinding
import kr.smobile.personaAI.view.custom.CircularProgress
import java.io.Serializable
import java.util.*

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : DaggerAppCompatActivity(),
    BaseNavigator {

    lateinit var circularProgress: CircularProgress
    lateinit var mViewDataBinding: T
    lateinit var mViewModel: V
    private var mActionbar: ActionBar? = null
    private var mActionToolbar: Toolbar? = null
    private var actionbar_title: TextView? = null
    lateinit var actionBarBinding: ActionBarBinding


    abstract fun getBindingVariable(): Int
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): V


    open fun hasActionBar(): Boolean = true
    open fun hasBackIcon(): Boolean = true
    open fun hasViewModel(): Boolean {
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
        setActionbar()
        circularProgress = CircularProgress(this)

    }


    private fun setActionbar() {
        if (supportActionBar != null) {
            if (hasActionBar()) {
                actionBarBinding = DataBindingUtil.inflate(LayoutInflater.from(this), getActionBarLayout(), null, false)
                mActionbar = supportActionBar!!
                mActionbar!!.customView = actionBarBinding.root
                //setSupportActionBar(mActionToolbar)
                Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(false)
                supportActionBar!!.setHomeButtonEnabled(false)
                supportActionBar!!.setDisplayShowTitleEnabled(false)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_icon)


                setActionBarTitle(getActionBarTitleId())

                if (hasBackIcon()) {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                    supportActionBar!!.setHomeButtonEnabled(true)
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_icon)
                }

            } else {
                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
            }
        } else {
            if (hasActionBar()) {
                mActionToolbar = mViewDataBinding.root.findViewById(R.id.actionBar)
                if (mActionToolbar != null) {
                    setSupportActionBar(mActionToolbar)
                    Objects.requireNonNull<ActionBar>(supportActionBar).setDisplayHomeAsUpEnabled(false)
                    supportActionBar!!.setHomeButtonEnabled(false)
                    supportActionBar!!.setDisplayShowTitleEnabled(false)
                    supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_icon)
                    actionbar_title = mActionToolbar!!.findViewById<View>(R.id.actionbarTitle) as TextView
                    setActionBarTitle(getActionBarTitleId())

                    if (hasBackIcon()) {
                        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                        supportActionBar!!.setHomeButtonEnabled(true)
                        supportActionBar!!.setDisplayShowTitleEnabled(false)
                        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_icon)
                        mActionToolbar!!.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
                    }
                }
            }

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = getViewModel()
        if (hasViewModel()) {
            mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        }
        mViewDataBinding.executePendingBindings()
    }


    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }


    open fun getActionBarTitleId(): Int {
        return R.string.app_name
    }

    open fun getActionBarLayout(): Int {
        return R.layout.action_bar
    }


    fun setActionBarTitle(titleRes: Int) {
        if (hasActionBar() && supportActionBar != null) {
            actionbar_title!!.text = getString(titleRes)
        }
    }

    fun setActionBarTitle(title: String) {
        if (hasActionBar() && supportActionBar != null) {
            actionbar_title!!.text = title
        }
    }


    override fun onBackPressed() {
        if (!isProgressing()) {
            super.onBackPressed()
        }
    }

    fun isProgressing(): Boolean {
        return circularProgress.isShowing()
    }

    fun getViewDataBinding(): T {
        return mViewDataBinding
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun hideLoading() {
        if (circularProgress.isShowing()) {
            circularProgress.dismiss()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }


    fun isNetworkConnected(): Boolean {
        return kr.smobile.personaAI.utils.Function.checkNetworkConnection(applicationContext)
    }

    fun openActivityOnTokenExpire() {
        //startActivity(LoginActivity.newIntent(this))
        finish()
    }

    fun openNextActivityAndFinish(nextActivity: Class<*>) {
        startActivity(Intent(this, nextActivity))
        finish()
    }

    fun openNextActivity(nextActivity: Class<*>) {
        startActivity(Intent(this, nextActivity))
    }

    override fun openNextActivityFinish(intented: kr.smobile.personaAI.utils.Intented) {
        try {
            val intent = Intent(this, Class.forName(intented.getName()))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    fun openNextActivityWithData(nextActivity: Class<*>, serializabledObject: Serializable) {
        val intent = Intent(this, nextActivity)
        intent.putExtra("data", serializabledObject)
        startActivity(intent)
    }

    fun openNextActivityForResult(nextActivity: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(this, nextActivity), requestCode)
    }

    override fun openNextActivity(intented: kr.smobile.personaAI.utils.Intented) {
        try {
            openNextActivity(Class.forName(intented.getName()))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun openNextActivityClearTop(intented: kr.smobile.personaAI.utils.Intented) {
        try {
            val intent = Intent(this, Class.forName(intented.getName()))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun showLoading() {
        circularProgress.show()

    }


    override fun handleError(throwable: Throwable) {


    }

    override fun showDialogMessage(message: Int) {
        kr.smobile.personaAI.utils.Dialog.showMessage(supportFragmentManager, getString(message))
    }

    override fun showDialogMessage(message: String) {
        kr.smobile.personaAI.utils.Dialog.showMessage(supportFragmentManager, message)
    }

    override fun showLoginRequiredDialogMessage(message: String) {
        kr.smobile.personaAI.utils.Dialog.showLoginRequiredDialogMessage(supportFragmentManager, message)
            .setMessageDialogClickListener(object : kr.smobile.personaAI.utils.MessageDialogClickListener {
                override fun confirmClick() {
                    openActivityOnTokenExpire()
                }
            })
    }

    override fun showLoginRequiredDialogMessage(message: Int) {
        kr.smobile.personaAI.utils.Dialog.showLoginRequiredDialogMessage(supportFragmentManager, getString(message))
            .setMessageDialogClickListener(object : kr.smobile.personaAI.utils.MessageDialogClickListener {
                override fun confirmClick() {
                    openActivityOnTokenExpire()
                }
            })
    }

    override fun showDialogMessageAndFinish(message: String) {
        kr.smobile.personaAI.utils.Dialog.showMessage(supportFragmentManager, message)
            .setMessageDialogClickListener(object : kr.smobile.personaAI.utils.MessageDialogClickListener {
                override fun confirmClick() {
                    finish()
                }

                override fun cancelClick() {

                }
            })
    }


    fun onFragmentAttached() {

    }

    fun onFragmentDetached(tag: String) {

    }

    override fun onRefreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openNextActivity(intent: Intent) {
        startActivity(intent)
    }

    override fun finishActivityFromViewModel() {
        finish()
    }
}