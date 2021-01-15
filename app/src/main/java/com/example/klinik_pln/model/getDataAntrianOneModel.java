package com.example.klinik_pln.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getDataAntrianOneModel implements Parcelable {

    public getDataAntrianOneModel(String kode_antrian, String nama_pasien, String tanggal_antrian, String jam_antrian, String status) {
        this.kode_antrian = kode_antrian;
        this.nama_pasien = nama_pasien;
        this.tanggal_antrian = tanggal_antrian;
        this.jam_antrian = jam_antrian;
        this.status = status;
    }

    @SerializedName("kode_antrian")
    @Expose
    String kode_antrian;

    @SerializedName("nama_pasien")
    @Expose
    String nama_pasien;

    @SerializedName("tanggal_antrian")
    @Expose
    String tanggal_antrian;

    @SerializedName("jam_antrian")
    @Expose
    String jam_antrian;

    @SerializedName("status")
    @Expose
    String status;

    protected getDataAntrianOneModel(Parcel in) {
        kode_antrian = in.readString();
        nama_pasien = in.readString();
        tanggal_antrian = in.readString();
        jam_antrian = in.readString();
        status = in.readString();
    }

    public static final Creator<getDataAntrianOneModel> CREATOR = new Creator<getDataAntrianOneModel>() {
        @Override
        public getDataAntrianOneModel createFromParcel(Parcel in) {
            return new getDataAntrianOneModel(in);
        }

        @Override
        public getDataAntrianOneModel[] newArray(int size) {
            return new getDataAntrianOneModel[size];
        }
    };

    public String getKode_antrian() {
        return kode_antrian;
    }

    public void setKode_antrian(String kode_antrian) {
        this.kode_antrian = kode_antrian;
    }

    public String getJam_antrian() {
        return jam_antrian;
    }

    public void setJam_antrian(String jam_antrian) {
        this.jam_antrian = jam_antrian;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public void setNama_pasien(String nama_pasien) {
        this.nama_pasien = nama_pasien;
    }

    public String getTanggal_antrian() {
        return tanggal_antrian;
    }

    public void setTanggal_antrian(String tanggal_antrian) {
        this.tanggal_antrian = tanggal_antrian;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public getDataAntrianOneModel(String nama_pasien, String tanggal_antrian, String status) {
        this.nama_pasien = nama_pasien;
        this.tanggal_antrian = tanggal_antrian;
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kode_antrian);
        dest.writeString(nama_pasien);
        dest.writeString(tanggal_antrian);
        dest.writeString(jam_antrian);
        dest.writeString(status);
    }
}
