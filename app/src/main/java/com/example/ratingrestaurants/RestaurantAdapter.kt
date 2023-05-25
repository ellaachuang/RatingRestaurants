package com.example.ratingrestaurants

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.ratingRestaurants.R
import weborb.util.ThreadContext.context

class RestaurantAdapter(var restaurantList: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewRating: TextView
        val textViewPrice: TextView
        val textViewLocation: TextView
        val textViewDesc: TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            textViewName = view.findViewById(R.id.name)
            textViewRating = view.findViewById(R.id.rating)
            textViewPrice = view.findViewById(R.id.price)
            textViewLocation = view.findViewById(R.id.location)
            textViewDesc = view.findViewById(R.id.typeOfFood)
            layout = view.findViewById(R.id.restaurant_item_layout)
        }
    }

    // Create new views 7(invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)

        return ViewHolder(view)
//        val holder = ViewHolder(view)
//        return holder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val restaurant = restaurantList[position]
        val context = holder.textViewName.context
        holder.textViewName.text = restaurant.name
        holder.textViewDesc.text = restaurant.desc
        holder.textViewRating.text = restaurant.rating.toString()
        holder.textViewLocation.text = restaurant.location
        holder.textViewPrice.text = restaurant.priceRange
        holder.layout.isLongClickable = true

        holder.layout.setOnClickListener{
//            val popMenu = PopupMenu(context, holder.textViewName)
//            popMenu.inflate(R.menu.menu_restaurant_detail)
//            popMenu.setOnMenuItemClickListener {
//                when(it.itemId){
//                    R.id.menu_item_restaurant_detail_delete -> {
//                        deleteFromBackendless(position)
//                        true
//                    }
//                    R.id.menu_item_restaurant_detail_edit -> {
//                        val detailIntent = Intent(context, RestaurantDetailActivity::class.java)
//                        detailIntent.putExtra(RestaurantListActivity.EXTRA_RESTAURANT, restaurant)
//
//                        context.startActivity(detailIntent)
//                        true
//                    }
//                    else -> true
//                }
//            }
//            popMenu.show()
//            true
            val detailIntent = Intent(context, RestaurantDetailActivity::class.java)
            detailIntent.putExtra(RestaurantListActivity.EXTRA_RESTAURANT, restaurant)

            context.startActivity(detailIntent)
            true
        }

        when  {
            // low
            restaurant.rating < 3 -> {
                holder.textViewRating.setTextColor(
                    context.resources.getColor(
                        R.color.oneToThree,
                        context.theme
                    )
                )
                holder.textViewName.setTextColor(
                    context.resources.getColor(
                        R.color.oneToThree,
                        context.theme
                    )
                )
            }
            restaurant.rating >= 3 && restaurant.rating <= 5 -> {
                holder.textViewRating.setTextColor(
                    context.resources.getColor(
                        R.color.threeToFive,
                        context.theme
                    )
                )
                holder.textViewName.setTextColor(
                    context.resources.getColor(
                        R.color.threeToFive,
                        context.theme
                    )
                )
            }
            // substantial
            restaurant.rating > 5 && restaurant.rating <= 7 -> {
                holder.textViewRating.setTextColor(
                    context.resources.getColor(
                        R.color.fiveToSeven,
                        context.theme
                    )
                )
                holder.textViewName.setTextColor(
                    context.resources.getColor(
                        R.color.fiveToSeven,
                        context.theme
                    )
                )
            }
            // high
            restaurant.rating <= 10 -> {
                holder.textViewRating.setTextColor(
                    context.resources.getColor(
                        R.color.SevenToTen,
                        context.theme
                    )
                )
                holder.textViewName.setTextColor(
                    context.resources.getColor(
                        R.color.SevenToTen,
                        context.theme
                    )
                )
            }
        }

    }

    private fun deleteFromBackendless(position: Int) {
        val restaurant = restaurantList[position]
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete $restaurant")
        Backendless.Data.of(Restaurant::class.java).remove(restaurant,
            object: AsyncCallback<Long?> {
            override fun handleResponse(response: Long?) {
                restaurantList.removeAt(position)
                notifyDataSetChanged()
            }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("Restaurant List", "handleFault: ${fault.message}")
                }
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount(): Int{
//        return loanList.size
//    }
    override fun getItemCount() = restaurantList.size

}