package com.example.fampayapp.Activitites

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fampayapp.CardViews.*
import com.example.fampayapp.Model.CardGroup
import com.example.fampayapp.R
import com.example.fampayapp.ViewModel.ViewModel


class StartActivity : AppCompatActivity(), HC3RecylerAdapter.onOptionsSelectListener {
    lateinit var viewModel: ViewModel
    private lateinit var containerLayout: LinearLayout
    private var dismisCard: Boolean = false
    private var remindMeLater: Boolean = false
    /**
    to suppress depreciation warning
    */
    @Suppress("DEPRECATION")
    private lateinit var progressDialog:ProgressDialog
    private lateinit var sharedPref: SharedPreferences
    var shredPrefStr: String = "dismiss"
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        containerLayout = findViewById(R.id.container_linear_layout)
        /**
        display of progress bar before loading data
         */
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("FamPay")
        progressDialog.setMessage("Application is loading, please wait")
        progressDialog.show()
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipe_layout)
        swipeRefreshLayout.setOnRefreshListener {
            progressDialog = ProgressDialog(this@StartActivity)
            progressDialog.setTitle("FamPay")
            progressDialog.setMessage("Application is loading, please wait")
            containerLayout.visibility = View.GONE
                /**
                  refreshing data using loadData  function on slide down
                 */
            swipeRefreshLayout.isRefreshing = false
            loadData()
        }
        sharedPref = this.getSharedPreferences("SharePref", Context.MODE_PRIVATE)
        dismisCard = sharedPref.getBoolean(shredPrefStr, false)
        loadData()
    }

    /**
     loadData() is the main function and fetches all the data coming from api
     */
    private fun loadData() {
        viewModel.getDataFromCard()!!.observe(this, Observer{ response ->
            progressDialog.cancel()
            progressDialog.dismiss()
            containerLayout.visibility = View.VISIBLE
            /**
            if the response from the api is not null we will inflate all the recycler adapters
            in the given api and figma design
             */
                if (response != null) {
                    Log.d("data","response ${response.toString()}")
                    if (!remindMeLater && !dismisCard) {
                        for (cardGroup in response.card_groups) {
                            //HC3 Design Card having reminder and Dismiss functions : ADD SOME MONEY CARD
                            if (cardGroup.design_type == "HC3") {
                                inflateHC3(cardGroup)
                            }
                        }
                    }
                    /**
                      all cards are arranged as the given position in design
                     **/
                    //HC6 card  : SETUP UPI
                    for (cardGroup in response.card_groups) {
                        if (cardGroup.design_type == "HC6") {
                            inflateHC6(cardGroup)
                        }
                    }
                    //HC5 CARD SCROLLABLE CLICKABLE CARDVIEW
                    for (cardGroup in response.card_groups) {
                        if (cardGroup.design_type == "HC5") {
                            inflateHC5(cardGroup)
                        }
                    }
                    //HC9 CARD NOT SCROLLABLE CARD VIEW
                    for (cardGroup in response.card_groups) {
                        if (cardGroup.design_type == "HC9") {
                            inflateHC9(cardGroup)
                        }
                    }
                    //HC1 : TRANSACTION CARDVIEWS
                    for (cardGroup in response.card_groups) {
                        if (cardGroup.design_type == "HC1") {
                            inflateHC1(cardGroup)
                        }
                    }
                } else {
                    /**
                      TO AVOID SHOW IF THEIR IS ANY NETWORK OR SERVER ERROR OF THE APPLICATION
                      IF ANY ERROR OCCURS AUTOMATIC ALERT DIALOG BOX WITH RETRY BUTTON WILL OCCUR
                     */
                    val builder = AlertDialog.Builder(this)
                    //set title for alert dialog
                    builder.setTitle("Error Occurred")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    //performing positive action
                    builder.setPositiveButton("Retry")
                    { DialogInterface, Which ->
                        progressDialog = ProgressDialog(this@StartActivity)
                        progressDialog.setTitle("FamPay")
                        progressDialog.setMessage("Application is loading, please wait")
                        loadData()
                    }
                    // Create the AlertDialog
                    val alertDialog: AlertDialog = builder.create()
                    // Set other dialog properties
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }
            }

        )

    }

    /**
     * SETTING HC1 CARDVIEW WITH LAYOUT AND ADDING RECYCLER VIEW INTO CONTAINER
     * SIMIALR ACTION FOR ALL THE CARDVIEWS PRESENT IN THE API
     */

    private fun inflateHC1(cardGroup: CardGroup) {
        val recyclerView = RecyclerView(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 0)
        recyclerView.layoutParams = params
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val hC1Adapter = HC1RecylerAdapter()
        hC1Adapter.context = this
        hC1Adapter.cards = cardGroup.cards
        recyclerView.adapter = hC1Adapter
        containerLayout.addView(recyclerView)
    }

    private fun inflateHC3(cardGroup: CardGroup) {
        val recyclerView = RecyclerView(this)
        recyclerView.id = R.id.hc3
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 0)
        recyclerView.layoutParams = params
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val hC3Adapter = HC3RecylerAdapter()
        hC3Adapter.context = this
        hC3Adapter.cards = cardGroup.cards
        hC3Adapter.listener = this
        recyclerView.adapter = hC3Adapter
        containerLayout.addView(recyclerView)

    }

    private fun inflateHC5(cardGroup: CardGroup) {
        val recyclerView = RecyclerView(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 0)
        recyclerView.layoutParams = params
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val hC5Adapter = HC5RecylerAdapter()
        hC5Adapter.context = this
        hC5Adapter.cards = cardGroup.cards
        recyclerView.adapter = hC5Adapter
        containerLayout.addView(recyclerView)
    }

    private fun inflateHC9(cardGroup: CardGroup) {
        val recyclerView = RecyclerView(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 0)
        recyclerView.layoutParams = params
        recyclerView.layoutManager =
            LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL,
                false
            )
        val hC9RecyclerAdapter = HC9RecyclerAdapter()
        hC9RecyclerAdapter.context = this
        hC9RecyclerAdapter.cards = cardGroup.cards
        recyclerView.adapter = hC9RecyclerAdapter
        containerLayout.addView(recyclerView)
    }

    private fun inflateHC6(cardGroup: CardGroup) {
        val recyclerView = RecyclerView(this)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 0)
        recyclerView.layoutParams = params
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val hC6Adapter = HC6RecylerAdapter()
        hC6Adapter.context = this
        hC6Adapter.cards = cardGroup.cards
        recyclerView.adapter = hC6Adapter
        containerLayout.addView(recyclerView)
    }

    /**
     * ON REMINDER : HIDES HC3 CARD VIEW  : ONLY WHEN APP IS NOT  DESTROYED
     * STATE IS SAVED IN A SIMPLE VARIABLE
     */
    override fun onRemindLaterSelected() {
        remindMeLater = true
        val recyclerView: RecyclerView = findViewById(R.id.hc3)
        recyclerView.visibility = View.GONE

    }

    /**
     * ON DISMISS : STATE IS SAVED IN SHARED - PREFERENCE AS IT WILL BE HIDDEN EVEN AFTER THE APP IS DESTROYED
     */
    override fun onDismissNowSelected() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(shredPrefStr, true)
        editor.apply()
        dismisCard = true
        val recyclerView: RecyclerView = findViewById(R.id.hc3)
        recyclerView.visibility = View.GONE


    }
}