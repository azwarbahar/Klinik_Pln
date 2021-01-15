package com.example.klinik_pln.klinik;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.activity.TambahKlinikActivity;
import com.example.klinik_pln.api.ApiRequestKlinik;
import com.example.klinik_pln.api.RetroServerKlinik;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KlinikAdminActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private View dialogView;
    private ProgressDialog pd;
    private List<KlinikModel> klinikModelList;
    private RecyclerView rv_klinik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_admin);

        Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        rv_klinik = findViewById(R.id.rv_klinik);


        pd = new ProgressDialog(this);

        FloatingActionButton fb_klinik = findViewById(R.id.fb_klinik);

        ReaddataKlinik();

        fb_klinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(KlinikAdminActivity.this, TambahKlinikActivity.class));

//                DialogForm();
            }
        });


    }

    public void ReaddataKlinik() {

        pd.setMessage("Proses ... ");
        pd.setCancelable(false);
        pd.show();

        ApiRequestKlinik apiRequestKlinik = RetroServerKlinik.getClient().create(ApiRequestKlinik.class);
        Call<ResponsModelTambah> getdataklinik = apiRequestKlinik.getdataKlinik();
        getdataklinik.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pd.hide();
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    klinikModelList = response.body().getResult();
                    rv_klinik.setLayoutManager(new LinearLayoutManager(KlinikAdminActivity.this));
                    adapter = new KlinikAdapter(KlinikAdminActivity.this, klinikModelList);
                    rv_klinik.setAdapter(adapter);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pd.hide();
            }
        });


    }

//    @SuppressLint("InflateParams")
//    private void DialogForm() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(KlinikAdminActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        dialogView = inflater.inflate(R.layout.input_dialog_klinik, null);
//        dialog.setView(dialogView);
//        dialog.setCancelable(true);
//        dialog.setTitle("Tambah Klinik");
//
//
//        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(final DialogInterface dialog, int which) {
//
//                pd.setMessage("Proses ... ");
//                pd.setCancelable(false);
//                pd.show();
//
//                EditText url_klinik = dialogView.findViewById(R.id.url_klinik);
//                EditText namaklinik = dialogView.findViewById(R.id.input_nama_klinik);
//
//
//                ApiRequestKlinik apiRequestKlinik = RetroServerKlinik.getClient().create(ApiRequestKlinik.class);
//                Call<ResponsModelTambah> sendklinik = apiRequestKlinik.sendData(namaklinik.getText().toString(), url_klinik.getText().toString(),"","");
//                sendklinik.enqueue(new Callback<ResponsModelTambah>() {
//                    @Override
//                    public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
//                        pd.hide();
//                        Log.d("RETRO", "response : " + response.body().toString());
//                        String kode = response.body().getKode();
//
//                        if (kode.equals("1")) {
//                            Toast.makeText(KlinikAdminActivity.this, "Berhasil Menambahkan", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                            ReaddataKlinik();
//                        } else {
//                            Toast.makeText(KlinikAdminActivity.this, "Data Error, Tidak Berhasil Menambahkan", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                            ReaddataKlinik();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
//                        pd.hide();
//                        dialog.dismiss();
//                        ReaddataKlinik();
//                    }
//                });
//
//
//                dialog.dismiss();
//            }
//        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                ReaddataKlinik();
//            }
//        });
//
//        dialog.show();
//
//
//    }

    public void setSupportActionBar(Toolbar mTopToolbar) {
    }
}
