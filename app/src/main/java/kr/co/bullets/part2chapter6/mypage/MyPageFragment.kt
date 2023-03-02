package kr.co.bullets.part2chapter6.mypage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.bullets.part2chapter6.LoginActivity
import kr.co.bullets.part2chapter6.R
import kr.co.bullets.part2chapter6.chatlist.ChatRoomAdapter
import kr.co.bullets.part2chapter6.chatlist.ChatRoomItem
import kr.co.bullets.part2chapter6.databinding.FragmentChatRoomBinding
import kr.co.bullets.part2chapter6.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment(R.layout.fragment_my_page) {

    private lateinit var binding: FragmentMyPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPageBinding.bind(view)

        val chatRoomListAdapter = ChatRoomAdapter()

        binding.applyButton.setOnClickListener {
            val userName = binding.userNameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if (userName.isEmpty()) {
                Toast.makeText(context, "유저이름은 빈 값으로 두실 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}