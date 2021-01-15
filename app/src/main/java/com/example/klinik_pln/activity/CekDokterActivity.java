package com.example.klinik_pln.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.ApiRequestDoketr;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.api.RetroServerDokter;
import com.example.klinik_pln.model.AntrianLastDoneModel;
import com.example.klinik_pln.model.DokterModel;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CekDokterActivity extends AppCompatActivity {

//    private RecyclerView rv_dokter;

    private List<DokterModel> modelList;
    private ProgressDialog pd;
    private RecyclerView.Adapter adapter;

    private RelativeLayout cv1;
    private RelativeLayout cv2;
    private RelativeLayout cv3;
    private RelativeLayout container_dialog;

    private ImageView img_close;
    private TextView tv_nama_dokter;
    private TextView tv_jam;
    private TextView tv_spesialis;
    private TextView tv_absen;

    private TextView tv_btn_ambil;

    //variabel realtime
    private String nol = "";
    private String kode = "PS";
    private String nomor_tampil = "";
    private String nomor_final = "";
    private String antrian_terakhir;

    private String tanggal_now;
    private String nama;

    private CardView btn_ambil_antrian;
    String jam_respon;
    String absen_dokter;

    private List<AntrianLastDoneModel> antrianLastDoneModels;
    private ArrayList<DokterModel> dokterModels;

    private SharedPreferences mPreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_dokter);

        androidx.appcompat.widget.Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        mPreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nama = mPreferences.getString("NAMA", "");

        tv_btn_ambil = findViewById(R.id.tv_btn_ambil);
        btn_ambil_antrian = findViewById(R.id.btn_ambil_antrian);
        tv_nama_dokter = findViewById(R.id.tv_nama_dokter);
        tv_spesialis = findViewById(R.id.tv_spesialis);
        tv_absen = findViewById(R.id.tv_absen);
        tv_jam = findViewById(R.id.tv_jam);
        img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container_dialog.setVisibility(View.GONE);
            }
        });
        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);
        container_dialog = findViewById(R.id.container_dialog);
        container_dialog.setVisibility(View.GONE);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOpen("08.00");
            }
        });

        btn_ambil_antrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilNomor();
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOpen("11.00");
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOpen("14.00");
            }
        });

        pd = new ProgressDialog(this);

        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tanggal_now = dateFormat.format(date);

        cekStatus();

    }

    private void cekStatus() {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<List<getDataAntrianOneModel>> getantrianstatus = apiRequestAntrian.getSingleData(nama, tanggal_now, "Tunggu");
        getantrianstatus.enqueue(new Callback<List<getDataAntrianOneModel>>() {
            @Override
            public void onResponse(Call<List<getDataAntrianOneModel>> call, Response<List<getDataAntrianOneModel>> response) {

                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (!response.body().isEmpty()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            btn_ambil_antrian.setEnabled(false);
                            tv_btn_ambil.setText("Sedang Mengantri");
                        }
                    } else {
                        btn_ambil_antrian.setEnabled(true);
                        tv_btn_ambil.setText("Ambil Antrian");
                    }
                }

            }

            @Override
            public void onFailure(Call<List<getDataAntrianOneModel>> call, Throwable t) {

            }
        });
    }

    private void ambilNomor() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Antrian")
                .setContentText("Ingin Mengambil Antrian ?")
                .setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        pDialog.dismiss();
                    }
                })
                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        pDialog.show();
                        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
                        Call<ResponsModelTambah> sendAntrian = apiRequestAntrian.sendDataAntrian(antrian_terakhir, nama, tanggal_now, jam_respon, "Tunggu");
                        sendAntrian.enqueue(new Callback<ResponsModelTambah>() {
                            @Override
                            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {

                                Log.d("RETRO", "response : " + response.body().toString());
                                String kode_pest = response.body().getKode();
                                if (kode_pest.equals("1")) {
                                    new SweetAlertDialog(CekDokterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success..")
                                            .setContentText("Berhasil Mengambil Antrian")
                                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                    pDialog.dismiss();
                                                    //berhasil
                                                    finish();
                                                }
                                            })
                                            .show();
                                } else {

                                    new SweetAlertDialog(CekDokterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Mohon Maaf...")
                                            .setContentText("Terjadi Kesalahan!")
                                            .show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                                pDialog.dismiss();
                                new SweetAlertDialog(CekDokterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Permintaan Gagal, Terjadi Kesalahan")
                                        .show();
                            }
                        });
                    }
                })
                .show();
    }

    private void startOpen(final String jam) {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> getdatalasDone = apiRequestAntrian.getdatalastDone(jam, tanggal_now);
        getdatalasDone.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                String kode = response.body().getKode();
                if (kode.equals("1")) {
                    antrianLastDoneModels = response.body().getResultLastDone();
                    if (antrianLastDoneModels.size() < 1) {
                        antrian_terakhir = "0";
                    } else {
                        antrian_terakhir = antrianLastDoneModels.get(0).getKode_antrian();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
//                Toast.makeText(CekDokterActivity.this, "gagal", Toast.LENGTH_SHORT).show();
            }
        });


        ApiRequestDoketr apiRequestDoketr = RetroServerDokter.getClient().create(ApiRequestDoketr.class);
        Call<List<DokterModel>> getdataDokterJam = apiRequestDoketr.getdataDokterJam(jam);
        getdataDokterJam.enqueue(new Callback<List<DokterModel>>() {
            @Override
            public void onResponse(Call<List<DokterModel>> call, Response<List<DokterModel>> response) {
                if (response.isSuccessful()) {
                    if (!response.body().isEmpty()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            tv_nama_dokter.setText(response.body().get(i).getNama_lengkap());
                            tv_spesialis.setText(response.body().get(i).getSpesialis());
                            jam_respon = response.body().get(i).getJam_mulai();
                            absen_dokter = response.body().get(i).getAbsen();
                            if (jam_respon.equals("08.00")) {
                                tv_jam.setText("08.00 s/d 10.00 WITA");
                            } else if (jam_respon.equals("11.00")) {
                                tv_jam.setText("11.00 s/d 13.00 WITA");
                            } else if (jam_respon.equals("14.00")) {
                                tv_jam.setText("14.00 s/d 16.00 WITA");
                            }

                            if (absen_dokter.equals("Libur")) {
                                tv_absen.setText("Offline");
                                if (tv_btn_ambil.getText().equals("Sedang Mengantri")) {
                                    btn_ambil_antrian.setEnabled(false);
                                    tv_btn_ambil.setText("Sedang Mengantri");
                                } else {
                                    btn_ambil_antrian.setEnabled(false);
                                    tv_btn_ambil.setText("Sedang Offline");
                                }
                                tv_absen.setTextColor(getResources().getColor(R.color.merah));
                            } else if (absen_dokter.equals("Hadir")) {
                                tv_absen.setText("Online");
                                if (tv_btn_ambil.getText().equals("Sedang Mengantri")) {
                                    btn_ambil_antrian.setEnabled(false);
                                    tv_btn_ambil.setText("Sedang Mengantri");
                                } else {
                                    btn_ambil_antrian.setEnabled(true);
                                    tv_btn_ambil.setText("Ambil Antrian");
                                }
                                tv_absen.setTextColor(getResources().getColor(R.color.hijau));
                            }

                        }
                        container_dialog.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(CekDokterActivity.this, "Data Dokter Tidak Tersedia", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DokterModel>> call, Throwable t) {

            }
        });

    }

    private void readDokterabsen() {
//        pd.setMessage("Proses ... ");
//        pd.setCancelable(false);
//        pd.show();
//        ApiRequestDoketr apiRequestDoketr = RetroServerDokter.getClient().create(ApiRequestDoketr.class);
//        Call<ResponsModelTambah> getabsenDokter = apiRequestDoketr.getabsenDokter();
//        getabsenDokter.enqueue(new Callback<ResponsModelTambah>() {
//            @Override
//            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
//                pd.hide();
//                String kode = response.body().getKode();
//                if (kode.equals("1")){
//                    modelList = response.body().getResultAbsenDokter();
//                    rv_dokter.setLayoutManager(new LinearLayoutManager(CekDokterActivity.this));
//                    adapter = new AbsenDokterAdapter(CekDokterActivity.this, modelList);
//                    rv_dokter.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
//                pd.hide();
//            }
//        });
    }
}
