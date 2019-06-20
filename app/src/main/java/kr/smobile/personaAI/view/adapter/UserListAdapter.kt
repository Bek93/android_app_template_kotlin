package kr.smobile.personaAI.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.smobile.personaAI.base.BaseViewHolder
import kr.smobile.personaAI.databinding.ItemListTimelineBinding
import kr.smobile.personaAI.view.indepth.TimelineIndepthActivity
import kr.smobile.personaAI.view.model.Timeline

class UserListAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    var timelineArrayList = ArrayList<Timeline>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var itemListTimelineBinding =
            ItemListTimelineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimelineListViewHolder(itemListTimelineBinding)
    }

    override fun getItemCount(): Int {
        return timelineArrayList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class TimelineListViewHolder(var itemListTimelineBinding: ItemListTimelineBinding) :
        BaseViewHolder(itemListTimelineBinding.root) {
        override fun onBind(position: Int) {
            var timeline = timelineArrayList[position]
            itemListTimelineBinding.timeline = timeline
            itemListTimelineBinding.executePendingBindings()

            itemListTimelineBinding.root.setOnClickListener {
                var intent = Intent(itemListTimelineBinding.root.context, TimelineIndepthActivity::class.java)
                itemListTimelineBinding.root.context.startActivity(intent)
            }
        }

    }
}