package kr.co.bullets.part2chapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.co.bullets.part2chapter6.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 회원가입
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 회원가입 성공
                        // 신규 계정이 생성되면 사용자도 로그인됩니다. 콜백에서 getCurrentUser 메서드로 사용자의 계정 데이터를 가져올 수 있습니다.
                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인해주세요.", Toast.LENGTH_SHORT).show()

                    } else {
                        // 회원가입 실패
                        Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // 로그인
        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // 로그인 실패
                        Log.e("LoginActivity", task.exception.toString())
                        // com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted.
                        Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}