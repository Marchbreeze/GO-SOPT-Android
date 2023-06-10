package org.android.go.sopt.presentation.follower

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import org.android.go.sopt.databinding.ItemFollowerBinding
import org.android.go.sopt.data.remote.FollowerResponseDTO

class FollowerViewHolder(val binding: ItemFollowerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: FollowerResponseDTO.User) {
        val name = item.firstName + " " + item.lastName
        binding.tvFollowerName.text = name
        binding.tvFollowerEmail.text = item.email
        binding.ivFollowerImage.load(item.avatar) {
            transformations(RoundedCornersTransformation(10.0F))
        }
    }
}