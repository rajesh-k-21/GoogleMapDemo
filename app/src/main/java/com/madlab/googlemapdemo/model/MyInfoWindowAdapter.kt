package com.madlab.googlemapdemo.model

import android.app.Activity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.madlab.googlemapdemo.R


class MyInfoWindowAdapter(val context: Activity?) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View? {
        val infowindow: View =
            context!!.layoutInflater.inflate(R.layout.layout_custom_infowindow, null)

        val textViewTitle = infowindow.findViewById<AppCompatTextView>(R.id.textViewTitle)
        textViewTitle.text = p0!!.title
        val textViewSnippet = infowindow.findViewById<AppCompatTextView>(R.id.textViewSnippet)
        textViewSnippet.text = p0.snippet

        return infowindow
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}