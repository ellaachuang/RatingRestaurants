package com.example.ratingrestaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.ratingRestaurants.R
import com.example.ratingRestaurants.databinding.ActivityRestaurantDetailBinding

class RestaurantDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRestaurantDetailBinding
    var restaurantIsEditable = false
    lateinit var restaurant : Restaurant



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var passedRestaurant = intent.getParcelableExtra<Restaurant>(RestaurantListActivity.EXTRA_RESTAURANT)
        if (passedRestaurant == null) {
            restaurant = Restaurant()
            toggleEditable()
        } else {
            restaurant = passedRestaurant!!
            binding.editTextRating.setText(restaurant.rating.toString())
            binding.editTextName.setText(restaurant.name)
            binding.editTextPrice.setText(restaurant.priceRange)
            binding.editTextLocation.setText(restaurant.location)
            binding.editTextDesc.setText(restaurant.desc)
        }

        binding.buttonSave.setOnClickListener {
            if(restaurant.ownerId.isNullOrBlank()) {
                restaurant.ownerId = intent.getStringExtra(RestaurantListActivity.EXTRA_USER_ID)!!
            }
            restaurant.name = binding.editTextName.text.toString()
            restaurant.rating = binding.editTextRating.text.toString().toDouble()
            restaurant.priceRange = binding.editTextPrice.text.toString()
            restaurant.location = binding.editTextLocation.text.toString()
            restaurant.desc = binding.editTextDesc.text.toString()

            Backendless.Persistence.of(Restaurant::class.java).save(restaurant, object: AsyncCallback<Restaurant> {
                override fun handleResponse(response: Restaurant?) {
                    Toast.makeText(this@RestaurantDetailActivity, "Loan Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun handleFault(fault: BackendlessFault?) {
                    Toast.makeText(this@RestaurantDetailActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_restaurant_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
//            R.id.menu_item_restaurant_detail_edit -> {
//                toggleEditable()
//                true
//            }
            R.id.menu_item_restaurant_detail_delete -> {
                deleteFromBackendless()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteFromBackendless() {
        Backendless.Data.of(Restaurant::class.java).remove(restaurant,
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    // Person has been deleted. The response is the
                    // time in milliseconds when the object was deleted
                    Toast.makeText(this@RestaurantDetailActivity, "${restaurant.name} Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("BirthdayDetail", "handleFault: ${fault.message}")
                }
            })
    }


    private fun toggleEditable() {
        if (restaurantIsEditable) {
            restaurantIsEditable = false
            binding.editTextName.inputType = InputType.TYPE_NULL
            binding.editTextName.isEnabled = false
            binding.editTextDesc.inputType = InputType.TYPE_NULL
            binding.editTextDesc.isEnabled = false
            binding.editTextLocation.inputType = InputType.TYPE_NULL
            binding.editTextLocation.isEnabled = false
            binding.editTextPrice.inputType = InputType.TYPE_NULL
            binding.editTextPrice.isEnabled = false
            binding.editTextRating.inputType = InputType.TYPE_NULL
            binding.editTextRating.isEnabled = false
        } else {
            restaurantIsEditable = false
            binding.editTextName.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextName.isEnabled = true
            binding.editTextDesc.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextDesc.isEnabled = true
            binding.editTextLocation.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextLocation.isEnabled = true
            binding.editTextPrice.inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
            binding.editTextPrice.isEnabled = true
            binding.editTextRating.inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL
            binding.editTextRating.isEnabled = true
        }
    }
}