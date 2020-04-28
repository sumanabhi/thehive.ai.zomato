package com.animusabhi.zomato_search.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.animusabhi.zomato_search.R
import com.animusabhi.zomato_search.adapter.RestaurantListAdapter
import com.animusabhi.zomato_search.model.Restaurants
import com.animusabhi.zomato_search.model.SearchModel
import com.animusabhi.zomato_search.netowrk.APIClient
import com.animusabhi.zomato_search.netowrk.APIInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null;
    private lateinit var searchView: SearchView
    private var apiInterface: APIInterface? = null
    var searchText: String = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var TAG = "@@@@@#######::";
    private var REQUEST_PERMISSIONS_REQUEST_CODE = 111;
    private var mLatitudeLabel: String? = null
    private var mLongitudeLabel: String? = null
    private var mLastLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiInterface = APIClient.client?.create(APIInterface::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //method for initialisation
        init();
    }

    private fun getAPI(
        searchText: String,
        mLatitudeLabel: Double?,
        mLongitudeLabel: Double?
    ) {
        val call: Call<SearchModel> = apiInterface!!.getRestaurantList(
            "city",
            searchText,
            "0",
            mLatitudeLabel!!,
            mLongitudeLabel!!
        )

        call.enqueue(object : Callback<SearchModel?> {
            override fun onFailure(call: Call<SearchModel?>, t: Throwable) {
                Log.e("FAILURE :- ", t.message.toString())
            }

            override fun onResponse(call: Call<SearchModel?>, response: Response<SearchModel?>) {
                if (response.isSuccessful) {
                    val restaurantList: SearchModel? = response.body()
                    val restaurantArrayList: ArrayList<Restaurants> = restaurantList!!.restaurants
                    val restaurantListAdapter =
                        RestaurantListAdapter(this@MainActivity, restaurantArrayList)
                    recyclerView.adapter = restaurantListAdapter
                    recyclerView.visibility = View.VISIBLE
                    textView.visibility = View.GONE
                }
                Log.e("RESPONSE :- ", response.body().toString())
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val searchItem = menu!!.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView
        searchView.isFocusable = false
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {

                //clear the previous data in search arraylist if exist
                searchText = s.toLowerCase().trim().toString()
                getAPI(searchText, mLatitudeLabel!!.toDouble(), mLongitudeLabel!!.toDouble())
                invalidateOptionsMenu()

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Zomato Restaurant"

        recyclerView.layoutManager = LinearLayoutManager(this)


        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
            }
    }

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    //Request permission from user
    private fun requestPermissions() {
        Log.i(TAG, "Inside requestPermissions function")
        val shouldProvideRationale =
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

        //Log an additional rationale to the user. This would happen if the user denied the
        //request previously, but didn't check the "Don't ask again" checkbox.
        // In case you want, you can also show snackbar. Here, we used Log just to clear the concept.
        if (shouldProvideRationale) {
            Log.i(
                TAG,
                "****Inside requestPermissions function when shouldProvideRationale = true"
            )
            startLocationPermissionRequest()
        } else {
            Log.i(
                TAG,
                "****Inside requestPermissions function when shouldProvideRationale = false"
            )
            startLocationPermissionRequest()
        }
    }

    //Start the permission request dialog
    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            Log.i(
                TAG,
                "Inside onStart function; requesting permission when permission is not available"
            )
            requestPermissions()
        } else {
            Log.i(
                TAG,
                "Inside onStart function; getting location when permission is already available"
            )
            getLastLocation()
        }
    }

    /**
     * This method should be called after location permission is granted. It gets the recently available location,
     * In some situations, when location, is not available, it may produce null result.
     * WE used SuppressWarnings annotation to avoid the missing permission warnng. You can comment the annotation
     * and check the behaviour yourself.
     */
    private fun getLastLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    mLatitudeLabel = mLastLocation!!.latitude.toString()
                    mLongitudeLabel = mLastLocation!!.longitude.toString()
                    Log.i(
                        TAG,
                        mLatitudeLabel + "" + mLongitudeLabel
                    )
                } else {
                    Log.i(
                        TAG,
                        "Inside getLocation function. Error while getting location"
                    )
                    println(TAG + task.exception)
                }
            }
    }
}
