package com.example.klinik_pln.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.klinik_pln.R;
import com.example.klinik_pln.antrian.ItemAntrianAdminActivity;
import com.example.klinik_pln.antrian.antriankedua.AntrianKeduaActivity;
import com.example.klinik_pln.antrian.antrianketiga.AntrianKetigaActivity;
import com.example.klinik_pln.antrian.antrianpertama.AntrianPertamaActivity;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.klinik.KlinikAdminActivity;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String GET_NAMA = "get_nama";
    private String GET_LEVEL = "get_level";
    private String GET_ALAMAT = "get_alamat";
    private String GET_TELPON = "get_telpon";
    private String GET_JEKEL = "get_jekel";
    private String GET_USER = "get_user";
    private String GET_PASS = "get_pass";
    TextView nama_lengkap;
    TextView no_telpon;

    private String status = "Tunggu";
    private TextView tv_jumlah_antrian_1;
    private TextView tv_jumlah_antrian_2;
    private TextView tv_jumlah_antrian_3;
    private List<getDataAntrianOneModel> getDataAntrianOneModels;

    private ImageView img_menu;
    private String tanggal = "";

    private CardView cv_08;
    private CardView cv_11;
    private CardView cv_14;

    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tanggal = dateFormat.format(date);

        cv_08 = findViewById(R.id.cv_08);
        cv_11 = findViewById(R.id.cv_11);
        cv_14 = findViewById(R.id.cv_14);

        cv_08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Antrian")
                        .setContentText("Ingin menuju halaman Antrian ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, AntrianPertamaActivity.class);
                                startActivity(intent);

                            }
                        })
                        .show();
            }
        });

        cv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Antrian")
                        .setContentText("Ingin menuju halaman Antrian ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, AntrianKeduaActivity.class);
                                startActivity(intent);

                            }
                        })
                        .show();

            }
        });

        cv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Antrian")
                        .setContentText("Ingin menuju halaman Antrian ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, AntrianKetigaActivity.class);
                                startActivity(intent);

                            }
                        })
                        .show();

            }
        });

        tv_jumlah_antrian_1 = findViewById(R.id.tv_jumlah_antrian_1);
        tv_jumlah_antrian_2 = findViewById(R.id.tv_jumlah_antrian_2);
        tv_jumlah_antrian_3 = findViewById(R.id.tv_jumlah_antrian_3);
        img_menu = findViewById(R.id.img_menu);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        no_telpon = findViewById(R.id.no_telpon);
        LinearLayout ll_pasien = findViewById(R.id.ll_pasien);
        LinearLayout ll_klinik = findViewById(R.id.ll_klinik);
        LinearLayout ll_dokter = findViewById(R.id.ll_dokter);
        LinearLayout ll_admin = findViewById(R.id.ll_admin);

        ll_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Antrian")
                        .setContentText("Ingin menuju halaman Admin ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, AdminAdminActivity.class);
                                startActivity(intent);

                            }
                        })
                        .show();
            }
        });


        ll_pasien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Pasien")
                        .setContentText("Ingin menuju halaman Pasien ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, PasienAdminActivity.class);
                                startActivity(intent);

                            }
                        })
                        .show();
            }
        });

        ll_dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Dokter")
                        .setContentText("Ingin menuju halaman Dokter ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, DokterAdminActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        ll_klinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Klinik")
                        .setContentText("Ingin menuju halaman Klinik ?")
                        .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(AdminActivity.this, KlinikAdminActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminActivity.this);
                dialog.setTitle("Logout");
                dialog.setMessage("Yakin ingin keluar dari admin ?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                        startActivity(intent);
                        SharedPreferences mPreferences1 = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mPreferences1.edit();
                        editor.apply();
                        editor.clear();
                        editor.commit();
                        finish();
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        mSwipeRefreshLayout = findViewById(R.id.swipe_continer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                getBundle();
                loadData1();
                loadData2();
                loadData3();
            }
        });
        getBundle();
        loadData1();
        loadData2();
        loadData3();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getBundle();
        loadData1();
        loadData2();
        loadData3();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBundle();
        loadData1();
        loadData2();
        loadData3();
    }

    private void loadData1() {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> getDataantrian = apiRequestAntrian.getAllmenunggu(tanggal, "08.00", status);
        getDataantrian.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                mSwipeRefreshLayout.setRefreshing(false);
                assert response.body() != null;
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    getDataAntrianOneModels = response.body().getResultstatusAntrian();
                    if (response.body().getResultstatusAntrian().isEmpty()) {
                        tv_jumlah_antrian_1.setText("0");
                    } else {
                        tv_jumlah_antrian_1.setText(String.valueOf(getDataAntrianOneModels.size()));
                    }
                } else {
                    tv_jumlah_antrian_1.setText("00");
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
                tv_jumlah_antrian_1.setText("000");
            }
        });
    }

    private void loadData2() {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> getDataantrian = apiRequestAntrian.getAllmenunggu(tanggal, "11.00", status);
        getDataantrian.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                assert response.body() != null;
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    getDataAntrianOneModels = response.body().getResultstatusAntrian();
                    if (response.body().getResultstatusAntrian().isEmpty()) {
                        tv_jumlah_antrian_2.setText("0");
                    } else {
                        tv_jumlah_antrian_2.setText(String.valueOf(getDataAntrianOneModels.size()));
                    }
                } else {
                    tv_jumlah_antrian_2.setText("00");
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                tv_jumlah_antrian_2.setText("000");
            }
        });
    }

    private void loadData3() {

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> getDataantrian = apiRequestAntrian.getAllmenunggu(tanggal, "14.00", status);
        getDataantrian.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                assert response.body() != null;
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    getDataAntrianOneModels = response.body().getResultstatusAntrian();
                    if (response.body().getResultstatusAntrian().isEmpty()) {
                        tv_jumlah_antrian_3.setText("0");
                    } else {
                        tv_jumlah_antrian_3.setText(String.valueOf(getDataAntrianOneModels.size()));
                    }
                } else {
                    tv_jumlah_antrian_3.setText("00");
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                tv_jumlah_antrian_3.setText("000");
            }
        });
    }

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        String nama = bundle.getString(GET_NAMA);
        String alamat = bundle.getString(GET_ALAMAT);
        String telpon = bundle.getString(GET_TELPON);
        String jekel = bundle.getString(GET_JEKEL);
        String username = bundle.getString(GET_USER);
        String password = bundle.getString(GET_PASS);
        String level = bundle.getString(GET_LEVEL);

        nama_lengkap.setText(nama);
        no_telpon.setText(telpon);
    }

    @Override
    public void onRefresh() {
        getBundle();
        loadData1();
        loadData2();
        loadData3();
    }
}
