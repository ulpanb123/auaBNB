package com.example.auabnb

import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.BackendlessDataQuery
import com.backendless.persistence.DataQueryBuilder
import com.backendless.persistence.DataQueryBuilder.create
import com.example.auabnb.databinding.ActivityApartmentsBinding
import com.example.auabnb.databinding.ActivityCitiesBinding
import java.net.URI.create

class ApartmentsActivity : AppCompatActivity()  {

    private val CITY_ID_KEY = "city_id"
    private val TAG: String = "ApartmentsActivity"
    private lateinit var listView : ListView
    private lateinit var cityId : String
    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AuaBNB)
        setContentView(R.layout.activity_cities)

        val binding : ActivityApartmentsBinding = setContentView(this, R.layout.activity_apartments)
        listView = binding.listViewApartments
        swipeRefresh = binding.apartmentsSwipeRefresh

        cityId = intent.extras?.getString(CITY_ID_KEY, "") ?: cityId

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            downloadApartments(cityId)
        }

        downloadApartments(cityId)

    }

    private fun downloadApartments(cityId : String) {
        val whereClause = "city.objectId = '$cityId'"
        val queryBuilder = create()
        queryBuilder.whereClause = whereClause


        Backendless.Persistence.of(Apartment::class.java).find(queryBuilder, object : AsyncCallback<List<Apartment>?> {
            override fun handleFault(fault: BackendlessFault?) {
                if (fault != null) {
                    Log.i(TAG, "some errors:" + fault.message)
                    swipeRefresh.isRefreshing = false
                }
            }

            override fun handleResponse(response: List<Apartment>?) {
                swipeRefresh.isRefreshing = false
                if (response != null) {
                    displayApartments(response)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemClickedId= item.itemId
        if(itemClickedId == R.id.menu_refresh) {
            swipeRefresh.isRefreshing = true
            downloadApartments(cityId)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayApartments(apartments : List<Apartment>) {
        val apartmentAdapter = ApartmentAdapter(apartments, context = applicationContext)
        listView.adapter = apartmentAdapter
    }
}