package com.example.loginandregistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.loginandregistration.databinding.ActivityLoanListBinding

class LoanListActivity : AppCompatActivity() {

    companion object {
        val TAG = "LoanListActivity"
        val EXTRA_USER_ID = "userId"
        val EXTRA_LOAN = "loan"
    }

    private lateinit var binding: ActivityLoanListBinding
    lateinit var adapter: LoanAdapter
    lateinit var loanList : ArrayList<Loan>
    lateinit var userId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.fabLoanListCreateNewLoan.setOnClickListener{
            val loanDetailIntent = Intent(this,LoanDetailActivity::class.java).apply{
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

    private fun retrieveAllData(userId: String) {
        val whereClause = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Loan::class.java).find(queryBuilder, object :
            AsyncCallback<List<Loan?>?> {
            override fun handleResponse(foundLoans: List<Loan?>?) {
                Log.d(LoginActivity.TAG, "handleResponse: $foundLoans")
                adapter = LoanAdapter(foundLoans as MutableList<Loan>)
                binding.loanListRecyclerView.adapter = adapter
                binding.loanListRecyclerView.layoutManager = LinearLayoutManager(this@LoanListActivity)
            }
            override fun handleFault(fault: BackendlessFault) {
                Log.d(LoginActivity.TAG, "handleFault: ${fault.message}")
            }
        })
    }
}