package org.android.go.sopt.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.android.go.sopt.databinding.ItemPlaylistSongBinding
import org.android.go.sopt.util.ItemDiffCallback

class PlaylistAdapter :
    ListAdapter<PlaylistSong, PlaylistViewHolder>(
        ItemDiffCallback<PlaylistSong>(onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new })
    ) {

    var selectionList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding: ItemPlaylistSongBinding =
            ItemPlaylistSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.chkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectionList.add(position)
            } else {
                selectionList.remove(position)
            }
        }
    }
}