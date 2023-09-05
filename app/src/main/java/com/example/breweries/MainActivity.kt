package com.example.calatour

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.calatour.adapters.BreweriesAdapter
import com.example.calatour.apis.BreweryAPI
import com.example.calatour.models.BreweryByCity
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener {

    private lateinit var breweriesListView: ListView
    var breweries = mutableListOf<BreweryByCity>()

    lateinit var adapter: BreweriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        breweriesListView = findViewById<ListView>(R.id.brewery_list)

        var searchButton = findViewById<Button>(R.id.search)
        searchButton.setOnClickListener(this)

        breweriesListView.setOnItemClickListener(this)

        breweriesListView.setOnCreateContextMenuListener(this)
    }

    override fun onClick(p0: View?) {
        var city = findViewById<EditText>(R.id.city).text.toString()

        var city_error = findViewById<TextView>(R.id.city_error)
        var auth_message = findViewById<TextView>(R.id.auth_message)

        city_error.text = ""
        auth_message.text = ""

        if(city.isEmpty()) {
            city_error.text = getString(R.string.invalid_username)
        } else if (city.length < 3) {
            city_error.text = getString(R.string.short_username)
        }
        else {

            val breweryAPI = BreweryAPI.createApi()

            breweryAPI.getBreweriesByCity(city)
                .enqueue(object : retrofit2.Callback<List<BreweryByCity>> {
                    override fun onResponse(
                        call: Call<List<BreweryByCity>>,
                        response: Response<List<BreweryByCity>>
                    ) {
                        if (response.isSuccessful) {


                            Log.e("breweries", response.body().toString())
                            breweries = response.body() as MutableList<BreweryByCity>
                            Log.e("@@2", breweries.size.toString())

                            adapter = BreweriesAdapter(this@MainActivity, breweries)
                            breweriesListView.adapter = adapter
                        }
                    }

                    override fun onFailure(call: Call<List<BreweryByCity>>, t: Throwable) {
                        Log.e("Api call failed", t.toString())
                    }

                }

                )
        }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        getMenuInflater().inflate (R.menu.breweries_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var name = findViewById<TextView>(R.id.brewery_list_item_name)
        breweries[p2].isFav = !breweries[p2].isFav
        adapter.notifyDataSetChanged()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (v!!.id == R.id.brewery_list)
        {
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            getMenuInflater().inflate ( R.menu.brewery_list_contextual_menu, menu )
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.display_favourites){
            var breweriesFave =
                breweries.filter { breweryByCity -> breweryByCity.isFav } as MutableList<BreweryByCity>
            adapter = BreweriesAdapter(this@MainActivity, breweriesFave)
            breweriesListView.adapter = adapter


        } else if (item.itemId == R.id.display_not_favourites){
            var breweriesNotFave = breweries.filter{ breweryByCity -> breweryByCity.isFav == false  } as MutableList<BreweryByCity>
            adapter = BreweriesAdapter(this@MainActivity, breweriesNotFave)
            breweriesListView.adapter = adapter

        } else if (item.itemId == R.id.display_all){
            adapter = BreweriesAdapter(this@MainActivity, breweries)
            breweriesListView.adapter = adapter
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        val menuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo

        if (item.itemId == R.id.see_breweries_with_the_same_type) {
            val myIntent = Intent (this, BreweryByTypeActivity::class.java)
            val breweryByCity =adapter.getItem(menuInfo.position) as BreweryByCity

            var breweriesByType =
                breweries.filter { b-> b.brewery_type == breweryByCity.brewery_type} as ArrayList<BreweryByCity>
            myIntent.putExtra("breweries_by_type", breweriesByType)
            myIntent.putExtra("type", breweryByCity.brewery_type)
            startForResult.launch(myIntent)
        }
        return super.onContextItemSelected(item)
    }

    @SuppressLint("SuspiciousIndentation")
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val result_type = result.data?.getStringExtra("type")
                        Log.e("##", result_type!!)
                    var breweriesDelete =
                        breweries.filter { breweryByCity -> breweryByCity.brewery_type != result_type } as MutableList<BreweryByCity>
                    adapter = BreweriesAdapter(this@MainActivity, breweriesDelete)
                    breweriesListView.adapter = adapter
                    adapter.notifyDataSetChanged()
        }

    }

}
