package com.example.klinik_pln.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestUser;
import com.example.klinik_pln.api.RetroServerUser;
import com.example.klinik_pln.model.GetDataUserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String GET_ID = "get_id";
    private String GET_NAMA = "get_nama";
    private String GET_LEVEL = "get_level";
    private String GET_ALAMAT = "get_alamat";
    private String GET_TELPON = "get_telpon";
    private String GET_JEKEL = "get_jekel";
    private String GET_TGL_LAHIR = "get_tgl_lahir";
    private String GET_USER = "get_user";
    private String GET_PASS = "get_pass";

    private String id;
    private String nama;
    private String alamat;
    private String telpon;
    private String jekel;
    private String tgl_lahir;
    private String user;
    private String pass;
    private String level;

    private TextView nama_user, alamat_user, telepon_user, jekel_user, tvtgl_lahir, username_user, password_user, level_user;
    private EditText input_username;
    private EditText input_password;
    private Button btn_login;
    private TextView tv_register;
    private TextView tv_salah;
    private String CEK_LEVEL = "";

    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    private SharedPreferences.Editor editor;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getApplicationContext().getSharedPreferences(my_shared_preferences, MODE_PRIVATE);
        id = sharedpreferences.getString("ID", "");
        nama = sharedpreferences.getString("NAMA", "");
        alamat = sharedpreferences.getString("ALAMAT", "");
        telpon = sharedpreferences.getString("TELPON", "");
        jekel = sharedpreferences.getString("JEKEL", "");
        tgl_lahir = sharedpreferences.getString("TGL_LAHIR", "");
        user = sharedpreferences.getString("USER", "");
        pass = sharedpreferences.getString("PASS", "");
        level = sharedpreferences.getString("LEVEL", "");
        if (!level.isEmpty()) {
            if (!level.equals("")) {

                Bundle bundle = new Bundle();
                bundle.putString(GET_ID, id);
                bundle.putString(GET_NAMA, nama);
                bundle.putString(GET_ALAMAT, alamat);
                bundle.putString(GET_TELPON, telpon);
                bundle.putString(GET_JEKEL, jekel);
                bundle.putString(GET_TGL_LAHIR, tgl_lahir);
                bundle.putString(GET_USER, user);
                bundle.putString(GET_PASS, pass);
                bundle.putString(GET_LEVEL, level);

                if (level.equals("admin")) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else if (level.equals("dokter")) {
                    Intent intent = new Intent(LoginActivity.this, DokterActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                } else if (level.equals("pasien")) {
                    Intent intent = new Intent(LoginActivity.this, PasienActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        }

        pd = new ProgressDialog(this);

        nama_user = findViewById(R.id.nama_user);
        alamat_user = findViewById(R.id.alamat_user);
        telepon_user = findViewById(R.id.telepon_user);
        jekel_user = findViewById(R.id.jekel_user);
        tvtgl_lahir = findViewById(R.id.tvtgl_lahir);
        username_user = findViewById(R.id.username_user);
        password_user = findViewById(R.id.password_user);
        level_user = findViewById(R.id.level_user);
        input_username = findViewById(R.id.input_username);
        input_password = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        tv_salah = findViewById(R.id.tv_salah);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cekusername = input_username.getText().toString();
                String cekpassword = input_password.getText().toString();
                if (cekusername.isEmpty()) {
                    tv_salah.setText("Usrename Kosong");
//                    Toast.makeText(LoginActivity.this, "Username Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    input_username.setFocusable(true);
                } else if (cekpassword.isEmpty()) {
                    tv_salah.setText("Password Kosong");
//                    Toast.makeText(LoginActivity.this, "Password Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    input_password.setFocusable(true);
                } else {
                    cekuser();
                }

            }
        });
    }


    private void cekuser() {

        nama_user.setText(" ");
        alamat_user.setText(" ");
        telepon_user.setText(" ");
        jekel_user.setText(" ");
        tvtgl_lahir.setText(" ");
        username_user.setText(" ");
        password_user.setText(" ");
        level_user.setText(" ");

        pd.setMessage("Proses ... ");
        pd.setCancelable(true);
        pd.show();


        String username_login;
        username_login = input_username.getText().toString().trim();
        String password_login;
        password_login = input_password.getText().toString().trim();


        ApiRequestUser apiRequestUser = RetroServerUser.getClient().create(ApiRequestUser.class);
        Call<List<GetDataUserModel>> getdataUser = apiRequestUser.getSingleData(username_login, password_login);

        getdataUser.enqueue(new Callback<List<GetDataUserModel>>() {
            @Override
            public void onResponse(Call<List<GetDataUserModel>> call, Response<List<GetDataUserModel>> response) {
                pd.hide();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().isEmpty()) {

                        tv_salah.setText("");

                        for (int i = 0; i < response.body().size(); i++) {
                            id = response.body().get(i).getId_user();
                            nama_user.setText(response.body().get(i).getNama());
                            alamat_user.setText(response.body().get(i).getAlamat());
                            telepon_user.setText(response.body().get(i).getTelpon());
                            jekel_user.setText(response.body().get(i).getJekel());
                            tvtgl_lahir.setText(response.body().get(i).getTanggal_lahir());
                            username_user.setText(response.body().get(i).getUsername_user());
                            password_user.setText(response.body().get(i).getPassword());
                            level_user.setText(response.body().get(i).getLevel());
                            CEK_LEVEL = response.body().get(i).getLevel();
                        }


                        editor = sharedpreferences.edit();
                        editor.putString("ID", id);
                        editor.putString("NAMA", String.valueOf(nama_user.getText()));
                        editor.putString("ALAMAT", String.valueOf(alamat_user.getText()));
                        editor.putString("TELPON", String.valueOf(telepon_user.getText()));
                        editor.putString("JEKEL", String.valueOf(jekel_user.getText()));
                        editor.putString("TGL_LAHIR", String.valueOf(tvtgl_lahir.getText()));
                        editor.putString("USER", String.valueOf(username_user.getText()));
                        editor.putString("PASS", String.valueOf(input_password.getText()));
                        editor.putString("LEVEL", String.valueOf(level_user.getText()));
                        editor.apply();

                        Bundle bundle = new Bundle();
                        bundle.putString(GET_ID, id);
                        bundle.putString(GET_NAMA, (String) nama_user.getText());
                        bundle.putString(GET_ALAMAT, (String) alamat_user.getText());
                        bundle.putString(GET_TELPON, (String) telepon_user.getText());
                        bundle.putString(GET_JEKEL, (String) jekel_user.getText());
                        bundle.putString(GET_TGL_LAHIR, (String) tvtgl_lahir.getText());
                        bundle.putString(GET_USER, (String) username_user.getText());
                        bundle.putString(GET_PASS, (String) password_user.getText());
                        bundle.putString(GET_LEVEL, (String) level_user.getText());

                        if (CEK_LEVEL.equals("admin")) {
                            Toast.makeText(LoginActivity.this, "Berhasil Admin", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else if (CEK_LEVEL.equals("dokter")) {
                            Toast.makeText(LoginActivity.this, "Berhasil Dokter", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, DokterActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        } else if (CEK_LEVEL.equals("pasien")) {
                            Toast.makeText(LoginActivity.this, "Berhasil Pasien", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, PasienActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    } else {

                        tv_salah.setText("Username atau Password anda salah !");

                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetDataUserModel>> call, Throwable t) {
                pd.hide();
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
