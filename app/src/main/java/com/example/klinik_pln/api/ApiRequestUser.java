package com.example.klinik_pln.api;

import com.example.klinik_pln.model.GetDataUserModel;
import com.example.klinik_pln.model.ResponsModelTambah;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestUser {

    @FormUrlEncoded
    @POST("tambah_user.php")
    Call<ResponsModelTambah> sendUser(@Field("nama_user") String nama,
                                      @Field("alamat_user") String alamat,
                                      @Field("telepon_user") String telpon,
                                      @Field("jekel_user") String jekel,
                                      @Field("tanggal_lahir") String tanggal_lahir,
                                      @Field("username_user") String user,
                                      @Field("password_user") String pass,
                                      @Field("level_user") String level);

    @FormUrlEncoded
    @POST("update_user.php")
    Call<ResponsModelTambah> updateUser(@Field("id_user") String id_user,
                                      @Field("nama_user") String nama,
                                      @Field("alamat_user") String alamat,
                                      @Field("telepon_user") String telpon,
                                      @Field("jekel_user") String jekel,
                                      @Field("tanggal_lahir") String tanggal_lahir,
                                      @Field("username_user") String user,
                                      @Field("password_user") String pass,
                                      @Field("level_user") String level);


    @GET("single_data.php")
    Call<List<GetDataUserModel>> getSingleData(@Query("username_user") String username, @Query("password_user") String password);


    @GET("cek_username.php")
    Call<List<GetDataUserModel>> getdatauser(@Query("username_user") String username);


    @GET("read_pasien.php")
    Call<ResponsModelTambah> getdatapasien(@Query("level_user") String level_user);


    @FormUrlEncoded
    @POST("delete_user.php")
    Call<ResponsModelTambah> deleteUser(@Field("nama_user") String nama_user);

}
