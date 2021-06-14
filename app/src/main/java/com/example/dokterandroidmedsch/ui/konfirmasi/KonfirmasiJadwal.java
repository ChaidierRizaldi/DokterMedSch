package com.example.dokterandroidmedsch.ui.konfirmasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.databinding.ActivityKonfirmasiJadwalBinding;
import com.example.dokterandroidmedsch.model.get.JadwalDokter;
import com.example.dokterandroidmedsch.model.get.ResponseUbahStatus;
import com.example.dokterandroidmedsch.model.post.UbahStatusJadwal;
import com.example.dokterandroidmedsch.utils.Retrofit;
import com.example.dokterandroidmedsch.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiJadwal extends AppCompatActivity {

    private ActivityKonfirmasiJadwalBinding binding;
    AdapterKonfirmasiJadwal adapter;
    Dialog dialog_konfirmasi_jadwal;
    SharedPreferences sp_helper;
    List<JadwalDokter> list_jadwal_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKonfirmasiJadwalBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = binding.getRoot();
        setContentView(view);

        list_jadwal_dokter = new ArrayList<>();
        sp_helper = new SharedPreferences(this);
        binding.rvJadwalDokter.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapter = new AdapterKonfirmasiJadwal(list_jadwal_dokter);

        getDataJadwal();


        binding.btnBackKonfirmasi.setOnClickListener(v->{
            onBackPressed();
        });
    }

    private void getDataJadwal() {
        HashMap<String , String> detail_user = sp_helper.getDetailUser();

        int id_dokter = Integer.parseInt(detail_user.get(SharedPreferences.ID));

        Log.d("id_dokter", String.valueOf(id_dokter));

        Call<List<JadwalDokter>> retrofit_jadwal = Retrofit.endpoints().getKonfirmasiJadwal(id_dokter);
        retrofit_jadwal.enqueue(new Callback<List<JadwalDokter>>() {
            @Override
            public void onResponse(Call<List<JadwalDokter>> call, Response<List<JadwalDokter>> response) {
                if (response.isSuccessful()){
                    Log.d("Jadwal", response.body().toString());
                    List<JadwalDokter> jadwal = response.body();

                    jadwal.forEach(jadwalDokter -> {
                        int tolak_jadwal = 2;
                        int konfirmasi_jadwal = 1;

                        if (jadwalDokter.getStatus() == 0){
                            binding.rvJadwalDokter.setVisibility(View.VISIBLE);
                            binding.tvTidakAdaJadwal.setVisibility(View.GONE);
                            adapter.setData(jadwal);
                            binding.rvJadwalDokter.setAdapter(adapter);

                            adapter.setOnClickListener(new AdapterKonfirmasiJadwal.KonfirmasiClickListener() {
                                @Override
                                public void onKonfrimasiClick(JadwalDokter confirmJadwal, int position) {
                                    ConfirmJadwal(jadwalDokter.getIdJadwal(), konfirmasi_jadwal);
                                    jadwal.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Log.d("ID Jadwal", String.valueOf(jadwalDokter.getIdJadwal()));
                                    Log.d("POSITION", String.valueOf(position));
                                }

                                @Override
                                public void onTolakClick(JadwalDokter rejectJadwal, int position) {
                                    OpenDialog(jadwalDokter.getIdJadwal(), tolak_jadwal);
                                    jadwal.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                            });
                        }
                    });
                    if (response.body().isEmpty()){
                        Log.d("Data Jadwal", "JADWAL KOSONG");
                    }
                }else {
                    Log.e("RESPONSE NOT SUCCES", "GAGAL DAPAT DATA");
                }
            }
            @Override
            public void onFailure(Call<List<JadwalDokter>> call, Throwable t) {
                Log.e("FAILURE", "RETROFIT GAGAL");
            }
        });
    }

    public void OpenDialog(int id_jadwal, int status){
        dialog_konfirmasi_jadwal = new Dialog(KonfirmasiJadwal.this);
        dialog_konfirmasi_jadwal.setContentView(R.layout.dialog_keterangan_tolak);
        dialog_konfirmasi_jadwal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_konfirmasi_jadwal.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        Button btn_konfirmasi = dialog_konfirmasi_jadwal.findViewById(R.id.btn_konfirmasi_tolak);
        Button btn_batal = dialog_konfirmasi_jadwal.findViewById(R.id.btn_batal_tolak);
        TextView note_ubah_jadwal  = dialog_konfirmasi_jadwal.findViewById(R.id.et_note_konfirmasi);


        dialog_konfirmasi_jadwal.create();
        dialog_konfirmasi_jadwal.show();



        btn_konfirmasi.setOnClickListener(v -> {
            String note = note_ubah_jadwal.getText().toString();

            UbahStatusJadwal req_body = new UbahStatusJadwal();
            req_body.setIdJadwal(id_jadwal);
            req_body.setStatus(status);
            req_body.setNote(note);

            Call<ResponseUbahStatus> ubah_jadwal = Retrofit.endpoints().ubahStatus(req_body);
            ubah_jadwal.enqueue(new Callback<ResponseUbahStatus>() {
                @Override
                public void onResponse(Call<ResponseUbahStatus> call, Response<ResponseUbahStatus> response) {
                        if (response.isSuccessful()){
                            Log.d("Perubahan Jadwal", response.body().getNote());
                        }
                }

                @Override
                public void onFailure(Call<ResponseUbahStatus> call, Throwable t) {
                    Log.d("ERROR UBAH JADWAL", t.getMessage());
                }
            });
            dialog_konfirmasi_jadwal.dismiss();
        });

        btn_batal.setOnClickListener(v->{
            dialog_konfirmasi_jadwal.dismiss();
        });
    }

    public  void ConfirmJadwal(int id_jadwal, int status){
        UbahStatusJadwal req_body = new UbahStatusJadwal();
        req_body.setNote("DISETUJUI");
        req_body.setStatus(status);
        req_body.setIdJadwal(id_jadwal);

        Call<ResponseUbahStatus> ubah_jadwal = Retrofit.endpoints().ubahStatus(req_body);
        ubah_jadwal.enqueue(new Callback<ResponseUbahStatus>() {
            @Override
            public void onResponse(Call<ResponseUbahStatus> call, Response<ResponseUbahStatus> response) {
                if (response.isSuccessful()){
                    Log.d("Perubahan Jadwal", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseUbahStatus> call, Throwable t) {
                Log.e("ERROR UBAH JADWAL", t.getMessage());
            }
        });
        getDataJadwal();
    }
}