package kr.co.bullets.part2chapter6.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bullets.part2chapter6.Key.Companion.DB_USERS
import kr.co.bullets.part2chapter6.R
import kr.co.bullets.part2chapter6.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)

        val userListAdapter = UserAdapter()

        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        val currentUserId = Firebase.auth.currentUser?.uid ?: ""

        // Firebase Realtime Database
        // 리스너를 사용하여 한 번 읽기
        // 경우에 따라 서버의 업데이트된 값을 확인하는 대신 로컬 캐시의 값을 즉시 반환하고 싶을 수 있습니다.
        // 이 경우에는 addListenerForSingleValueEvent를 사용하여 로컬 디스크 캐시에서 데이터를 즉시 가져올 수 있습니다.
        // 이 방법은 한 번 로드된 후 자주 변경되지 않거나 능동적으로 수신 대기할 필요가 없는 데이터에 유용합니다.
        // 예를 들어 위 예시의 블로깅 앱에서는 사용자가 새 글을 작성하기 시작할 때 이 메서드로 사용자의 프로필을 로드합니다.
//        Firebase.database.reference.child(DB_USERS)
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val list = snapshot.children.map {
//                        it.getValue(UserItem::class.java)
//                    }
//
//                    userListAdapter.submitList(list)
//
//                    val usersItemList = mutableListOf<UserItem>()

//                    snapshot.children.forEach {
//                        val users = it.getValue(UserItem::class.java)
//                        users ?: return
//
//                        if (users.userId != currentUserId) {
//                            usersItemList.add(users)
//                        }
//                    }
//
//                    userListAdapter.submitList(usersItemList)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//
//                }
//            })

        // Firebase Realtime Database
        // 영구 리스너로 데이터 읽기
        // 경로에서 데이터를 읽고 변경사항을 수신 대기하려면 addValueEventListener() 메서드를 사용하여
        // ValueEventListener를 DatabaseReference에 추가해야 합니다.
        Firebase.database.reference.child(DB_USERS)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val usersItemList = mutableListOf<UserItem>()

                    snapshot.children.forEach {
                        val users = it.getValue(UserItem::class.java)
                        users ?: return

                        if (users.userId != currentUserId) {
                            usersItemList.add(users)
                        }
                    }

                    userListAdapter.submitList(usersItemList)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


//        userListAdapter.submitList(
//            mutableListOf<UserItem?>().apply {
//                add(UserItem("7WxSLSG5T2Ru3YtXJZrIgt20V8B3", "류지영", "AOS Developer"))
//            }
//        )
    }
}