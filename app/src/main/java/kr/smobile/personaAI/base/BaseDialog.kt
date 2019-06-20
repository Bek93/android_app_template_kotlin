/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package kr.smobile.personaAI.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kr.smobile.personaAI.utils.Intented
import java.io.Serializable
import java.util.*

/**
 * Created by Bek on 12/06/19.
 */

abstract class BaseDialog : DialogFragment(), BaseNavigator {

    var baseActivity: BaseActivity<*, *>? = null
        private set

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            if (isDialogShowFull()) {
                Objects.requireNonNull(dialog.window)
                    .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            }
        }
    }


    open fun isDialogShowFull(): Boolean = true


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // creating the fullscreen dialog
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    fun setToolbar(toolbar: Toolbar) {
        if (hasBackIcon()) {
            baseActivity!!.setSupportActionBar(toolbar)
            Objects.requireNonNull<ActionBar>(baseActivity!!.supportActionBar).setDisplayHomeAsUpEnabled(true)
            Objects.requireNonNull<ActionBar>(baseActivity!!.supportActionBar).setHomeButtonEnabled(true)
            Objects.requireNonNull<ActionBar>(baseActivity!!.supportActionBar).setDisplayShowTitleEnabled(false)
           /* Objects.requireNonNull<ActionBar>(baseActivity!!.supportActionBar)
                .setHomeAsUpIndicator(R.drawable.back_icon)*/
            toolbar.setNavigationOnClickListener { dismiss() }
        }
    }


    override fun openNextActivityClearTop(intented: kr.smobile.personaAI.utils.Intented) {
        try {
            val intent = Intent(baseActivity, Class.forName(intented.getName()))
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

    }

    protected fun hasBackIcon(): Boolean {
        return false
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String) {
        dismiss()
        baseActivity!!.onFragmentDetached(tag)
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    override fun hideLoading() {
        if (baseActivity != null) {
            baseActivity!!.hideLoading()
        }
    }

    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            baseActivity!!.openActivityOnTokenExpire()
        }
    }

    override fun showLoading() {
        if (baseActivity != null) {
            baseActivity!!.showLoading()
        }
    }



    fun openNextActivityWithData(nextActivity: Class<*>, serializabledObject: Serializable) {
        val intent = Intent(context, nextActivity)
        intent.putExtra("data", serializabledObject)
        startActivity(intent)
    }


    override fun openNextActivity(intented:Intented) {
        baseActivity!!.openNextActivity(intented)
    }

    override fun openNextActivityFinish(intented: Intented) {
        baseActivity!!.openNextActivityFinish(intented)
    }

    override fun showDialogMessage(message: String) {
        baseActivity!!.showDialogMessage(message)
    }

    override fun showDialogMessage(message: Int) {
        baseActivity!!.showDialogMessage(message)
    }

    override fun handleError(throwable: Throwable) {
        baseActivity!!.handleError(throwable)
    }

    override fun showDialogMessageAndFinish(message: String) {
        baseActivity!!.showDialogMessageAndFinish(message)
    }

    override fun onRefreshData() {

    }
}
