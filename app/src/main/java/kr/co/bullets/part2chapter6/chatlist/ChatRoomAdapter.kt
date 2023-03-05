package kr.co.bullets.part2chapter6.chatlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.bullets.part2chapter6.databinding.ItemChatRoomBinding

class ChatRoomAdapter : ListAdapter<ChatRoomItem, ChatRoomAdapter.ChatRoomHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomHolder {
        return ChatRoomHolder(ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatRoomHolder, position: Int) {
        return holder.bind(currentList[position])
    }

    inner class ChatRoomHolder(private val binding: ItemChatRoomBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatRoomItem) {
            binding.nickNameTextView.text = item.otherUserName
            binding.lastMessageTextView.text = item.lastMessage
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatRoomItem>() {
            override fun areItemsTheSame(oldItem: ChatRoomItem, newItem: ChatRoomItem): Boolean {
                return oldItem.chatRoomId == newItem.chatRoomId
            }

            override fun areContentsTheSame(oldItem: ChatRoomItem, newItem: ChatRoomItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}