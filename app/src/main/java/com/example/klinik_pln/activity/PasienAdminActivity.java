package com.example.klinik_pln.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.adapter.PasienListAdapter;
import com.example.klinik_pln.api.ApiRequestUser;
import com.example.klinik_pln.api.RetroServerUser;
import com.example.klinik_pln.klinik.KlinikAdminActivity;
import com.example.klinik_pln.klinik.KlinikModel;
import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasienAdminActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private View dialogView;
    private ProgressDialog pd;
    private List<GetDataUserModel> getDataUserModels;


    private RecyclerView rv_pasien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_admin);


        androidx.appcompat.widget.Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);
        rv_pasien = findViewById(R.id.rv_pasien);

        pd = new ProgressDialog(this);

        ReadPasien();

    }

    private void ReadPasien() {
        pd.setMessage("Proses ... ");
        pd.setCancelable(false);
        pd.show();

        String level_user = "pasien";

        ApiRequestUser apiRequestUser = RetroServerUser.getClient().create(ApiRequestUser.class);
        Call<ResponsModelTambah> getpasien = apiRequestUser.getdatapasien(level_user);
        getpasien.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pd.hide();

                pd.hide();
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    getDataUserModels = response.body().getResultpasien();
                    rv_pasien.setLayoutManager(new LinearLayoutManager(PasienAdminActivity.this));
                    adapter = new PasienListAdapter(PasienAdminActivity.this, getDataUserModels);
                    rv_pasien.setAdapter(adapter);
                } else {
                    Toast.makeText(PasienAdminActivity.this, "Gagal Read Data" + kode,
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pd.hide();
            }
        });

    }
}
