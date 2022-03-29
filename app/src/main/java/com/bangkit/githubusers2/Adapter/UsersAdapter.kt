package com.bangkit.githubusers2.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.githubusers2.databinding.ListUsersAdapterBinding
import com.bangkit.githubusers2.searchusers.ListUsers
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val lu = ArrayList<ListUsers>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(list: ArrayList<ListUsers>) {
        lu.clear()
        lu.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ListUsersAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ListUsers) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(users)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(users.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(binding.imageUsers)
                binding.usernameUsers.text = users.login
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ListUsersAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lu[position])
    }

    override fun getItemCount(): Int = lu.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ListUsers)
    }
}