package com.madlab.googlemapdemo.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyLocations(lat: Double, lng: Double, title: String, snippet: String) : ClusterItem {

    var mPosition: LatLng = LatLng(lat, lng)
    var mTitle: String = title
    var mSnippet: String = snippet

    override fun getSnippet(): String = mSnippet
    override fun getTitle(): String = mTitle
    override fun getPosition(): LatLng = mPosition

}