package com.example.auabnb

import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.BackendlessDataQuery
import com.backendless.persistence.DataQueryBuilder
import com.backendless.persistence.DataQueryBuilder.create
import com.example.auabnb.databinding.ActivityCitiesBinding
import java.net.URI.create

class ApartmentsActivity : AppCompatActivity()  {

    private val CITY_ID_KEY = "city_id"
    private val TAG: String = "ApartmentsActivity"
    private lateinit var listView : ListView
    private lateinit var cityId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        val binding : ActivityCitiesBinding = setContentView(this, R.layout.activity_cities)
        listView = binding.listView

        cityId = intent.extras?.getString(CITY_ID_KEY, "") ?: cityId

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
                }
            }

            override fun handleResponse(response: List<Apartment>?) {
                if (response != null) {
                    displayApartments(response)
                }
            }

        })

    }

    private fun displayApartments(apartments : List<Apartment>) {
        val apartmentAdapter = ApartmentAdapter(apartments, context = applicationContext)
        listView.adapter = apartmentAdapter
    }
}