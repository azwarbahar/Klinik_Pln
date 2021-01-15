package com.example.klinik_pln.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestKlinik;
import com.example.klinik_pln.api.RetroServerKlinik;
import com.example.klinik_pln.klinik.KlinikAdminActivity;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKlinikActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;

    private ProgressDialog pd;
    private TextView tv_alamat;
    private EditText input_nama;

    String latitud, longitud;
    private String alamat_latlig;
    private Button btn_simpan;
    private Button btn_batal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_klinik);

        androidx.appcompat.widget.Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps_titik);
        mapFragment.getMapAsync(this);

        pd = new ProgressDialog(this);

        tv_alamat = findViewById(R.id.tv_alamat);
        input_nama = findViewById(R.id.input_nama);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String alamat = tv_alamat.getText().toString().trim();
//                String nama = input_nama.getText().toString().trim();
                if (TextUtils.isEmpty(input_nama.getText())) {
                    Toast.makeText(TambahKlinikActivity.this, "Lengkapi Nama Klinik", Toast.LENGTH_SHORT).show();
                } else if (tv_alamat.getText().equals("Alamat Jalan")) {
                    Toast.makeText(TambahKlinikActivity.this, "Lengkapi Lokasi", Toast.LENGTH_SHORT).show();
                } else {
                    kirimData();
                }
            }
        });


    }

    private void kirimData() {

        ApiRequestKlinik apiRequestKlinik = RetroServerKlinik.getClient().create(ApiRequestKlinik.class);
        Call<ResponsModelTambah> sendklinik = apiRequestKlinik.sendData(input_nama.getText().toString(), tv_alamat.getText().toString(),latitud,longitud);
        sendklinik.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pd.hide();
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();

                if (kode.equals("1")) {
                    clear();
                    Toast.makeText(TambahKlinikActivity.this, "Berhasil Menambahkan", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(TambahKlinikActivity.this, "Data Error, Tidak Berhasil Menambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pd.hide();
                Toast.makeText(TambahKlinikActivity.this, "Gagal Menambahkan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void clear() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps_titik);
        mapFragment.getMapAsync(this);
        map.clear();
        input_nama.setText("");
    }

    private void getAddress(double latitud, double longitud) {

        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addressList = geocoder.getFromLocation(latitud, longitud, 1);
            String address = addressList.get(0).getAddressLine(0);
            alamat_latlig = address;
            tv_alamat.setText(address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng latLngzoom = new LatLng(-5.157265, 119.436625);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 12));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Titik Lokasi Klinik");
                getAddress(latLng.latitude, latLng.longitude);
                latitud = String.valueOf(latLng.latitude);
                longitud = String.valueOf(latLng.longitude);
                map.clear();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                map.addMarker(markerOptions);
            }
        });

    }
}