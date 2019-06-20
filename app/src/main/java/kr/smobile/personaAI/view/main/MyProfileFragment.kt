package kr.smobile.personaAI.view.main

import android.os.Bundle
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseFragment
import kr.smobile.personaAI.databinding.FragmentChatBinding
import javax.inject.Inject

class MyProfileFragment : BaseFragment<FragmentChatBinding, MainViewModel>(), MainNavigator {


    @Inject
    lateinit var mainViewModel: MainViewModel

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_chat
    override val viewModel: MainViewModel
        get() = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.setNavigator(this)
    }
}