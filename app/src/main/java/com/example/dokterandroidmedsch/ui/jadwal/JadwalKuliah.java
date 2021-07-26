package com.example.dokterandroidmedsch.ui.jadwal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.databinding.ActivityJadwalKuliahBinding;
import com.example.dokterandroidmedsch.model.Status;
import com.example.dokterandroidmedsch.model.get.DataBlok;
import com.example.dokterandroidmedsch.model.get.JadwalByTanggal;
import com.example.dokterandroidmedsch.model.get.ListBlok;
import com.example.dokterandroidmedsch.model.get.ResponseUbahStatus;
import com.example.dokterandroidmedsch.model.post.UbahStatusJadwal;
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
    private ActivityJadwalKuliahBinding binding;
    private AdapterJadwalKuliah adapter;

    Dialog dialog_ubah_status;
    SharedPreferences preferences;

    EditText et_note;
    String note_ubah_status;
    Spinner spinner_status;
    ArrayList<Status> list_status;
    ArrayAdapter<String> statusAdapter;

    Button btn_submit, btn_batal_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJadwalKuliahBinding.inflate(getLayoutInflater());
        View view =  binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(view);

        preferences = new SharedPreferences(this);
        binding.rvJadwalKuliah.setLayoutManager(new LinearLayoutManager(JadwalKuliah.this, LinearLayoutManager.HORIZONTAL, false));
        statusAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);

        getDataBlok();

        list_status = new ArrayList<>();
        list_status.add(new Status(1, "Disetujui"));
        list_status.add(new Status(2, "Dibatalkan"));

        dialog_ubah_status = new Dialog(JadwalKuliah.this);
        dialog_ubah_status.setContentView(R.layout.dialog_ubah_status);
        dialog_ubah_status.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_ubah_status.getWindow().getAttributes().windowAnimations = R.style.Animation_Design_BottomSheetDialog;
        dialog_ubah_status.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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
                            Call<DataBlok> data_blok = Retrofit.endpoints().getDataBlok(id_blok);

                            data_blok.enqueue(new Callback<DataBlok>() {
                                @Override
                                public void onResponse(Call<DataBlok> call, Response<DataBlok> response) {
                                    if (response.isSuccessful()){
                                        binding.cardMsgPendingSchedule.setVisibility(View.GONE);
                                        binding.cardMsgNoSchedule.setVisibility(View.GONE);

                                        DataBlok data_response = response.body();
                                        String masa_blok = data_response.getMasaBlok();

                                        int tahun_masa = Integer.parseInt(masa_blok.substring(0,4));
                                        int bulan_masa = Integer.parseInt(masa_blok.substring(5,7));
                                        int tanggal_masa = Integer.parseInt(masa_blok.substring(8));

                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat format_date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                                        calendar.set(tahun_masa, bulan_masa-1, tanggal_masa);
                                        long milliTime = calendar.getTimeInMillis();
                                        binding.calendarView.setDate(milliTime);

                                        checkDateSchedule(tahun_masa, bulan_masa, tanggal_masa, id_dokter, id_blok);

                                        binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
                                            int bulan_jadwal = month+1;
                                            checkDateSchedule(year, bulan_jadwal, dayOfMonth, id_dokter, id_blok);
                                        });

                                    }else {
                                        Log.e("Error Get Blok", String.valueOf(response.errorBody()));
                                    }
                                }

                                @Override
                                public void onFailure(Call<DataBlok> call, Throwable t) {
                                    Log.e("Error Get Data", t.getMessage());
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

                    binding.rvJadwalKuliah.setVisibility(View.VISIBLE);
                    binding.cardMsgNoSchedule.setVisibility(View.GONE);
                    binding.cardMsgPendingSchedule.setVisibility(View.GONE);

                    adapter = new AdapterJadwalKuliah(response_list);

                    binding.rvJadwalKuliah.setAdapter(adapter);

                    ArrayList<String> list_status_dialog = new ArrayList<>();

                    statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                    list_status.forEach(status -> {
                        list_status_dialog.add(status.getNama_status());
                    });

                    statusAdapter.addAll(list_status_dialog);

                    adapter.SetOnChangedStatus((jadwal_dokter, position) -> {
                        dialog_ubah_status.create();

                        et_note = dialog_ubah_status.findViewById(R.id.et_note);

                        spinner_status = dialog_ubah_status.findViewById(R.id.spinner_status);

                        spinner_status.setAdapter(statusAdapter);

                        btn_submit = dialog_ubah_status.findViewById(R.id.btn_submit_status);
                        btn_batal_submit = dialog_ubah_status.findViewById(R.id.btn_batal_status);

                        dialog_ubah_status.show();

                        btn_batal_submit.setOnClickListener(v -> {
                            dialog_ubah_status.dismiss();
                        });

                        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                int id_status = position+1;

                                btn_submit.setOnClickListener(v -> {
                                    note_ubah_status = et_note.getText().toString();

                                    UbahStatusJadwal req_body = new UbahStatusJadwal();
                                    req_body.setIdJadwal(jadwal_dokter.getIdJadwal());
                                    req_body.setStatus(id_status);
                                    req_body.setNote(note_ubah_status);

                                    if (note_ubah_status.isEmpty()){
                                        Toast.makeText(JadwalKuliah.this,"Note Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Call<ResponseUbahStatus> ubah_status_jadwal = Retrofit.endpoints().ubahStatus(req_body);
                                        ubah_status_jadwal.enqueue(new Callback<ResponseUbahStatus>() {
                                            @Override
                                            public void onResponse(Call<ResponseUbahStatus> call, Response<ResponseUbahStatus> response) {
                                                if (response.isSuccessful()){
                                                    getDataBlok();
                                                    dialog_ubah_status.dismiss();
                                                    Toast.makeText(JadwalKuliah.this, "Sukses Update Status", Toast.LENGTH_LONG).show();
                                                }else {
                                                    Toast.makeText(JadwalKuliah.this, "Gagal Update Status", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseUbahStatus> call, Throwable t) {
                                            }
                                        });
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    });

                    response_list.forEach(jadwalByTanggal -> {
                        if (jadwalByTanggal.getStatus() == 1 && jadwalByTanggal.getStatus() == 2){
                            binding.cardMsgPendingSchedule.setVisibility(View.GONE);
                            binding.cardMsgNoSchedule.setVisibility(View.GONE);

                        }else if (jadwalByTanggal.getStatus() == 0){
                            binding.rvJadwalKuliah.setVisibility(View.GONE);
                            binding.cardMsgPendingSchedule.setVisibility(View.VISIBLE);
                            binding.cardMsgNoSchedule.setVisibility(View.GONE);
                        }
                    });
                }else {
                    binding.cardMsgNoSchedule.setVisibility(View.VISIBLE);
                    binding.rvJadwalKuliah.setVisibility(View.GONE);
                    binding.cardMsgPendingSchedule.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<JadwalByTanggal>> call, Throwable t) {
                Log.e("Error Get Jadwal", t.getMessage());
            }
        });
    }

    private void checkDateSchedule(int tahun_masa, int bulan_masa, int tanggal_masa, int id_dokter, int id_blok){
        if (tanggal_masa <10 ){
            if (bulan_masa < 10){
                String date_selected = tahun_masa + "/" + 0+bulan_masa + "/" + 0+tanggal_masa;
                Log.d("Data day<10 mont<10", date_selected);

                getJadwal(id_dokter, id_blok, date_selected);

            }else {
                String date_selected = tahun_masa + "/" + bulan_masa + "/" + 0+tanggal_masa;
                Log.d("Data day<10 mont>10", date_selected);
                getJadwal(id_dokter, id_blok, date_selected);

            }
        }else {
            if (bulan_masa < 10){
                String date_selected = tahun_masa + "/" + 0+bulan_masa + "/" + tanggal_masa;
                Log.d("Data day>10 mont<10", date_selected);

                getJadwal(id_dokter, id_blok, date_selected);

            } else {
                String date_selected = tahun_masa + "/" + bulan_masa + "/" + tanggal_masa;
                Log.d("Data day>10 mont>10", date_selected);

                getJadwal(id_dokter, id_blok, date_selected);
            }
        }
    }
}