package com.example.dokterandroidmedsch.ui.jadwal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.databinding.ActivityJadwalKuliahBinding;
import com.example.dokterandroidmedsch.model.get.JadwalByTanggal;
import com.example.dokterandroidmedsch.model.get.ListBlok;
import com.example.dokterandroidmedsch.ui.Dashboard;
import com.example.dokterandroidmedsch.utils.Retrofit;
import com.example.dokterandroidmedsch.utils.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalKuliah extends AppCompatActivity{
    private RecyclerView rv_jadwal;
    private ActivityJadwalKuliahBinding binding;
    private AdapterJadwalKuliah adapter;
    Dialog dialog_ubah_status;
    SharedPreferences preferences;
    Button btn_submit;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJadwalKuliahBinding.inflate(getLayoutInflater());
        View view =  binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        preferences = new SharedPreferences(this);
        binding.rvJadwalKuliah.setLayoutManager(new LinearLayoutManager(JadwalKuliah.this, LinearLayoutManager.VERTICAL, false));
        
        getDataBlok();

        dialog_ubah_status = new Dialog(JadwalKuliah.this);
        dialog_ubah_status.setContentView(R.layout.dialog_ubah_status);
        btn_submit = findViewById(R.id.btn_submit_status);
        dialog_ubah_status.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_ubah_status.getWindow().getAttributes().windowAnimations = R.style.Animation_Design_BottomSheetDialog;
        dialog_ubah_status.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        HashMap<String, String > detail_user = preferences.getDetailUser();
        int id_dokter = Integer.parseInt(detail_user.get(SharedPreferences.ID));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String waktu = date_format.format(calendar.getTime());

        Call<List<JadwalByTanggal>>  recent_date = Retrofit.endpoints().getJadwalByTanggal(id_dokter, 1, waktu);
        recent_date.enqueue(new Callback<List<JadwalByTanggal>>() {
            @Override
            public void onResponse(Call<List<JadwalByTanggal>> call, Response<List<JadwalByTanggal>> response) {
                if (response.isSuccessful()){
                    List<JadwalByTanggal> list_jadwal = response.body();
                    adapter = new AdapterJadwalKuliah(list_jadwal);
                    binding.rvJadwalKuliah.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<JadwalByTanggal>> call, Throwable t) {

            }
        });

      binding.btnBackJadwal.setOnClickListener(v->{
          onBackPressed();
      });
    }

    private void getDataBlok() {
        ArrayAdapter<String > blok_adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        ArrayList<String> data_blok = new ArrayList<>();

        blok_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.spinnerBlok.setAdapter(blok_adapter);

        HashMap<String, String > detail_user = preferences.getDetailUser();

        int id_dokter =Integer.parseInt(detail_user.get(SharedPreferences.ID));


        Call<List<ListBlok>> list_blok = Retrofit.endpoints().getBlok(id_dokter);

        list_blok.enqueue(new Callback<List<ListBlok>>() {
            @Override
            public void onResponse(Call<List<ListBlok>> call, Response<List<ListBlok>> response) {
                if (response.isSuccessful()){
                    List<ListBlok> list_blok_dokter= response.body();
                    data_blok.clear();

                    list_blok_dokter.forEach(listBlok -> {
                        data_blok.add(listBlok.getNamaBlok());
                    });

                    blok_adapter.addAll(data_blok);
                    blok_adapter.notifyDataSetChanged();

                    binding.spinnerBlok.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            int id_blok = position+1;

                            binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
                                int monthReal = month+1;

                                if (dayOfMonth<10){
                                    if (monthReal<10){
                                        String date_selected = year + "/" + 0+monthReal + "/" + 0+dayOfMonth;
                                        getJadwal(id_dokter,id_blok, date_selected );
                                    }else {
                                        String date_selected = year + "/" + monthReal + "/" + 0+dayOfMonth;
                                        getJadwal(id_dokter,id_blok, date_selected );
                                    }
                                }else{
                                    if (monthReal<10){
                                        String date_selected = year + "/" + 0+monthReal + "/" + +dayOfMonth;
                                        getJadwal(id_dokter,id_blok, date_selected );
                                    }else {
                                        String date_selected = year + "/" + +monthReal + "/" +dayOfMonth;
                                        getJadwal(id_dokter,id_blok, date_selected );
                                    }
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ListBlok>> call, Throwable t) {
                Toast.makeText(JadwalKuliah.this, "Gagal Mendapatkan Jadwal Kuliah", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getJadwal(int id_dokter, int id_blok, String tanggal){

        Call<List<JadwalByTanggal>> list_jadwal = Retrofit.endpoints().getJadwalByTanggal(id_dokter, id_blok, tanggal);
        list_jadwal.enqueue(new Callback<List<JadwalByTanggal>>() {
            @Override
            public void onResponse(Call<List<JadwalByTanggal>> call, Response<List<JadwalByTanggal>> response) {
                if (response.isSuccessful()){
                    List<JadwalByTanggal> response_list = response.body();
                    adapter = new AdapterJadwalKuliah(response_list);
                    binding.rvJadwalKuliah.setAdapter(adapter);
                    if (response_list.isEmpty()){
                        Toast.makeText(JadwalKuliah.this, "Tidak Ada Jadwal", Toast.LENGTH_LONG).show();
                    }
                    response_list.forEach(jadwalByTanggal -> {
                        adapter.SetOnChangedStatus((jadwal_dokter, position) -> {

                            dialog_ubah_status.show();
                        });
                    });
                }
            }

            @Override
            public void onFailure(Call<List<JadwalByTanggal>> call, Throwable t) {
                Log.e("Error Get Jadwal", t.getMessage());
            }
        });
    }
}