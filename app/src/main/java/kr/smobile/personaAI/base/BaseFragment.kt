package net.wepla.campus_planet.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import net.wepla.campus_planet.utils.Dialog
import net.wepla.campus_planet.utils.Intented
import net.wepla.campus_planet.utils.MessageDialogClickListener

import java.io.Serializable

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(), BaseNavigator {
    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var mRootView: View? = null
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            baseActivity!!.openActivityOnTokenExpire()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    override fun onRefreshData() {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onRefreshData()
        }
    }

    override fun onResume() {
        super.onResume()
        onRefreshData()
    }

    fun openNextActivityAndFinish(nextActivity: Class<*>) {
        startActivity(Intent(context, nextActivity))
        baseActivity!!.finish()
    }

    fun openNextActivity(nextActivity: Class<*>) {
        startActivity(Intent(context, nextActivity))
    }

    override fun openNextActivityFinish(intented: Intented) {
        try {
            val intent = Intent(context, Class.forName(intented.getName()))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    fun openNextActivityWithData(nextActivity: Class<*>, serializabledObject: Serializable) {
        val intent = Intent(context, nextActivity)
        intent.putExtra("data", serializabledObject)
        startActivity(intent)
    }

    fun openNextActivityForResult(nextActivity: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(context, nextActivity), requestCode)
    }

    override fun openNextActivity(intented: Intented) {
        try {
            openNextActivity(Class.forName(intented.getName()))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }


    override fun openNextActivityClearTop(intented: Intented) {
        try {
            val intent = Intent(context, Class.forName(intented.getName()))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    override fun hideLoading() {
        baseActivity!!.hideLoading()
    }


    override fun showLoading() {
        baseActivity!!.showLoading()

    }

    override fun handleError(throwable: Throwable) {

    }

    override fun showDialogMessage(message: Int) {
        Dialog.showMessage(childFragmentManager, getString(message))
    }

    override fun showDialogMessage(message: String) {
        Dialog.showMessage(childFragmentManager, message)
    }


    override fun showDialogMessageAndFinish(message: String) {
        Dialog.showMessage(childFragmentManager, message)
            .setMessageDialogClickListener(object : MessageDialogClickListener{
                override fun confirmClick() {
                    baseActivity!!.finish()
                }

                override fun cancelClick() {

                }
            })
    }


}
