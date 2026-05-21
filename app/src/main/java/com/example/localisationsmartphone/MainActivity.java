package com.example.localisation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvInfo;
    private RequestQueue requestQueue;
    private LocationManager locationManager;

    // REMPLACEZ par l'IP de votre ordinateur
    private String insertUrl = "http://192.168.1.143/localisation/createPosition.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = findViewById(R.id.tvInfo);
        requestQueue = Volley.newRequestQueue(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Vérifier et demander les permissions
        if (checkPermissions()) {
            startLocationUpdates();
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE
                }, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            Toast.makeText(this, "Permissions nécessaires", Toast.LENGTH_LONG).show();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000,  // 10 secondes (pour les tests)
                10,     // 10 mètres
                locationListener
        );

        // Alternative avec NETWORK_PROVIDER
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                10000,
                10,
                locationListener
        );

        Toast.makeText(this, "Recherche de position GPS...", Toast.LENGTH_LONG).show();
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            String msg = String.format(Locale.FRANCE,
                    "Latitude: %.6f\nLongitude: %.6f\nPrécision: %.1f m",
                    latitude, longitude, location.getAccuracy());

            tvInfo.setText(msg);
            Toast.makeText(MainActivity.this, "Position obtenue !", Toast.LENGTH_SHORT).show();

            // Envoi vers le serveur
            sendPositionToServer(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            String statusText;
            switch (status) {
                case LocationProvider.AVAILABLE:
                    statusText = "DISPONIBLE";
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    statusText = "TEMPORAIREMENT INDISPONIBLE";
                    break;
                default:
                    statusText = "HORS SERVICE";
            }
            Toast.makeText(MainActivity.this, provider + " : " + statusText, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, provider + " activé", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, provider + " désactivé", Toast.LENGTH_SHORT).show();
        }
    };

    private void sendPositionToServer(double latitude, double longitude) {
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl,
                response -> Toast.makeText(MainActivity.this, "Envoyé !", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(MainActivity.this, "Erreur: " + error.getMessage(), Toast.LENGTH_LONG).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
                params.put("date_position", sdf.format(new Date()));

                // Récupération IMEI
                String imei = "unknown";
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                        == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    if (tm != null) {
                        imei = tm.getDeviceId();
                        if (imei == null) imei = "no_imei";
                    }
                }
                params.put("imei", imei);

                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}