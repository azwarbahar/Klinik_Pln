package com.example.klinik_pln.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.adapter.LokasiKlinikAdapter;
import com.example.klinik_pln.api.ApiRequestKlinik;
import com.example.klinik_pln.api.RetroServerKlinik;
import com.example.klinik_pln.klinik.KlinikAdapter;
import com.example.klinik_pln.klinik.KlinikAdminActivity;
import com.example.klinik_pln.klinik.KlinikModel;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JalanApotekActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private List<KlinikModel> klinikModelList;
    private RecyclerView rv_apotek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jalan_apotek);

        rv_apotek = findViewById(R.id.rv_apotek);

        getData();

    }

    private void getData() {

        ApiRequestKlinik apiRequestKlinik = RetroServerKlinik.getClient().create(ApiRequestKlinik.class);
        Call<ResponsModelTambah> getdataklinik = apiRequestKlinik.getdataKlinik();
        getdataklinik.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    klinikModelList = response.body().getResult();
                    rv_apotek.setLayoutManager(new LinearLayoutManager(JalanApotekActivity.this));
                    adapter = new LokasiKlinikAdapter(JalanApotekActivity.this, klinikModelList);
                    rv_apotek.setAdapter(adapter);
                } else {
                    Toast.makeText(JalanApotekActivity.this, "Gagal Read Data" + kode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
            }
        });
    }
}
