package com.example.klinik_pln.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDataUserModel implements Parcelable {

    @SerializedName("id_user")
    @Expose
    private String  id_user;

    @SerializedName("nama_user")
    @Expose
    private String  nama;

    @SerializedName("alamat_user")
    @Expose
    private String alamat;

    @SerializedName("telepon_user")
    @Expose
    private String telpon;

    @SerializedName("jekel_user")
    @Expose
    private String jekel;

    @SerializedName("tanggal_lahir")
    @Expose
    private String tanggal_lahir;

    @SerializedName("username_user")
    @Expose
    private String username_user;

    @SerializedName("password_user")
    @Expose
    private String password;

    @SerializedName("level_user")
    @Expose
    private String level;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getJekel() {
        return jekel;
    }

    public void setJekel(String jekel) {
        this.jekel = jekel;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getUsername_user() {
        return username_user;
    }

    public void setUsername_user(String username_user) {
        this.username_user = username_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    protected GetDataUserModel(Parcel in) {
        id_user = in.readString();
        nama = in.readString();
        alamat = in.readString();
        telpon = in.readString();
        jekel = in.readString();
        tanggal_lahir = in.readString();
        username_user = in.readString();
        password = in.readString();
        level = in.readString();
    }

    public static final Creator<GetDataUserModel> CREATOR = new Creator<GetDataUserModel>() {
        @Override
        public GetDataUserModel createFromParcel(Parcel in) {
            return new GetDataUserModel(in);
        }

        @Override
        public GetDataUserModel[] newArray(int size) {
            return new GetDataUserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_user);
        dest.writeString(nama);
        dest.writeString(alamat);
        dest.writeString(telpon);
        dest.writeString(jekel);
        dest.writeString(tanggal_lahir);
        dest.writeString(username_user);
        dest.writeString(password);
        dest.writeString(level);
    }
}
