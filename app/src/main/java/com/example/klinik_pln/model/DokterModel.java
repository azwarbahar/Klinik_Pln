package com.example.klinik_pln.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DokterModel implements Parcelable {

    @SerializedName("id_dokter")
    @Expose
    private String  id_dokter;

    @SerializedName("nama_lengkap")
    @Expose
    private String  nama_lengkap;

    @SerializedName("spesialis")
    @Expose
    private String  spesialis;

    @SerializedName("jam_mulai")
    @Expose
    private String  jam_mulai;

    @SerializedName("absen")
    @Expose
    private String  absen;

    protected DokterModel(Parcel in) {
        id_dokter = in.readString();
        nama_lengkap = in.readString();
        spesialis = in.readString();
        jam_mulai = in.readString();
        absen = in.readString();
    }

    public static final Creator<DokterModel> CREATOR = new Creator<DokterModel>() {
        @Override
        public DokterModel createFromParcel(Parcel in) {
            return new DokterModel(in);
        }

        @Override
        public DokterModel[] newArray(int size) {
            return new DokterModel[size];
        }
    };

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getAbsen() {
        return absen;
    }

    public void setAbsen(String absen) {
        this.absen = absen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_dokter);
        dest.writeString(nama_lengkap);
        dest.writeString(spesialis);
        dest.writeString(jam_mulai);
        dest.writeString(absen);
    }
}
