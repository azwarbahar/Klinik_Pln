package com.example.klinik_pln.klinik;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KlinikModel implements Parcelable {

    @SerializedName("id_klinik")
    @Expose
    private String id;

    @SerializedName("nama_klinik")
    @Expose
    private String nama;

    @SerializedName("lokasi_klinik")
    @Expose
    private String lokasi;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    protected KlinikModel(Parcel in) {
        id = in.readString();
        nama = in.readString();
        lokasi = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<KlinikModel> CREATOR = new Creator<KlinikModel>() {
        @Override
        public KlinikModel createFromParcel(Parcel in) {
            return new KlinikModel(in);
        }

        @Override
        public KlinikModel[] newArray(int size) {
            return new KlinikModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nama);
        dest.writeString(lokasi);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}
