package com.example.klinik_pln.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestUser;
import com.example.klinik_pln.api.RetroServerUser;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePasienActivity extends AppCompatActivity {

    private String id;
    private String nama;
    private String alamat;
    private String telpon;
    private String jekel;
    private String tgllahir;
    private String user;
    private String pass;
    private String level;

    private EditText et_fullnama;
    private EditText et_alamat;
    private EditText et_jekel;
    private EditText et_tgl_lahir;
    private EditText et_telpon;
    private EditText et_username;
    private EditText et_password;

    private TextView tv_nama;
    private TextView tv_alamat;
    private TextView tv_jekel;
    private TextView tv_tgl_lahir;
    private TextView tv_telpon;
    private TextView tv_username;
    private TextView tv_password;
    private Button btn_edit_profil;
    private Button btn_batal;
    private Button btn_simpan;
    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    private SharedPreferences.Editor editor;

    private SlidingUpPanelLayout sliding_layout;
    private View dialogView;
    private AlertDialog.Builder dialog;
    private DateFormat dateFormat;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pasien);


        sliding_layout = findViewById(R.id.sliding_layout_akun);

        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);
        et_telpon = findViewById(R.id.et_telpon);
        et_tgl_lahir = findViewById(R.id.et_tgl_lahir);
        et_jekel = findViewById(R.id.et_jekel);
        et_username = findViewById(R.id.et_username);
        et_alamat = findViewById(R.id.et_alamat);
        et_fullnama = findViewById(R.id.et_fullnama);


        tv_nama = findViewById(R.id.tv_nama);
        tv_alamat = findViewById(R.id.tv_alamat);
        tv_jekel = findViewById(R.id.tv_jekel);
        tv_tgl_lahir = findViewById(R.id.tv_tgl_lahir);
        tv_telpon = findViewById(R.id.tv_telpon);
        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);
        btn_edit_profil = findViewById(R.id.btn_edit_profil);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_batal = findViewById(R.id.btn_batal);

        setData();

        et_tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTanggal();
            }
        });

        et_jekel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new AlertDialog.Builder(ProfilePasienActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                dialogView = inflater.inflate(R.layout.dialog_jekel, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
                final AlertDialog show = dialog.show();

                TextView tv_laki = dialogView.findViewById(R.id.tv_laki);
                TextView tv_perempuan = dialogView.findViewById(R.id.tv_perempuan);
                tv_laki.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_jekel.setText("Laki - laki");
                        show.dismiss();
                    }
                });

                tv_perempuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        et_jekel.setText("Perempuan");
                        show.dismiss();
                    }
                });
            }
        });

        btn_edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanel();
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPanel();
                resetUpPael();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(ProfilePasienActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Simpan")
                        .setContentText("Ingin Menyimpan Perubahan Profile ?")
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
                                setUpdateData();
                            }
                        })
                        .show();
            }
        });

    }

    private void showDialogTanggal() {

        dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

        Calendar mCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar newdate = Calendar.getInstance();
                newdate.set(year, month, day);

                et_tgl_lahir.setText(dateFormat.format(newdate.getTime()));
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void setUpdateData() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);
        pDialog.show();

        String id_send = id;
        String nama_send = String.valueOf(et_fullnama.getText());
        String alamat_send = String.valueOf(et_alamat.getText());
        String telpon_send = String.valueOf(et_telpon.getText());
        String jekel_send = String.valueOf(et_jekel.getText());
        String tgl_send = String.valueOf(et_tgl_lahir.getText());
        String user_send = String.valueOf(et_username.getText());
        String pass_send = String.valueOf(et_password.getText());
        String level_send = level;

        ApiRequestUser apiRequestUser = RetroServerUser.getClient().create(ApiRequestUser.class);
        Call<ResponsModelTambah> modelTambahCall = apiRequestUser.updateUser(id_send,nama_send,alamat_send,
                telpon_send,jekel_send,tgl_send,user_send,pass_send,level_send);
        modelTambahCall.enqueue(new Callback<ResponsModelTambah>() {
            @Override
            public void onResponse(Call<ResponsModelTambah> call, Response<ResponsModelTambah> response) {
                pDialog.dismiss();
                if (response.body().getKode().equals("1")){
                    new SweetAlertDialog(ProfilePasienActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success..")
                            .setContentText(response.body().getPesan())
                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    updateDataShared();
                                }
                            })
                            .show();

                } else {

                    new SweetAlertDialog(ProfilePasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Mohon Maaf...")
                            .setContentText("Terjadi Kesalahan!")
                            .show();
                }
            }

            @Override
            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(ProfilePasienActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Permintaan Gagal, Terjadi Kesalahan")
                        .show();
            }
        });

    }

    private void updateDataShared() {

        String id_send = id;
        String nama_send = String.valueOf(et_fullnama.getText());
        String alamat_send = String.valueOf(et_alamat.getText());
        String telpon_send = String.valueOf(et_telpon.getText());
        String jekel_send = String.valueOf(et_jekel.getText());
        String tgl_send = String.valueOf(et_tgl_lahir.getText());
        String user_send = String.valueOf(et_username.getText());
        String pass_send = String.valueOf(et_password.getText());
        String level_send = level;


        editor = sharedpreferences.edit();
        editor.putString("ID", id_send);
        editor.putString("NAMA", nama_send);
        editor.putString("ALAMAT",alamat_send);
        editor.putString("TELPON", telpon_send);
        editor.putString("JEKEL", jekel_send);
        editor.putString("TGL_LAHIR", tgl_send);
        editor.putString("USER", user_send);
        editor.putString("PASS", pass_send);
        editor.putString("LEVEL", level_send);
        editor.apply();

        setData();
        showPanel();

    }

    private void showPanel() {

        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        }

    }

    private void setData() {

        sharedpreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        id = sharedpreferences.getString("ID", "");
        nama = sharedpreferences.getString("NAMA", "");
        alamat = sharedpreferences.getString("ALAMAT", "");
        telpon = sharedpreferences.getString("TELPON", "");
        jekel = sharedpreferences.getString("JEKEL", "");
        tgllahir = sharedpreferences.getString("TGL_LAHIR", "");
        user = sharedpreferences.getString("USER", "");
        pass = sharedpreferences.getString("PASS", "");
        level = sharedpreferences.getString("LEVEL", "");

        tv_nama.setText(nama);
        tv_alamat.setText(alamat);
        tv_jekel.setText(jekel);
        tv_tgl_lahir.setText(tgllahir);
        tv_telpon.setText(telpon);
        tv_username.setText(user);
        tv_password.setText(pass);

        resetUpPael();
    }

    private void resetUpPael(){

        et_fullnama.setText(nama);
        et_alamat.setText(alamat);
        et_telpon.setText(telpon);
        et_jekel.setText(jekel);
        et_tgl_lahir.setText(tgllahir);
        et_username.setText(user);
        et_password.setText(pass);

    }
}