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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bullets.part2chapter6.Key.Companion.DB_USERS
import kr.co.bullets.part2chapter6.LoginActivity
import kr.co.bullets.part2chapter6.R
import kr.co.bullets.part2chapter6.chatlist.ChatRoomAdapter
import kr.co.bullets.part2chapter6.chatlist.ChatRoomItem
import kr.co.bullets.part2chapter6.databinding.FragmentChatRoomBinding
import kr.co.bullets.part2chapter6.databinding.FragmentMyPageBinding
import kr.co.bullets.part2chapter6.userlist.UserItem

class MyPageFragment : Fragment(R.layout.fragment_my_page) {

    private lateinit var binding: FragmentMyPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPageBinding.bind(view)

        val currentUserId = Firebase.auth.currentUser?.uid ?: ""

        // Firebase Realtime Database
        val currentUserDB = Firebase.database.reference.child(DB_USERS).child(currentUserId)

        // 데이터 한 번 읽기
        // get()을 사용하여 한 번 읽기
        // SDK는 앱이 온라인이든 오프라인이든 상관없이 데이터베이스 서버와의 상호작용을 관리하도록 설계되었습니다.
        // 일반적으로 위에서 설명한 ValueEventListener 기법을 사용하여 데이터를 읽어 백엔드에서 데이터에 대한 업데이트 알림을 수신해야 합니다.
        // 리스너 기법은 사용량과 청구 비용을 줄여주며 사용자가 온라인과 오프라인으로 진행할 때 최상의 환경을 제공하도록 최적화되어 있습니다.
        // 데이터가 한 번만 필요한 경우 get()을 사용하여 데이터베이스에서 데이터의 스냅샷을 가져올 수 있습니다.
        // 어떠한 이유로든 get()이 서버 값을 반환할 수 없는 경우 클라이언트는 로컬 스토리지 캐시를 프로브하고 값을 여전히 찾을 수 없으면 오류를 반환합니다.
        // 불필요한 get() 사용은 대역폭 사용을 증가시키고 성능 저하를 유발할 수 있지만 위와 같이 실시간 리스너를 사용하면 이를 방지할 수 있습니다.
        currentUserDB.get().addOnSuccessListener {
            val currentUserItem = it.getValue(UserItem::class.java) ?: return@addOnSuccessListener

            binding.userNameEditText.setText(currentUserItem.userName)
            binding.descriptionEditText.setText(currentUserItem.description)
        }

        binding.applyButton.setOnClickListener {
            val userName = binding.userNameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            if (userName.isEmpty()) {
                Toast.makeText(context, "유저이름은 빈 값으로 두실 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = mutableMapOf<String, Any>()
            user["userName"] = userName
            user["description"] = description

            currentUserDB.updateChildren(user)
        }

        binding.signOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}