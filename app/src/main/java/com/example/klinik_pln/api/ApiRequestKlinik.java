package com.example.klinik_pln.api;

import com.example.klinik_pln.model.ResponsModelTambah;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequestKlinik {

    @FormUrlEncoded
    @POST("tambah_klinik.php")
    Call<ResponsModelTambah> sendData(@Field("nama_klinik") String nama_klinik,
                                      @Field("lokasi_klinik") String lokasi_klinik,
                                      @Field("latitude") String latitude,
                                      @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("delete_klinik.php")
    Call<ResponsModelTambah> deleteData(@Field("id_klinik") String id_klinik);

    @GET("read_klinik.php")
    Call<ResponsModelTambah> getdataKlinik();

    @FormUrlEncoded
    @POST("update_klinik.php")
    Call<ResponsModelTambah> updateData(@Field("id_klinik") String id_klinik,
                                        @Field("nama_klinik") String nama_klinik,
                                        @Field("lokasi_klinik") String lokasi_klinik);
}
