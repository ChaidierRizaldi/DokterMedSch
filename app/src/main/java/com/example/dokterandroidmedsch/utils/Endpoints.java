package com.example.dokterandroidmedsch.utils;

import com.example.dokterandroidmedsch.model.get.DataBlok;
import com.example.dokterandroidmedsch.model.get.JadwalByTanggal;
import com.example.dokterandroidmedsch.model.get.JadwalDokter;
import com.example.dokterandroidmedsch.model.get.ListBlok;
import com.example.dokterandroidmedsch.model.get.ResponseLogin;
import com.example.dokterandroidmedsch.model.get.ResponseUbahStatus;
import com.example.dokterandroidmedsch.model.post.RequestLogin;
import com.example.dokterandroidmedsch.model.post.UbahStatusJadwal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {
    @POST("dokter/login")
    Call<ResponseLogin> login (@Body RequestLogin body_login);

    @GET("jadwal/getByDokterId")
    Call<List<JadwalDokter>> getKonfirmasiJadwal(@Query("idDokter") int id_dokter);

    @PUT("jadwal/updateStatus")
    Call<ResponseUbahStatus> ubahStatus(@Body UbahStatusJadwal body_ubah_jadwal);

    @GET("blok/getByPengajarId")
    Call<List<ListBlok>> getBlok(@Query("idPengajar")int id);

    @GET("blok/getById/{id_blok}")
    Call<DataBlok> getDataBlok(@Path("id_blok") int id_blok);

    @GET("jadwal/dokter/getByTanggal")
    Call<List<JadwalByTanggal>> getJadwalByTanggal(@Query("idDokter")int id_dokter, @Query("idBlok")int id_blok, @Query("tanggal")String tanggal);
}
