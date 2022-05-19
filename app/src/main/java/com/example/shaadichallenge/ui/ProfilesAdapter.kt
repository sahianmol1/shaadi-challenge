package com.example.shaadichallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.shaadichallenge.R
import com.example.shaadichallenge.data.local.Gender
import com.example.shaadichallenge.data.local.ShaadiProfileEntity
import com.example.shaadichallenge.databinding.ItemShaadiCardBinding

class ProfilesAdapter(
    val onAcceptClick: (ShaadiProfileEntity) -> Unit,
    val onDeclineClick: (ShaadiProfileEntity) -> Unit
) :
    ListAdapter<ShaadiProfileEntity, ProfilesAdapter.ProfilesViewHolder>(ProfilesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilesViewHolder {
        val binding =
            ItemShaadiCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfilesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProfilesViewHolder(private val binding: ItemShaadiCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var glide: RequestManager = Glide.with(binding.root)
        fun bind(item: ShaadiProfileEntity) {
            val context = binding.root.context
            binding.apply {

                tvAccept.setOnClickListener(null)
                tvDecline.setOnClickListener(null)

                tvName.text = item.name
                tvDob.text = item.dob

                tvInfo.text =
                    context.getString(R.string.profile_info, item.age, item.phone)
                tvAddressBio.text =
                    context.getString(R.string.profile_info, item.city, item.country)

                if (item.isAccepted != null) {
                    tvAcceptedStatus.visibility = View.VISIBLE
                    tvAcceptedStatus.text =
                        if (item.isAccepted!!) context.getString(R.string.accpeted) else context.getString(
                            R.string.declined
                        )
                    tvAccept.visibility = View.GONE
                    tvDecline.visibility = View.GONE
                } else {
                    tvAcceptedStatus.visibility = View.GONE
                    tvAccept.visibility = View.VISIBLE
                    tvDecline.visibility = View.VISIBLE
                }
                glide.load(item.picture)
                    .placeholder(if (item.gender == Gender.male) R.drawable.man else R.drawable.woman)
                    .into(imgProfile)

                tvAccept.setOnClickListener {
                    onAcceptClick(item)
                }

                tvDecline.setOnClickListener {
                    onDeclineClick(item)
                }
            }
        }
    }

    class ProfilesDiffUtil : DiffUtil.ItemCallback<ShaadiProfileEntity>() {

        override fun areItemsTheSame(
            oldItem: ShaadiProfileEntity,
            newItem: ShaadiProfileEntity
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShaadiProfileEntity,
            newItem: ShaadiProfileEntity
        ) = oldItem == newItem
    }
}