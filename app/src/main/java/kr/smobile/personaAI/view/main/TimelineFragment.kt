package kr.smobile.personaAI.view.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.smobile.personaAI.BR
import kr.smobile.personaAI.R
import kr.smobile.personaAI.base.BaseFragment
import kr.smobile.personaAI.databinding.FragmentTimelineBinding
import kr.smobile.personaAI.view.adapter.TimelineListAdapter
import kr.smobile.personaAI.view.model.Timeline
import javax.inject.Inject

class TimelineFragment : BaseFragment<FragmentTimelineBinding, MainViewModel>(), MainNavigator {


    lateinit var fragmentTimelineBinding: FragmentTimelineBinding
    @Inject
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var timelineListAdapter: TimelineListAdapter
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_timeline
    override val viewModel: MainViewModel
        get() = mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTimelineBinding = viewDataBinding!!
        fragmentTimelineBinding.timelineRecyclerview.layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
        fragmentTimelineBinding.timelineRecyclerview.adapter = timelineListAdapter
        mainViewModel.getPosts()




        mainViewModel.timelineListLiveData.observe(this, Observer {
            timelineListAdapter.timelineArrayList.addAll(it)
            timelineListAdapter.notifyDataSetChanged()
        })



    }
}