package com.example.ratingrestaurants

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.ratingRestaurants.databinding.ActivityLoginBinding

//class LoginActivity : AppCompatActivity() {
//
//    companion object {
//        val EXTRA_USERNAME = "username"
//        val EXTRA_PASSWORD = "password"
//        val TAG = "loginActivity"
//    }
//
//    val startRegistrationForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val intent = result.data
//            // Handle the Intent
//            binding1.editTextNameLogin.setText(intent?.getStringExtra(EXTRA_USERNAME))
//            binding1.editTextPasswordLogin.setText(intent?.getStringExtra(EXTRA_PASSWORD))
//        }
//    }
//
//    private lateinit var binding1: ActivityLoginBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding1 = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding1.root)
//
//        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)
//
//        binding1.buttonLogin.setOnClickListener {
//            val loanIntent = Intent(this, RestaurantListActivity::class.java)
//            Backendless.UserService.login(
//                binding1.editTextNameLogin.text.toString(),
//                binding1.editTextPasswordLogin.text.toString(),
//                object : AsyncCallback<BackendlessUser?> {
//                    override fun handleResponse(user: BackendlessUser?) {
//                        Log.d(TAG, "handleResponse: ${user?.getProperty("name")} has logged in")
//                        Log.d(TAG, "${user?.getProperty("objectId")}")
//                        val userId = user?.getProperty("objectId").toString()
//
//                        loanIntent.putExtra(RestaurantListActivity.EXTRA_USER_ID, userId)
//                        startActivity(loanIntent)
//
//                        finish()
//                    }
//
//                    override fun handleFault(fault: BackendlessFault) {
//                        Log.d(TAG, "handleFault: ${fault.message}")
//                        finish()
//                    }
//                }
//            ) }
//
//        binding1.signup.setOnClickListener{
//            val registrationIntent = Intent(this, RegistrationActivity::class.java)
//            registrationIntent.putExtra(EXTRA_USERNAME, binding1.editTextNameLogin.text.toString())
//            registrationIntent.putExtra(EXTRA_PASSWORD, binding1.editTextPasswordLogin.text.toString())
//            startRegistrationForResult.launch(registrationIntent)
//        }
//
//
//    }
//
//}

class LoginActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
        val TAG = "loginActivity"
    }

    val startRegistrationForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            // Handle the Intent
            binding.editTextNameLogin.setText(intent?.getStringExtra(EXTRA_USERNAME))
            binding.editTextPasswordLogin.setText(intent?.getStringExtra(EXTRA_PASSWORD))
        }
    }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)

        binding.buttonLogin.setOnClickListener {
            val loanIntent = Intent(this,RestaurantListActivity::class.java)
            Backendless.UserService.login(
                binding.editTextNameLogin.text.toString(),
                binding.editTextPasswordLogin.text.toString(),
                object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(user: BackendlessUser?) {
                        Log.d(TAG, "handleResponse: ${user?.getProperty("name")} has logged in")
                        Log.d(TAG, "${user?.getProperty("objectId")}")
                        val userId = user?.getProperty("objectId").toString()

                        loanIntent.putExtra(RestaurantListActivity.EXTRA_USER_ID, userId)
                        startActivity(loanIntent)

                        finish()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        Log.d(TAG, "handleFault: ${fault.message}")
//                        finish()
                    }
                }
            ) }

        binding.signup.setOnClickListener{
            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            registrationIntent.putExtra(EXTRA_USERNAME, binding.editTextNameLogin.text.toString())
            registrationIntent.putExtra(EXTRA_PASSWORD, binding.editTextPasswordLogin.text.toString())
            startRegistrationForResult.launch(registrationIntent)
        }


    }

}