package com.example.klinik_pln.api;

import com.example.klinik_pln.model.DokterModel;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestDoketr {

    @FormUrlEncoded
    @POST("tambah_dokter.php")
    Call<ResponsModelTambah> sendDataDokter(@Field("nama_lengkap") String nama_lengkap,
                                      @Field("spesialis") String spesialis,
                                      @Field("jam_mulai") String jam_mulai,
                                      @Field("absen") String absen);

    @GET("read_one_dokter.php")
    Call<List<DokterModel>> getdataDokter(@Query("nama_lengkap") String nama_lengkap);

    @GET("read_one_dokter_jam_kerja.php")
    Call<List<DokterModel>> getdataDokterJam(@Query("jam_mulai") String jam_mulai);

    @FormUrlEncoded
    @POST("update_dokter.php")
    Call<ResponsModelTambah> updateDatadokter(@Field("id_dokter") String id_dokter,
                                              @Field("nama_lengkap") String nama_lengkap,
                                              @Field("spesialis") String spesialis,
                                              @Field("absen") String absen);

    @GET("read_absen_dokter.php")
    Call<ResponsModelTambah> getabsenDokter();


}
