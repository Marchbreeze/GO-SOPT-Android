package org.android.go.sopt.presentation.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import org.android.go.sopt.R
import org.android.go.sopt.data.ServicePool
import org.android.go.sopt.data.SignUpRequestDTO
import org.android.go.sopt.data.SignUpResponseDTO
import org.android.go.sopt.databinding.ActivitySignUpBinding
import org.android.go.sopt.util.KeyboardVisibilityUtils
import org.android.go.sopt.util.makeSnackBar
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    private val signUpService = ServicePool.signUpService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 조건에 따라 가입 버튼 활성화 / 비활성화
        binding.btnSignUp.isEnabled = false

        binding.etSignUpId.doAfterTextChanged {
            binding.btnSignUp.isEnabled = canUserSignIn()
        }
        binding.etSignUpPw.doAfterTextChanged {
            binding.btnSignUp.isEnabled = canUserSignIn()
        }
        binding.etSignUpName.doAfterTextChanged {
            binding.btnSignUp.isEnabled = canUserSignIn()
        }
        binding.etSignUpSkill.doAfterTextChanged {
            binding.btnSignUp.isEnabled = canUserSignIn()
        }

        // SignUp 버튼 클릭
        binding.btnSignUp.setOnClickListener {
            if (canUserSignIn()) {
                signUpWithServer()
            } else {
                binding.root.makeSnackBar(getString(R.string.snackbar_signup_rule))
            }
        }

        // 화면 터치로 키보드 내리기
        binding.root.setOnClickListener {
            hideKeyboard(this)
        }

        // 받아온 클래스 활용해 스크롤뷰에 적용
        keyboardVisibilityUtils = KeyboardVisibilityUtils(window,
            onShowKeyboard = { keyboardHeight ->
                binding.svSignUp.run {
                    smoothScrollTo(scrollX, scrollY + keyboardHeight)
                }
            })
    }

    private fun hideKeyboard(activity: Activity) {
        val keyboard = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }

    private fun signUpWithServer() {
        signUpService.login(
            with(binding) {
                SignUpRequestDTO(
                    etSignUpId.text.toString(),
                    etSignUpPw.text.toString(),
                    etSignUpName.text.toString(),
                    etSignUpSkill.text.toString()
                )
            }
        ).enqueue(object : retrofit2.Callback<SignUpResponseDTO> {
            override fun onResponse(
                call: Call<SignUpResponseDTO>,
                response: Response<SignUpResponseDTO>
            ) {
                if (response.isSuccessful) {
                    binding.root.makeSnackBar(getString(R.string.snackbar_signup_success))
                    if (!isFinishing) finish()
                } else {
                    binding.root.makeSnackBar(getString(R.string.snackbar_signup_server))
                }
            }
            override fun onFailure(call: Call<SignUpResponseDTO>, t: Throwable) {
                binding.root.makeSnackBar(getString(R.string.snackbar_signup_server))
            }
        })
    }

    private fun canUserSignIn(): Boolean {
        return binding.etSignUpId.text.length in 6..10
                && binding.etSignUpPw.text.length in 8..12
                && binding.etSignUpName.text.isNotBlank()
                && binding.etSignUpSkill.text.isNotBlank()
    }
}