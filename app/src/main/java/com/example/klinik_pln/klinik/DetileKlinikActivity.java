package com.example.klinik_pln.klinik;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestKlinik;
import com.example.klinik_pln.api.RetroServerKlinik;
import com.example.klinik_pln.model.ResponsModelTambah;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetileKlinikActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    private ProgressDialog pd;
    EditText nama_klinik,lokasi_klinik;
    Button batal, simpan;
    private String id_klinik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detile_klinik);


        nama_klinik = findViewById(R.id.nama_klinik);
        lokasi_klinik = findViewById(R.id.lokasi_klinik);
        batal = findViewById(R.id.btn_batal);
        simpan = findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(this);
        ambildata();

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DetileKlinikActivity.this, KlinikAdminActivity.class));
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setMessage("Proses ... ");
                pd.setCancelable(false);
                pd.show();

                String id = id_klinik;
                String nama = nama_klinik.getText().toString();
                String lokasi = lokasi_klinik.getText().toString();
                ApiRequestKlinik apiRequestKlinik = RetroServerKlinik.getClient().create(ApiRequestKlinik.class);
                Call<ResponsModelTambah> updateKlinik = apiRequestKlinik.updateData(id,nama,lokasi);
                updateKlinik.enqueue(new Callback<ResponsModelTambah>() {
                    @Override
                    public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if (kode.equals("1")) {
                            Toast.makeText(DetileKlinikActivity.this, "Berhasil Mengedit" + kode, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(DetileKlinikActivity.this, "Data Error, Tidak Berhasil Mengedit" + kode, Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ResponsModelTambah> call, Throwable t) {

                    }
                });

            }
        });



    }

    private void ambildata(){
        KlinikModel klinikModel = getIntent().getParcelableExtra(EXTRA_DATA);
        if (klinikModel != null){
            id_klinik= klinikModel.getId();
            nama_klinik.setText(klinikModel.getNama());
            lokasi_klinik.setText(klinikModel.getLokasi());

        }
    }
}
