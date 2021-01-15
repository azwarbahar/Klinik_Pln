package com.example.klinik_pln.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.adapter.DokterListAdapter;
import com.example.klinik_pln.adapter.PasienListAdapter;
import com.example.klinik_pln.api.ApiRequestDoketr;
import com.example.klinik_pln.api.ApiRequestUser;
import com.example.klinik_pln.api.RetroServerDokter;
import com.example.klinik_pln.api.RetroServerUser;
import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DokterAdminActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private View dialogView;
    private ProgressDialog pd;
    private List<GetDataUserModel> getDataUserModels;

    private RecyclerView rv_dokter;

    //string to tb_pasien
    private String alamat="-";
    private String jekel="-";
    private String tgl_lahir="-";
    private String level="dokter";

    //string to tb_dokter
    private String spesialis = "-";
    private String absen = "Hadir";

    //deklarasi jam dan tanggal
    private TextView tv_tanggal,tv_jam;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter_admin);

        androidx.appcompat.widget.Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        tv_jam = findViewById(R.id.tv_jam);
        tv_tanggal = findViewById(R.id.tv_tanggal);

        handler.postDelayed(runnable, 1000);

        rv_dokter = findViewById(R.id.rv_dokter);
        FloatingActionButton fb_dokter = findViewById(R.id.fb_dokter);
        pd = new ProgressDialog(this);
        ReadDokter();

        fb_dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            tv_jam.setText(timeFormat.format(date));
            tv_tanggal.setText(dateFormat.format(date));

            handler.postDelayed(this, 1000);
        }
    };


    private void ReadDokter() {
        pd.setMessage("Proses ... ");
        pd.setCancelable(false);
        pd.show();

        String level_user = "dokter";

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
                    rv_dokter.setLayoutManager(new LinearLayoutManager(DokterAdminActivity.this));
                    adapter = new DokterListAdapter(DokterAdminActivity.this, getDataUserModels);
                    rv_dokter.setAdapter(adapter);
                } else {
                    Toast.makeText(DokterAdminActivity.this, "Gagal Read Data" + kode, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pd.hide();
            }
        });

    }

    private void DialogForm() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(DokterAdminActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.input_dialog_dokter, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tambah Dokter");

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pd.setMessage("Proses ..");
                pd.setCancelable(false);
                pd.show();

                String mulai;
                EditText nama_lengkap = dialogView.findViewById(R.id.nama_lengkap);
                EditText no_telpon = dialogView.findViewById(R.id.no_telpon);
                EditText et_username = dialogView.findViewById(R.id.et_username);
                EditText et_password = dialogView.findViewById(R.id.et_password);
                Spinner jam_mulai = dialogView.findViewById(R.id.jam_mulai);

                mulai = jam_mulai.getSelectedItem().toString();

                ApiRequestUser apiRequestUser = RetroServerUser.getClient().create(ApiRequestUser.class);
                Call<ResponsModelTambah> sendDokter = apiRequestUser.sendUser(nama_lengkap.getText().toString(),alamat,no_telpon.getText().toString(),jekel, tgl_lahir,et_username.getText().toString(),et_password.getText().toString(),level);
                sendDokter.enqueue(new Callback<ResponsModelTambah>() {
                    @Override
                    public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                        pd.hide();
                        assert response.body() != null;
                        Log.d("RETRO", "respons : " +response.body().toString());
                        String kode = response.body().getKode();
                        if (kode.equals("1")){

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                        pd.hide();

                    }
                });


                ApiRequestDoketr apiRequestDoketer = RetroServerDokter.getClient().create(ApiRequestDoketr.class);
                Call<ResponsModelTambah> sendDokter2 = apiRequestDoketer.sendDataDokter(nama_lengkap.getText().toString(),spesialis,mulai,absen);
                sendDokter2.enqueue(new Callback<ResponsModelTambah>() {
                    @Override
                    public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                        pd.hide();
                        assert response.body() != null;
                        Log.d("RETRO","Respons : "+response.body().toString());
                        String kode = response.body().getKode();
                        if (kode.equals("1")){
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModelTambah> call, Throwable t) {

                        pd.hide();
                    }
                });

                dialog.dismiss();
                ReadDokter();
            }
        });

        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ReadDokter();
            }
        });

        dialog.show();

    }


}
