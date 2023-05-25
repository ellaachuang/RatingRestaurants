package com.example.loginandregistration

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

class LoanAdapter(var loanList: MutableList<Loan>) :
    RecyclerView.Adapter<LoanAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewAmount: TextView
        val layout: ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            textViewName = view.findViewById(R.id.loan_name_textView)
            textViewAmount = view.findViewById(R.id.loan_amount_textView)
            layout = view.findViewById(R.id.layout_LoanItem)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)

        return ViewHolder(view)
//        val holder = ViewHolder(view)
//        return holder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val loan = loanList[position]
        val context = holder.textViewName.context
        holder.textViewName.text = loan.name
        holder.textViewAmount.text = String.format("$%.2f", loan.amount-loan.amountRepaid)
        holder.layout.isLongClickable = true

        holder.layout.setOnClickListener{
            val popMenu = PopupMenu(context, holder.textViewName)
            popMenu.inflate(R.menu.menu_loan_detail)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_item_loan_detail_delete -> {
                        deleteFromBackendless(position)
                        true
                    }
                    R.id.menu_item_loan_detail_edit -> {
                        val detailIntent = Intent(context, LoanDetailActivity::class.java)
                        detailIntent.putExtra(LoanListActivity.EXTRA_LOAN, loan)

                        context.startActivity(detailIntent)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }
    }

    private fun deleteFromBackendless(position: Int) {
        val loan = loanList[position]
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete $loan")
        Backendless.Data.of(Loan::class.java).remove(loan,
            object: AsyncCallback<Long?> {
            override fun handleResponse(response: Long?) {
                loanList.removeAt(position)
                notifyDataSetChanged()
            }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("LoanAdapater", "handleFault: ${fault.message}")
                }
        })
    }

    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount(): Int{
//        return loanList.size
//    }
    override fun getItemCount() = loanList.size

}