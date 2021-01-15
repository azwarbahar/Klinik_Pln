package com.example.klinik_pln.antrian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.klinik_pln.R;
import com.example.klinik_pln.antrian.antriankedua.AntrianKeduaActivity;
import com.example.klinik_pln.antrian.antrianketiga.AntrianKetigaActivity;
import com.example.klinik_pln.antrian.antrianpertama.AntrianPertamaActivity;

public class ItemAntrianAdminActivity extends AppCompatActivity {

    private RelativeLayout cv1;
    private RelativeLayout cv2;
    private RelativeLayout cv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_antrian_admin);

        cv1 = findViewById(R.id.cv1);
        cv2 = findViewById(R.id.cv2);
        cv3 = findViewById(R.id.cv3);

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemAntrianAdminActivity.this, AntrianPertamaActivity.class);
                startActivity(intent);
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemAntrianAdminActivity.this, AntrianKeduaActivity.class);
                startActivity(intent);
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemAntrianAdminActivity.this, AntrianKetigaActivity.class);
                startActivity(intent);
            }
        });
    }
}