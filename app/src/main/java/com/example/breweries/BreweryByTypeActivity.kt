package com.example.calatour

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calatour.adapters.BreweriesAdapter
import com.example.calatour.models.BreweryByCity

class BreweryByTypeActivity  : AppCompatActivity() {

   private lateinit var breweriesListView: ListView
    var breweries = mutableListOf<BreweryByCity>()

   lateinit var adapter: BreweriesAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brewery_by_type)

        val ss:String = intent.getStringExtra("type").toString()

        this.setTitle(intent.getStringExtra("type").toString())
        findViewById<TextView>(R.id.brewery_by_type).text = ss
        val breweriesByType = intent.getSerializableExtra("breweries_by_type") as ArrayList<BreweryByCity>

        breweries = breweriesByType

        breweriesListView = findViewById<ListView>(R.id.brewery_by_type_list)

        adapter = BreweriesAdapter(this@BreweryByTypeActivity, breweries)
        breweriesListView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        getMenuInflater().inflate (R.menu.brewery_by_type_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_type) {

            onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        var result = findViewById<TextView>(R.id.brewery_by_type).text.toString()
        returnIntent.putExtra("type", result)
        Log.e("@@@", result)
        setResult(Activity.RESULT_OK, returnIntent)
        super.onBackPressed()
    }
}