package com.example.klinik_pln.klinik;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;

import com.example.klinik_pln.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsApotekActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CardView cv_back;
    GoogleMap map;
    LatLng latLngzoom;
    String nama;
    String lokasi;
    String latit;
    String longit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_apotek);

        cv_back = findViewById(R.id.cv_back);
        cv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        KlinikModel klinikModel = getIntent().getParcelableExtra("EXTRA_DATA");
        assert klinikModel != null;
        nama = klinikModel.getNama();
        lokasi = klinikModel.getLokasi();
        latit = klinikModel.getLatitude();
        longit = klinikModel.getLongitude();
        setMaps(klinikModel.getLatitude(), klinikModel.getLongitude());
    }

    private void setMaps(String latitude, String longitude) {
        latLngzoom = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setPadding(0, 150, 0, 0);
        if (latLngzoom != null){
            map.clear();
            googleMap.addMarker(new MarkerOptions().title(nama)
                    .snippet(lokasi)
                    .position(latLngzoom));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 13));
        }

    }
}