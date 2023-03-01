package kr.co.bullets.part2chapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = Firebase.auth.currentUser

        if (currentUser == null) {
            // 로그인이 되어있지 않음
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}