package kr.smobile.personaAI.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.smobile.personaAI.base.BaseViewHolder
import kr.smobile.personaAI.databinding.ItemCommentListBinding
import kr.smobile.personaAI.model.Comment

class CommentListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var commentArrayList = ArrayList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var itemCommentListBinding = ItemCommentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentHolderView(itemCommentListBinding)
    }

    override fun getItemCount(): Int {
        return commentArrayList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class CommentHolderView(var itemCommentListBinding: ItemCommentListBinding) :
        BaseViewHolder(itemCommentListBinding.root) {
        override fun onBind(position: Int) {
            var comment = commentArrayList[position]

        }

    }
}