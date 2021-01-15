package com.example.klinik_pln.histrory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.activity.CekDokterActivity;
import com.example.klinik_pln.activity.RegisterActivity;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.example.klinik_pln.model.getDataAntrianOneModel;
import com.google.android.gms.common.api.Api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPasienActivity extends AppCompatActivity {

    private String tanggal;
    private String tanggal_db;
    private String nama;
    private String kode_antrian;
    private String jam_antrian;
    private String status_antrian;
    private String kode_db;

    private TextView tv_no_antrian;
    private TextView tv_jam;
    private TextView tv_status;
    private TextView tv_kosong;

    private RelativeLayout rl_hari_ini;

    private RecyclerView rv_riwayat;

    private ArrayList<getDataAntrianOneModel> oneModels;

    private View dialogView;
    private AlertDialog.Builder dialog;

    private SharedPreferences mPreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pasien);

        rv_riwayat = findViewById(R.id.rv_riwayat);
        tv_kosong = findViewById(R.id.tv_kosong);
        tv_no_antrian = findViewById(R.id.tv_no_antrian);
        tv_jam = findViewById(R.id.tv_jam);
        tv_status = findViewById(R.id.tv_status);
        rl_hari_ini = findViewById(R.id.rl_hari_ini);
        tv_kosong.setVisibility(View.GONE);
        mPreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        nama = mPreferences.getString("NAMA", "");

        rl_hari_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (status_antrian.equals("Tunggu")) {

                    dialog = new AlertDialog.Builder(HistoryPasienActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    dialogView = inflater.inflate(R.layout.dialog_jekel, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(true);
                    final AlertDialog show = dialog.show();

                    TextView tv_laki = dialogView.findViewById(R.id.tv_laki);
                    tv_laki.setText("Tunda Antrian");
                    TextView tv_perempuan = dialogView.findViewById(R.id.tv_perempuan);
                    tv_perempuan.setText("Batalkan Antrian");
                    tv_laki.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Penundahan")
                                    .setContentText("Ingin Menunda Antrian ?")
                                    .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                        }
                                    })
                                    .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                            setTundaAntrian();
                                        }
                                    })
                                    .show();
                        }
                    });

                    tv_perempuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Pembatalan")
                                    .setContentText("Ingin Membatalkan Antrian ?")
                                    .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                        }
                                    })
                                    .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                            setBatalAntrian();
                                        }
                                    })
                                    .show();
                        }
                    });


                } else {

                    dialog = new AlertDialog.Builder(HistoryPasienActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    dialogView = inflater.inflate(R.layout.dialog_jekel, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(true);
                    final AlertDialog show = dialog.show();

                    TextView tv_laki = dialogView.findViewById(R.id.tv_laki);
                    tv_laki.setText("Melanjutkan Antrian");
                    TextView tv_perempuan = dialogView.findViewById(R.id.tv_perempuan);
                    tv_perempuan.setText("Batalkan Antrian");
                    tv_laki.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Lanjut")
                                    .setContentText("Ingin Melanjutkan Antrian ?")
                                    .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                        }
                                    })
                                    .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                            setLanjutAntrian();
                                        }
                                    })
                                    .show();
                        }
                    });

                    tv_perempuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Pembatalan")
                                    .setContentText("Ingin Membatalkan Antrian ?")
                                    .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                        }
                                    })
                                    .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            show.dismiss();
                                            setBatalAntrian();
                                        }
                                    })
                                    .show();
                        }
                    });


                }

            }
        });

        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tanggal = dateFormat.format(date);

        cekStatusskali();
        cekStatusskaliTunda();
        getDataRiwayat(nama);

    }

    private void setBatalAntrian() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> responsModelTambahCall = apiRequestAntrian.updateData(kode_db, tanggal_db, "Batal", jam_antrian);
        responsModelTambahCall.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pDialog.dismiss();
                if (response.body().getKode().equals("1")) {
                    new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success..")
                            .setContentText("Berhasil Membatalkan Antrian")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    getDataRiwayat(nama);
                                    cekStatusskali();
                                }
                            })
                            .show();
                } else {

                    new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Mohon Maaf...")
                            .setContentText("Terjadi Kesalahan!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Mohon Maaf...")
                        .setContentText("Terjadi Kesalahan!")
                        .show();
            }
        });

    }

    private void setTundaAntrian() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> responsModelTambahCall = apiRequestAntrian.updateData(kode_db, tanggal_db, "Tunda", jam_antrian);
        responsModelTambahCall.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pDialog.dismiss();
                if (response.body().getKode().equals("1")) {
                    new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success..")
                            .setContentText("Berhasil Menunda Antrian")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    getDataRiwayat(nama);
                                    cekStatusskaliTunda();
                                }
                            })
                            .show();
                } else {

                    new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Mohon Maaf...")
                            .setContentText("Terjadi Kesalahan!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Mohon Maaf...")
                        .setContentText("Terjadi Kesalahan!")
                        .show();
            }
        });

    }

    private void setLanjutAntrian() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> responsModelTambahCall = apiRequestAntrian.updateData(kode_db, tanggal_db, "Tunggu", jam_antrian);
        responsModelTambahCall.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pDialog.dismiss();
                assert response.body() != null;
                if (response.body().getKode().equals("1")) {
                    new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success..")
                            .setContentText("Berhasil Melanjutkan Antrian")
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    getDataRiwayat(nama);
                                    cekStatusskali();
                                }
                            })
                            .show();
                } else {

                    new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Mohon Maaf...")
                            .setContentText("Terjadi Kesalahan!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(HistoryPasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Mohon Maaf...")
                        .setContentText("Terjadi Kesalahan!")
                        .show();
            }
        });

    }

    private void getDataRiwayat(String nama) {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<List<getDataAntrianOneModel>> listCall = apiRequestAntrian.getAntrianRiwayat(nama);
        listCall.enqueue(new Callback<List<getDataAntrianOneModel>>() {
            @Override
            public void onResponse(Call<List<getDataAntrianOneModel>> call, Response<List<getDataAntrianOneModel>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isEmpty()) {
                        tv_kosong.setVisibility(View.VISIBLE);
                        rv_riwayat.setVisibility(View.GONE);
                    } else {
                        tv_kosong.setVisibility(View.GONE);
                        rv_riwayat.setVisibility(View.VISIBLE);
                        oneModels = (ArrayList<getDataAntrianOneModel>) response.body();
                        RiwayatAdapter riwayatAdapter = new RiwayatAdapter(HistoryPasienActivity.this, oneModels);
                        rv_riwayat.setLayoutManager(new LinearLayoutManager(HistoryPasienActivity.this));
                        rv_riwayat.setAdapter(riwayatAdapter);
                    }

                } else {
                    tv_kosong.setVisibility(View.VISIBLE);
                    rv_riwayat.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<getDataAntrianOneModel>> call, Throwable t) {
                tv_kosong.setVisibility(View.VISIBLE);
                rv_riwayat.setVisibility(View.GONE);

            }
        });

    }

    private void cekStatusskali() {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<List<getDataAntrianOneModel>> getantrianstatus = apiRequestAntrian.getSingleData(nama, tanggal, "Tunggu");
        getantrianstatus.enqueue(new Callback<List<getDataAntrianOneModel>>() {
            @Override
            public void onResponse(Call<List<getDataAntrianOneModel>> call, Response<List<getDataAntrianOneModel>> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (!response.body().isEmpty()) {
                        rl_hari_ini.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.body().size(); i++) {
                            kode_db = response.body().get(i).getKode_antrian();
                            jam_antrian = response.body().get(i).getJam_antrian();
                            status_antrian = response.body().get(i).getStatus();
                            tanggal_db = response.body().get(i).getTanggal_antrian();

                            if (jam_antrian.equals("08.00")) {
                                kode_antrian = "PS-" + "01-" + "00" + kode_db;
                            } else if (jam_antrian.equals("11.00")) {
                                kode_antrian = "PS-" + "02-" + "00" + kode_db;
                            } else if (jam_antrian.equals("14.00")) {
                                kode_antrian = "PS-" + "03-" + "00" + kode_db;
                            }

                            tv_no_antrian.setText(kode_antrian);
                            if (status_antrian.equals("Tunggu")) {
                                tv_status.setText("Terjadwal");
                            }
                            tv_jam.setText(jam_antrian);

                        }
                    } else {
                        rl_hari_ini.setVisibility(View.GONE);
                    }
                } else {
                    rl_hari_ini.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<getDataAntrianOneModel>> call, Throwable t) {
                rl_hari_ini.setVisibility(View.GONE);
            }
        });
    }

    private void cekStatusskaliTunda() {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<List<getDataAntrianOneModel>> getantrianstatus = apiRequestAntrian.getSingleData(nama, tanggal, "Tunda");
        getantrianstatus.enqueue(new Callback<List<getDataAntrianOneModel>>() {
            @Override
            public void onResponse(Call<List<getDataAntrianOneModel>> call, Response<List<getDataAntrianOneModel>> response) {
                if (response.isSuccessful()) {

                    assert response.body() != null;
                    if (!response.body().isEmpty()) {
                        rl_hari_ini.setVisibility(View.VISIBLE);
                        for (int i = 0; i < response.body().size(); i++) {
                            kode_db = response.body().get(i).getKode_antrian();
                            jam_antrian = response.body().get(i).getJam_antrian();
                            status_antrian = response.body().get(i).getStatus();
                            tanggal_db = response.body().get(i).getTanggal_antrian();

                            if (jam_antrian.equals("08.00")) {
                                kode_antrian = "PS-" + "01-" + "00" + kode_db;
                            } else if (jam_antrian.equals("11.00")) {
                                kode_antrian = "PS-" + "02-" + "00" + kode_db;
                            } else if (jam_antrian.equals("14.00")) {
                                kode_antrian = "PS-" + "03-" + "00" + kode_db;
                            }

                            tv_no_antrian.setText(kode_antrian);
                            if (status_antrian.equals("Tunda")) {
                                tv_status.setText("Ditunda");
                            }
                            tv_jam.setText(jam_antrian);

                        }
                    } else {
                        rl_hari_ini.setVisibility(View.GONE);
                    }
                } else {
                    rl_hari_ini.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<getDataAntrianOneModel>> call, Throwable t) {
                rl_hari_ini.setVisibility(View.GONE);
            }
        });
    }
}