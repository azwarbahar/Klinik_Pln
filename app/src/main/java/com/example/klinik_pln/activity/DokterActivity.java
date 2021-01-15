package com.example.klinik_pln.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.adapter.PasienTungguAdapter;
import com.example.klinik_pln.api.ApiRequestAntrian;
import com.example.klinik_pln.api.ApiRequestDoketr;
import com.example.klinik_pln.api.RetroServerAntrian;
import com.example.klinik_pln.api.RetroServerDokter;
import com.example.klinik_pln.model.DokterModel;
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

public class DokterActivity extends AppCompatActivity {
    private String GET_NAMA = "get_nama";
    private String GET_LEVEL = "get_level";
    private String GET_ALAMAT = "get_alamat";
    private String GET_TELPON = "get_telpon";
    private String GET_JEKEL = "get_jekel";
    private String GET_USER = "get_user";
    private String GET_PASS = "get_pass";
    private TextView nama_lengkap, no_telpon;

    private View dialogView;

    String id_dokter = "";
    String nama = "";
    String spesialis = "";
    String jam_mulai = "";
    String absen = "";

    private Button btn_absen;
    private TextView tv_kosong;

    private RecyclerView rv_pasien;
    private String status = "Tunggu";
    private List<getDataAntrianOneModel> getDataAntrianOneModels;
    private String tanggal = "";
    private String kode = "";

    String absen_final = "";
    private ImageView img_menu;

    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    private SharedPreferences.Editor editor;

    private Handler handler = new Handler();

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dokter);


        pd = new ProgressDialog(this);

        btn_absen = findViewById(R.id.btn_absen);
        rv_pasien = findViewById(R.id.rv_pasien);
        tv_kosong = findViewById(R.id.tv_kosong);
        img_menu = findViewById(R.id.img_menu);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(DokterActivity.this);
                dialog.setTitle("Logout");
                dialog.setMessage("Yakin ingin keluar dari admin ?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DokterActivity.this, LoginActivity.class);
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


        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tanggal = dateFormat.format(date);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        no_telpon = findViewById(R.id.no_telpon);
        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        nama_lengkap.setText(bundle.getString(GET_NAMA));
        no_telpon.setText(bundle.getString(GET_TELPON));

        btn_absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(DokterActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Kehadiran")
                        .setContentText("Ingin Mengubah Status Kehadiran ?")
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
                                updateDokter();
                            }
                        })
                        .show();
            }
        });
        readDataDokter();

        handler.postDelayed(runnable, 10000);

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            loadData();
            handler.postDelayed(this, 10000);
        }
    };

    private void loadData() {

        if (absen.equals("Hadir")) {
            btn_absen.setBackground(ContextCompat.getDrawable(
                    DokterActivity.this, R.drawable.bg_btn_mati));
            btn_absen.setText("SEDANG OFFLINE");

        } else if (absen.equals("Libur")) {
            btn_absen.setBackground(ContextCompat.getDrawable(
                    DokterActivity.this, R.drawable.bg_btn_terang));
            btn_absen.setText("SEDANG ONLINE");
        }

        ApiRequestAntrian apiRequestAntrian = RetroServerAntrian.getClient().create(ApiRequestAntrian.class);
        Call<ResponsModelTambah> getDataantrian = apiRequestAntrian.getAllmenungguJam(tanggal, jam_mulai, status);
        getDataantrian.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                assert response.body() != null;
                kode = response.body().getKode();
                if (kode.equals("1")) {
                    if (response.body().getResultstatusAntrian().isEmpty()) {
                        tv_kosong.setText("Belum Ada Antrian");
                    } else {
                        getDataAntrianOneModels = response.body().getResultstatusAntrian();
                        PasienTungguAdapter adapter = new PasienTungguAdapter(DokterActivity.this, getDataAntrianOneModels);
                        rv_pasien.setLayoutManager(new LinearLayoutManager(DokterActivity.this));
                        rv_pasien.setAdapter(adapter);
                        tv_kosong.setText("");
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
            }
        });
    }


    private void DialogForm() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DokterActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.input_dialog_spesialis, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Isi Terlebih dahulu");

        dialog.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.setMessage("Proses ..");
                pd.setCancelable(false);
                pd.show();


                EditText tv_spesialis_dialog = dialogView.findViewById(R.id.tv_spesialis_dialog);

                ApiRequestDoketr apiRequestDoketr = RetroServerDokter.getClient().create(ApiRequestDoketr.class);
                Call<ResponsModelTambah> updatedataDokter = apiRequestDoketr.updateDatadokter(id_dokter, nama, tv_spesialis_dialog.getText().toString(), absen);
                updatedataDokter.enqueue(new Callback<ResponsModelTambah>() {
                    @Override
                    public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();
                        if (kode.equals("1")) {
                            readDataDokter();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                        pd.hide();
                    }
                });
            }
        });

        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                updateDokter();
            }
        });

        dialog.show();
    }

    private void updateDokter() {

        if (absen.equals("Hadir")) {
            absen_final = "Libur";
        } else if (absen.equals("Libur")) {
            absen_final = "Hadir";
        }
        ApiRequestDoketr apiRequestDoketr = RetroServerDokter.getClient().create(ApiRequestDoketr.class);
        Call<ResponsModelTambah> updatedataDokter = apiRequestDoketr.updateDatadokter(id_dokter, nama, spesialis, absen_final);
        updatedataDokter.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                Log.d("RETRO", "response : " + response.body().toString());
                String kode = response.body().getKode();
                if (kode.equals("1")) {

                    if (absen.equals("Hadir")) {
                        btn_absen.setBackground(ContextCompat.getDrawable(
                                DokterActivity.this, R.drawable.bg_btn_mati));
                        btn_absen.setText("SEDANG OFFLINE");

                    } else if (absen.equals("Libur")) {
                        btn_absen.setBackground(ContextCompat.getDrawable(
                                DokterActivity.this, R.drawable.bg_btn_terang));
                        btn_absen.setText("SEDANG ONLINE");
                    }
                    readDataDokter();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
            }
        });


    }

    private void readDataDokter() {
        pd.setMessage("Proses ... ");
        pd.setCancelable(true);
        pd.show();

        ApiRequestDoketr apiRequestDoketr = RetroServerDokter.getClient().create(ApiRequestDoketr.class);
        Call<List<DokterModel>> readDokter = apiRequestDoketr.getdataDokter(nama_lengkap.getText().toString());
        readDokter.enqueue(new Callback<List<DokterModel>>() {
            @Override
            public void onResponse(Call<List<DokterModel>> call, Response<List<DokterModel>> response) {
                pd.hide();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().isEmpty()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            id_dokter = response.body().get(i).getId_dokter();
                            nama = response.body().get(i).getNama_lengkap();
                            spesialis = response.body().get(i).getSpesialis();
                            jam_mulai = response.body().get(i).getJam_mulai();
                            absen = response.body().get(i).getAbsen();
                            if (spesialis.equals("-")) {
                                DialogForm();
                            }
                        }

                        loadData();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DokterModel>> call, Throwable t) {
                pd.hide();
            }
        });
    }
}
