package com.example.storyapp.view.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.storyapp.R
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.databinding.ActivityMapsBinding
import com.example.storyapp.view.detail.DetailActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val viewModel by viewModel<MapsViewModel>()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        getToken()
        getStory()

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true


    }
    private fun marker(story:List<ListStoryItem>){
        story.forEach{
            val loc = LatLng(it.lat,it.lon)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(loc)
                    .title(it.name)
                    .alpha(0.7f)
                    .snippet(it.description)
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
            marker?.tag = it
            mMap.setOnInfoWindowClickListener {map->
                val intent = Intent(this@MapsActivity, DetailActivity::class.java)
                intent.putExtra( "id",map.tag as ListStoryItem)
                startActivity(intent)

            }


        }
    }

    private fun getToken(){
        viewModel.getToken().observe(this){token->
            viewModel.getMapsStory(bearer+token)
        }
    }
    private fun getStory(){
        viewModel.story.observe(this){
            marker(it)
        }
    }

    companion object{
        private const val bearer = "Bearer "
    }
}