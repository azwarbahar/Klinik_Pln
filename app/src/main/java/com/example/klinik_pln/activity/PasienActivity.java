package com.example.klinik_pln.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.histrory.HistoryPasienActivity;
import com.example.klinik_pln.model.AntrianLastDoneModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PasienActivity extends AppCompatActivity {

    DatabaseReference database;

    private String GET_NAMA = "get_nama";
    private String GET_LEVEL = "get_level";
    private String GET_ALAMAT = "get_alamat";
    private String GET_TELPON = "get_telpon";
    private String GET_JEKEL = "get_jekel";
    private String GET_USER = "get_user";
    private String GET_PASS = "get_pass";

    //kirim data ke cek nomor antrian
    private String GET_KODE = "get_kode";
    private String GET_PERIKSA = "get_periksa";
    private String GET_TGL = "get_tgl";
    private String GET_JAM = "get_jam";

    TextView nama_lengkap;
    TextView no_telpon;
    TextView tv_no_antrian;
    TextView tv_no_periksa;
    TextView tanggal;
    TextView jam;

    CardView cv_jadwal;
    CardView cv_apotek;
    CardView cv_riwayat;
    CardView cv_logout;
    private CardView cv_profile;

    private String nama;
    private String alamat;
    private String telpon;
    private String jekel;
    private String username;
    private String password;
    private String level;

    private String kode_sekarang;
    private String tgl_antrian;
    private String jam_antrian;

    //variabel realtime
    private String nol = "";
    private String kode = "PS";
    private String nomor_tampil = "";
    private String nomor_final = "";

    private String status_nomor = "";
    private List<AntrianLastDoneModel> antrianLastDoneModels;

    private String getStatusKlini = "";

    private Handler handler = new Handler();

    ProgressDialog pd;

    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien);

        handler.postDelayed(runnable, 1000);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        no_telpon = findViewById(R.id.no_telpon);
        cv_jadwal = findViewById(R.id.cv_jadwal);
        cv_apotek = findViewById(R.id.cv_apotek);
        cv_riwayat = findViewById(R.id.cv_riwayat);
        cv_logout = findViewById(R.id.cv_logout);
        cv_profile = findViewById(R.id.cv_profile);
        tv_no_periksa = findViewById(R.id.tv_no_periksa);
        tanggal = findViewById(R.id.tanggal);
        jam = findViewById(R.id.jam);
        pd = new ProgressDialog(this);
        getBundle();

        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(PasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Profile")
                        .setContentText("Ingin Masuk Halaman Profile ?")
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
                                Intent intent = new Intent(PasienActivity.this, ProfilePasienActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        });

        cv_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(PasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Jadwal Doket")
                        .setContentText("Ingin Masuk Halaman Jadwal ?")
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
                                Intent intent = new Intent(PasienActivity.this, CekDokterActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        cv_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(PasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("History")
                        .setContentText("Ingin Masuk Halaman History ?")
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
                                Intent intent = new Intent(PasienActivity.this, HistoryPasienActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        });

        cv_apotek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(PasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Lokasi Apotek")
                        .setContentText("Ingin Masuk Halaman Lokasi Apotek ?")
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

                                Intent intent = new Intent(PasienActivity.this, JalanApotekActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        });

        cv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(PasienActivity.this);
                dialog.setTitle("Logout");
                dialog.setMessage("Yakin ingin keluar dari Pasien ?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PasienActivity.this, LoginActivity.class);
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


        database = FirebaseDatabase.getInstance().getReference();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tanggal.setText(dateFormat.format(date));

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            jam.setText(timeFormat.format(date));

            handler.postDelayed(this, 1000);
        }
    };

    private void getBundle() {

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        nama = bundle.getString(GET_NAMA);
        alamat = bundle.getString(GET_ALAMAT);
        telpon = bundle.getString(GET_TELPON);
        jekel = bundle.getString(GET_JEKEL);
        username = bundle.getString(GET_USER);
        password = bundle.getString(GET_PASS);
        level = bundle.getString(GET_LEVEL);
        nama_lengkap.setText(nama);
        no_telpon.setText(telpon);
    }
}
