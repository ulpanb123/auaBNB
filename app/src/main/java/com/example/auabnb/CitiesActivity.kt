package com.example.auabnb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.auabnb.databinding.ActivityCitiesBinding
import com.facebook.shimmer.ShimmerFrameLayout

class CitiesActivity : AppCompatActivity() {

    private val TAG: String = "CitiesActivity"
    private lateinit var listView : ListView
    private lateinit var cities : List<City>
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var pb_loader : ProgressBar
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AuaBNB)
        setContentView(R.layout.activity_cities)

        val binding : ActivityCitiesBinding  = DataBindingUtil.setContentView(this, R.layout.activity_cities)

        listView = binding.listView

        swipeRefresh = binding.swipeRefresh

        shimmerFrameLayout = binding.shimmerFrameLayout


        val context: Context = applicationContext

        //initialize Backendless
        Backendless.initApp(context, Consts.APP_ID, Consts.ANDROID_KEY)

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = true
            downloadCities()
        }

        downloadCities()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemThatWasClickedId = item.itemId
        if (itemThatWasClickedId == R.id.menu_refresh) {
            swipeRefresh.isRefreshing = true
            downloadCities()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun downloadCities() {

        Backendless.Data.of(City::class.java).find(object : AsyncCallback<List<City>?> {
            override fun handleFault(fault: BackendlessFault?) {
                if (fault != null) {
                    swipeRefresh.isRefreshing = false
                    Log.i(TAG, "some errors:" + fault.message)
                }
            }

            override fun handleResponse(response: List<City>?) {
                swipeRefresh.isRefreshing = false

                if (response != null) {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    displayCities(response)
                }
            }
        })
        listView.setOnItemClickListener { parent, view, position, id ->
            openCity(position)
        }
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