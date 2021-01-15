package com.example.klinik_pln.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.klinik_pln.R;
import com.example.klinik_pln.adapter.ViewPagerAntrianAdapter;
import com.example.klinik_pln.fragment.AllAntrianFragment;
import com.example.klinik_pln.fragment.DoneFragment;
import com.example.klinik_pln.fragment.MenungguFragment;
import com.example.klinik_pln.model.AntrianModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AntrianAdminActivity extends AppCompatActivity {

    private TabLayout tab_antrian;
    private ViewPager pager_antrian;
    private ViewPagerAntrianAdapter adapter;
    private TextView tv_tanggal;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_admin);

        Toolbar mTopToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        tab_antrian = findViewById(R.id.tab_antrian);
        pager_antrian = findViewById(R.id.pager_antrian);
        adapter = new ViewPagerAntrianAdapter(getSupportFragmentManager());
        pd = new ProgressDialog(this);

        adapter.AddFragment(new MenungguFragment(), "Menunggu");
        adapter.AddFragment(new DoneFragment(), "Selesai");
        adapter.AddFragment(new AllAntrianFragment(), "Riwayat");
        pager_antrian.setAdapter(adapter);
        tab_antrian.setupWithViewPager(pager_antrian);
        tv_tanggal = findViewById(R.id.tv_tanggal);
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Date date = new Date();
        tv_tanggal.setText(dateFormat.format(date));
    }

}
