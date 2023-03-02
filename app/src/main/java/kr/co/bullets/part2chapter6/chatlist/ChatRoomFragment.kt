package kr.co.bullets.part2chapter6.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bullets.part2chapter6.Key.Companion.DB_CHAT_ROOMS
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

        val currentUserId = Firebase.auth.currentUser?.uid ?: return

        // Firebase Realtime Database
        // 영구 리스너로 데이터 읽기
        // 경로에서 데이터를 읽고 변경사항을 수신 대기하려면 addValueEventListener() 메서드를 사용하여
        // ValueEventListener를 DatabaseReference에 추가해야 합니다.
        val chatRoomsDB = Firebase.database.reference.child(DB_CHAT_ROOMS).child(currentUserId)
        chatRoomsDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatRoomList = snapshot.children.map {
                    it.getValue(ChatRoomItem::class.java)
                }

                chatRoomListAdapter.submitList(chatRoomList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

//        chatRoomListAdapter.submitList(
//            mutableListOf<ChatRoomItem?>().apply {
//                add(ChatRoomItem("7WxSLSG5T2Ru3YtXJZrIgt20V8B3", "류지영", "Android Jetpack을 사용해야 하는 이유는 무엇인가요?"))
//            }
//        )
    }
}