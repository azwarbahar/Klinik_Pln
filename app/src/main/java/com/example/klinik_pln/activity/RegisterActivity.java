package com.example.klinik_pln.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.klinik_pln.R;
import com.example.klinik_pln.api.ApiRequestUser;
import com.example.klinik_pln.api.RetroServerUser;
import com.example.klinik_pln.histrory.HistoryPasienActivity;
import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_fullnama, et_alamat, et_telpon, et_username, et_password;
    private EditText et_jekel;
    private EditText et_tgl_lahir;
    private String level = "pasien";
    private TextView tv_login;
    private ProgressDialog pd;

    private View dialogView;
    private AlertDialog.Builder dialog;
    private DateFormat dateFormat;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_fullnama = findViewById(R.id.et_fullnama);
        et_alamat = findViewById(R.id.et_alamat);
        et_jekel = findViewById(R.id.et_jekel);
        et_tgl_lahir = findViewById(R.id.et_tgl_lahir);
        et_telpon = findViewById(R.id.et_telpon);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        et_tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogTanggal();
            }
        });

        et_jekel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AlertDialog.Builder(RegisterActivity.this);
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


        tv_login = findViewById(R.id.tv_login);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        pd = new ProgressDialog(this);

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkForm()) {


                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Registrasi")
                            .setContentText("Ingin Registrasi Data ?")
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
                                    sendRegist();
                                }
                            })
                            .show();

                } else {
                    Toast.makeText(RegisterActivity.this, "Lengkapi Form Data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRegist() {


        pd.setMessage("Proses ... ");
        pd.setCancelable(false);
        pd.show();

        String username_cek = et_username.getText().toString();

        final ApiRequestUser apiRequestUser = RetroServerUser.getClient().create(ApiRequestUser.class);

        Call<List<GetDataUserModel>> getcekdata = apiRequestUser.getdatauser(username_cek);

        getcekdata.enqueue(new Callback<List<GetDataUserModel>>() {
            @Override
            public void onResponse(Call<List<GetDataUserModel>> call, Response<List<GetDataUserModel>> response) {
                pd.hide();

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (!response.body().isEmpty()) {

                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Mohon Maaf...")
                                .setContentText("Username sudah digunakan!")
                                .show();

                    } else {

                        String nama = et_fullnama.getText().toString();
                        String alamat = et_alamat.getText().toString();
                        String jekel_send = et_jekel.getText().toString();
                        String tgl_send = et_tgl_lahir.getText().toString();
                        String telpon = et_telpon.getText().toString();
                        String user = et_username.getText().toString();
                        String pass = et_password.getText().toString();

                        Call<ResponsModelTambah> senduser = apiRequestUser.sendUser(nama, alamat, telpon, jekel_send, tgl_send, user, pass, level);
                        senduser.enqueue(new Callback<ResponsModelTambah>() {
                            @Override
                            public void onResponse(Call<ResponsModelTambah>  call, Response<ResponsModelTambah> response) {
                                pd.hide();
                                Log.d("RETRO", "response : " + response.body().toString());
                                String kode = response.body().getKode();

                                if (kode.equals("1")) {
                                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success..")
                                            .setContentText("Berhasil Registrasi Akun")
                                            .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .show();

                                    et_alamat.setText("");
                                    et_fullnama.setText("");
                                    et_password.setText("");
                                    et_jekel.setText("");
                                    et_tgl_lahir.setText("");
                                    et_telpon.setText("");
                                    et_username.setText("");

                                } else {

                                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Mohon Maaf...")
                                            .setContentText("Terjadi Kesalahan!")
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponsModelTambah> call, Throwable t) {
                                pd.hide();

                                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Mohon Maaf...")
                                        .setContentText("Terjadi Kesalahan!")
                                        .show();
                                Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetDataUserModel>> call, Throwable t) {

            }
        });
    }

    private boolean checkForm() {

        String et_tgl_lahir_cek = et_tgl_lahir.getText().toString();
        String jekel = et_jekel.getText().toString();
        String alamat = et_alamat.getText().toString();
        String nama = et_fullnama.getText().toString();
        String telpon = et_telpon.getText().toString();
        String user = et_username.getText().toString();
        String pass = et_password.getText().toString();

        if (et_tgl_lahir_cek.equals("")) {
            return false;
        } else if (jekel.equals("")) {
            return false;
        } else if (alamat.equals("")) {
            return false;
        } else if (nama.equals("")) {
            return false;
        } else if (telpon.equals("")) {
            return false;
        } else if (user.equals("")) {
            return false;
        } else if (pass.equals("")) {
            return false;
        }
        return true;
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
}
