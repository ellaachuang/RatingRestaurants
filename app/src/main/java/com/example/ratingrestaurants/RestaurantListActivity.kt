package com.example.ratingrestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.ratingRestaurants.R
import com.example.ratingRestaurants.databinding.ActivityRestaurantListBinding


class RestaurantListActivity : AppCompatActivity() {

    companion object {
        val TAG = "RestaurantListActivity"
        val EXTRA_USER_ID = "userId"
        val EXTRA_RESTAURANT = "restaurant"
    }

    private lateinit var binding: ActivityRestaurantListBinding
    lateinit var adapter: RestaurantAdapter
    lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabLoanListCreateNewLoan.setOnClickListener{
            val loanDetailIntent = Intent(this, RestaurantDetailActivity::class.java).apply{
                putExtra(EXTRA_USER_ID, userId)
            }
            startActivity(loanDetailIntent)
        }

    }

    override fun onStart() {
        super.onStart()
        userId = intent.getStringExtra(EXTRA_USER_ID).toString()
        Log.d("", "$userId")
        if (userId != null)
            retrieveAllData(userId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.restaurant_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        var rest = adapter.restaurantList.toList()
        return when (item.itemId) {
            R.id.item_restaurantListMenu_sortByHigh -> {
                rest = adapter.restaurantList.sortedByDescending {
                    it.rating
                }
                adapter.restaurantList = rest.toMutableList()
                adapter.notifyDataSetChanged()
                true
            }
            R.id.item_restaurantListMenu_sortByLow -> {
                rest = adapter.restaurantList.sortedBy {
                    it.rating
                }
                adapter.restaurantList = rest.toMutableList()
                adapter.notifyDataSetChanged()
                true
            }
            R.id.item_restaurantListMenu_sortByAlpha -> {
                rest = adapter.restaurantList.sortedBy {
                    it.name
                }
                adapter.restaurantList = rest.toMutableList()
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun retrieveAllData(userId: String) {
        val whereClause = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Restaurant::class.java).find(queryBuilder, object :
            AsyncCallback<List<Restaurant?>?> {
            override fun handleResponse(foundLoans: List<Restaurant?>?) {
                Log.d(LoginActivity.TAG, "handleResponse: $foundLoans")
                adapter = RestaurantAdapter(foundLoans as MutableList<Restaurant>)
                binding.loanListRecyclerView.adapter = adapter
                binding.loanListRecyclerView.layoutManager = LinearLayoutManager(this@RestaurantListActivity)
            }
            override fun handleFault(fault: BackendlessFault) {
                Log.d(LoginActivity.TAG, "handleFault: ${fault.message}")
            }
        })
    }


}