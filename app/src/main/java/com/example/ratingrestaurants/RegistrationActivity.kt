package com.example.ratingrestaurants

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.ratingRestaurants.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    companion object {
        val TAG = "Registration"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME)
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD)

        binding.textEditUsernameRegistration.setText(username)
        binding.textEditPasswordRegistration.setText(password)

        binding.buttonRegister.setOnClickListener{
            val password = binding.textEditPasswordRegistration.text.toString()
            val username = binding.textEditUsernameRegistration.text.toString()
            val confirm = binding.textEditConfirmPasswordRegistration.text.toString()
            val name = binding.textEditNameRegistration.text.toString()
            val email = binding.textEditEmailRegistration.text.toString()

            if (RegistrationUtil.validatePassword(password, confirm) && RegistrationUtil.validateUsername(username)){
                registrationUserOnBackendless(username, password, name, email)
            }
        }
    }

        private fun registrationUserOnBackendless(
            username: String,
            password: String,
            name: String,
            email: String
        ){
            val user = BackendlessUser()
            user.setProperty("email", email)
            user.password = password
            user.setProperty("name", name)
            user.setProperty("username", username)

            Backendless.UserService.register(user, object : AsyncCallback<BackendlessUser?>{
                override fun handleResponse(registerUser: BackendlessUser?){
                    Log.d(TAG, "handleResponse: ${user?.getProperty("name")} successfully registered")
                    val resultIntent = Intent().apply {
                        putExtra(
                            LoginActivity.EXTRA_USERNAME,
                            binding.textEditUsernameRegistration.text.toString()
                        )
                        putExtra(LoginActivity.EXTRA_USERNAME, binding.textEditUsernameRegistration.text.toString())
                        putExtra(LoginActivity.EXTRA_PASSWORD, binding.textEditPasswordRegistration.text.toString())
                    }

                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
                override fun handleFault(fault: BackendlessFault){
                    Log.d(TAG, "handleFault: ${fault.message}")
                }
            })
        }

}