package kr.smobile.personaAI.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.smobile.personaAI.base.BaseViewHolder
import kr.smobile.personaAI.databinding.ItemFollowUserListBinding
import kr.smobile.personaAI.databinding.ItemUserListBinding
import kr.smobile.personaAI.model.Following
import kr.smobile.personaAI.model.User
import kr.smobile.personaAI.view.indepth.UserProfileActivity

class UserListAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    var userArrayList = ArrayList<User>()
    var followUserArrayList = ArrayList<Following>()
    var TYPE = 0
    var followerOrfollowing = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (TYPE == 0) {
            var itemUserListBinding =
                ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UserViewHolder(itemUserListBinding)
        } else {
            var itemFollowUserListBinding =
                ItemFollowUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FollowUserViewHolder(itemFollowUserListBinding)
        }
    }

    override fun getItemCount(): Int {
        return if (TYPE == 0) {
            userArrayList.size
        } else {
            followUserArrayList.size
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class UserViewHolder(var itemUserListBinding: ItemUserListBinding) :
        BaseViewHolder(itemUserListBinding.root) {
        override fun onBind(position: Int) {
            var user = userArrayList[position]
            itemUserListBinding.user = user
            itemUserListBinding.executePendingBindings()
            itemUserListBinding.root.setOnClickListener {
            }
        }

    }

    inner class FollowUserViewHolder(var itemFollowUserListBinding: ItemFollowUserListBinding) :
        BaseViewHolder(itemFollowUserListBinding.root) {
        override fun onBind(position: Int) {
            var following = followUserArrayList[position]
            itemFollowUserListBinding.user = following.userObject
            itemFollowUserListBinding.executePendingBindings()
            itemFollowUserListBinding.root.setOnClickListener {
                var intent = Intent(itemFollowUserListBinding.root.context, UserProfileActivity::class.java)
                intent.putExtra("color", 2)
                intent.putExtra(
                    "userId", if (followerOrfollowing == 0) {
                        following.user!!.path
                    } else {
                        following.following_user!!.path
                    }
                )
                itemFollowUserListBinding.root.context.startActivity(intent)
            }
        }

    }
}