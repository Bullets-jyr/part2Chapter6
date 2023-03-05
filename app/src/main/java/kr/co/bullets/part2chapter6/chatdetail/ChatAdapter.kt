package kr.co.bullets.part2chapter6.chatdetail

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.bullets.part2chapter6.databinding.ItemChatBinding
import kr.co.bullets.part2chapter6.userlist.UserItem

class ChatAdapter : ListAdapter<ChatItem, ChatAdapter.ChatViewHolder>(diffUtil){

    var otherUserItem: UserItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        return holder.bind(currentList[position])
    }

    inner class ChatViewHolder(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatItem) {
            if (item.userId == otherUserItem?.userId) {
                binding.userNameTextView.isVisible = true
                binding.userNameTextView.text = otherUserItem?.userName
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.START
            } else {
                binding.userNameTextView.isVisible = false
                binding.messageTextView.text = item.message
                binding.messageTextView.gravity = Gravity.END
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatItem>() {
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem.chatId == newItem.chatId
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}