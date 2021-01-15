package com.example.klinik_pln.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.klinik_pln.R;
import com.example.klinik_pln.activity.PasienActivity;

public class CekAntrianActivity extends AppCompatActivity {

    //Terima data dari Branda
    private String GET_KODE = "get_kode";
    private String GET_PERIKSA = "get_periksa";
    private String GET_TGL = "get_tgl";
    private String GET_JAM = "get_jam";

    private TextView no_antrian;
    private TextView tv_no_periksa;
    private TextView tv_jam;
    private TextView tv_tgl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_antrian);

        no_antrian = findViewById(R.id.no_antrian);
        tv_no_periksa = findViewById(R.id.tv_no_periksa);
        tv_jam = findViewById(R.id.tv_jam);
        tv_tgl = findViewById(R.id.tv_tgl);

        DapatData();
    }

    private void DapatData(){
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        no_antrian.setText(bundle.getString(GET_KODE));
        tv_no_periksa.setText(bundle.getString(GET_PERIKSA));
        tv_jam.setText(bundle.getString(GET_JAM));
        tv_tgl.setText(bundle.getString(GET_TGL));
    }
}
