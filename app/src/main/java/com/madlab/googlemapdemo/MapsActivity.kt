package com.madlab.googlemapdemo


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.madlab.googlemapdemo.model.LocationData
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var mMap: GoogleMap? = null
    lateinit var location: LocationData

    //lateinit var clusterManager: ClusterManager<MyLocations>
    lateinit var liveLocations: LatLng

    private var circleRadius = 0
    private var isMoving = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        location = intent.getParcelableExtra("data")!!
        liveLocations = LatLng(location.latitude, location.longitude)

    }

    private fun moveMapCamera() {
        if (mMap == null) {
            return
        }
        val center = CameraUpdateFactory.newLatLng(liveLocations)
        val zoom = CameraUpdateFactory.zoomTo(15f)
        mMap!!.moveCamera(center)
        mMap!!.animateCamera(zoom)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        /*    *//*  val cameraPosition = CameraPosition.Builder()
              .target(liveLocations)
              .zoom(17f).build()
          mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))*//*

        val marker = mMap!!.addMarker(
            MarkerOptions().position(liveLocations).title("You are here").snippet("$liveLocations")
        )

        val myInfoWindowAdapter = MyInfoWindowAdapter(this)
        mMap!!.setInfoWindowAdapter(myInfoWindowAdapter)
        marker.showInfoWindow()
        // set  UpCluster()*/


        mMap!!.setOnCameraMoveStartedListener {
            isMoving = true
            textView.visibility = View.GONE
            profile_loader.visibility = View.GONE

            val drawable: Drawable =
                applicationContext.resources.getDrawable(
                    R.drawable.circle_background_moving,
                    null
                )

            frameLayoutPinViewCircle.background = drawable
            resizeLayout(false)
        }

        mMap!!.setOnCameraIdleListener {
            isMoving = false;
            textView.visibility = View.INVISIBLE;
            profile_loader.visibility = View.VISIBLE;
            resizeLayout(true)


            val drawable: Drawable =
                applicationContext.resources.getDrawable(
                    R.drawable.circle_background_moving,
                    null
                )

            frameLayoutPinViewCircle.background = drawable


            Handler().postDelayed(
                {
                    if (!isMoving) {
                        frameLayoutPinViewCircle.background = drawable
                        textView.visibility = View.VISIBLE;
                        profile_loader.visibility = View.GONE;
                    }
                }, 1000
            )
        }
        MapsInitializer.initialize(this)
        moveMapCamera()
    }

    private fun resizeLayout(backToNormalSize: Boolean) {
        val params =
            frameLayoutPinViewCircle.layoutParams as FrameLayout.LayoutParams
        val vto: ViewTreeObserver = frameLayoutPinViewCircle.viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                frameLayoutPinViewCircle.viewTreeObserver.removeGlobalOnLayoutListener(this)
                circleRadius = frameLayoutPinViewCircle.measuredWidth
            }
        })
        if (backToNormalSize) {
            params.width = WRAP_CONTENT
            params.height = WRAP_CONTENT
            params.topMargin = 0
        } else {
            params.topMargin = (circleRadius * 0.3).toInt()
            params.height = circleRadius - circleRadius / 3
            params.width = circleRadius - circleRadius / 3
        }
        frameLayoutPinViewCircle.layoutParams = params
    }

    /*private fun addItems() {
       var lat = location.latitude + 0.1
       var lng = location.longitude + 0.1

       for (i in 0..9) {
           val offset = i / 70.0
           lat += offset
           lng += offset
           val offsetItem = MyLocations(lat, lng, "your mark", "lat:- $lat lng :- $lng ")
           clusterManager.addItem(offsetItem)
       }
   }*/
    /* private fun setUpCluster() {
         val liveLocation = LatLng(location.latitude, location.longitude)
         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(liveLocation, 10F))

         clusterManager = ClusterManager<MyLocations>(this, mMap)
         mMap.setOnCameraIdleListener(clusterManager)
         mMap.setOnMarkerClickListener(clusterManager)
         clusterManager.setAnimation(true)
         addItems()
     }*/
    /*  private fun moveToLocation(latLng: LatLng) {
          val cameraPosition = CameraPosition.Builder()
              .target(latLng)
              .zoom(17f).build()
          mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
      }*/
}