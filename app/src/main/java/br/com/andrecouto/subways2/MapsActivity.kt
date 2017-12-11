package br.com.andrecouto.subways2

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.location.LocationServices
import android.os.Looper
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.support.v4.content.ContextCompat
import br.com.andrecouto.easymetro.Utils.AssetsUtils
import br.com.andrecouto.subways2.model.LinhaResponse
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.*
import android.widget.Toast
import android.location.Location.distanceBetween
import br.com.andrecouto.subways2.model.Estacao


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mLocationCallback: LocationCallback
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mLocationRequest: LocationRequest

    private lateinit var linha: LinhaResponse
    private var test = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                for (location in locationResult!!.locations) {
                    var lat = location.latitude
                    var log = location.longitude
                    val sydney = LatLng(lat, log)
                    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    val distance = FloatArray(2)

                    for(estacao in linha.linha.estacoes) {
                        Location.distanceBetween(lat, log,
                                estacao.circle.getCenter().latitude,  estacao.circle.getCenter().longitude, distance)
                        if (distance[0] < estacao.circle.getRadius()) {
                            Toast.makeText(baseContext, estacao.nome, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun init() {
        linha = AssetsUtils.loadLinhaFromString(AssetsUtils.loadJSONFromAsset(this))

        for (estacao in linha.linha.estacoes) {
            var nestacao: Estacao = estacao
            val circle = (CircleOptions()
                    .center(LatLng(estacao.latitude, estacao.longitude))
                    .radius(70.0)
                    .strokeColor(Color.RED))
            estacao.circle = circle
            mMap.addCircle(circle)
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
        }
    }

    override fun onResume() {
        super.onResume()
        mGoogleApiClient.let {
            if (mFusedLocationClient != null) {
                requestLocationUpdates();
            } else {
                buildGoogleApiClient()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMinZoomPreference(6.0f)
        mMap.setMaxZoomPreference(18.0f)
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL)
        mMap.uiSettings.isZoomGesturesEnabled = true
        init()
    }

    override fun onConnected(p0: Bundle?) {
        requestLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Synchronized protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient!!.connect()
    }

    fun requestLocationUpdates() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 20000 // two minute interval
        mLocationRequest.fastestInterval = 20000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
        }
    }
}
