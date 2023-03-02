package kr.co.bullets.part2chapter6.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.bullets.part2chapter6.R
import kr.co.bullets.part2chapter6.databinding.FragmentChatRoomBinding

class ChatRoomFragment : Fragment() {

    private lateinit var binding: FragmentChatRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatRoomBinding.bind(view)

        val chatRoomListAdapter = ChatRoomAdapter()

        binding.chatRoomListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = chatRoomListAdapter
        }

        chatRoomListAdapter.submitList(
            mutableListOf<ChatRoomItem?>().apply {
                add(ChatRoomItem("7WxSLSG5T2Ru3YtXJZrIgt20V8B3", "류지영", "Android Jetpack을 사용해야 하는 이유는 무엇인가요?"))
            }
        )
    }
}