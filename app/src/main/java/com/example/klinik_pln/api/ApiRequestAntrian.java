package com.example.klinik_pln.api;

import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;
import com.example.klinik_pln.model.getDataAntrianOneModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestAntrian {
    @FormUrlEncoded
    @POST("tambah_antrian.php")
    Call<ResponsModelTambah> sendDataAntrian(@Field("kode_antrian") String kode_antrian,
                                      @Field("nama_pasien") String nama_pasien,
                                      @Field("tanggal_antrian") String tanggal_antrian,
                                      @Field("jam_antrian") String jam_antrian,
                                      @Field("status") String status);

    @GET("read_antrian.php")
    Call<List<getDataAntrianOneModel>> getSingleData(
            @Query("nama_pasien") String nama_pasien,
            @Query("tanggal_antrian") String tanggal_antrian,
            @Query("status") String status
    );

    @GET("read_antrian_user_done.php")
    Call<List<getDataAntrianOneModel>> getAntrianRiwayat(@Query("nama_pasien") String nama_pasien);

    @GET("read_antrian_user_status.php")
    Call<List<getDataAntrianOneModel>> read_antrian_user_status(@Query("nama_pasien") String nama_pasien,
                                                                @Query("status") String status);

    @GET("read_last_done_antrian.php")
    Call<ResponsModelTambah> getdatalastDone(@Query("jam_antrian") String jam_antrian,
                                             @Query("tanggal_antrian") String tanggal_antrian);

    @GET("read_all_menuggu.php")
    Call<ResponsModelTambah> getAllmenunggu(@Query("tanggal_antrian") String tanggal_antrian,
                                            @Query("jam") String jam,
                                            @Query("status") String status);

    @GET("read_all_menunggu_jam.php")
    Call<ResponsModelTambah> getAllmenungguJam(@Query("tanggal_antrian") String tanggal_antrian,
                                               @Query("jam") String jam,
                                               @Query("status") String status);

    @FormUrlEncoded
    @POST("update_antrian.php")
    Call<ResponsModelTambah> updateData(@Field("kode_antrian") String kode_antrian,
                                        @Field("tanggal_antrian") String tanggal_antrian,
                                        @Field("status") String status,
                                        @Field("jam_antrian") String jam_antrian);

    @GET("read_all_antrian.php")
    Call<ResponsModelTambah> getdataantrian(@Query("jam") String jam);

}
