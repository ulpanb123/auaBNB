package com.example.auabnb

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.UserDictionary.Words.APP_ID
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.auabnb.databinding.ActivityCitiesBinding
import java.text.FieldPosition

class CitiesActivity : AppCompatActivity() {

    private val TAG: String = "CitiesActivity"
    private lateinit var listView : ListView
    private lateinit var cities : List<City>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        val binding : ActivityCitiesBinding  = DataBindingUtil.setContentView(this, R.layout.activity_cities)

        listView = binding.listView


        val context: Context = applicationContext
        Backendless.initApp(context, Consts.APP_ID, Consts.ANDROID_KEY)
        downloadCities()
        listView.setOnItemClickListener { parent, view, position, id ->
            openCity(position)
        }
    }

    private fun downloadCities() {
        Backendless.Data.of(City::class.java).find(object : AsyncCallback<List<City>?> {
            override fun handleFault(fault: BackendlessFault?) {
                if (fault != null) {
                    Log.i(TAG, "some errors:" + fault.message)
                }
            }

            override fun handleResponse(response: List<City>?) {
                if (response != null) {
                    displayCities(response)
                }
            }

        })

    }

    private fun openCity(position: Int) {
        val intent : Intent = Intent(this, ApartmentsActivity::class.java)
        intent.putExtra("city_id", cities[position].getObjectId())
        startActivity(intent)
    }

    private fun displayCities(cities : List<City>) {
        this.cities = cities
        val cityAdapter = CityAdapter(cities, context = applicationContext)
        listView.adapter = cityAdapter
    }


}