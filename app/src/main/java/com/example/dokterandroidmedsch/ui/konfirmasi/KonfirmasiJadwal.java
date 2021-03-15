package com.example.dokterandroidmedsch.ui.konfirmasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.ModelKonfirmasiJadwal;
import com.example.dokterandroidmedsch.ui.Dashboard;

import java.util.ArrayList;

public class KonfirmasiJadwal extends AppCompatActivity {
    private RecyclerView rv_konfirmasi_jadwal;
    private AdapterKonfirmasiJadwal adapter;
    ArrayList list_jadwal;
    Dialog dialog_konfirmasi_jadwal;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_konfirmasi_jadwal);

        rv_konfirmasi_jadwal = findViewById(R.id.rv_jadwal_dokter);
        rv_konfirmasi_jadwal.setHasFixedSize(true);
        rv_konfirmasi_jadwal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        list_jadwal = new ArrayList<ModelKonfirmasiJadwal>();
        list_jadwal.add(new ModelKonfirmasiJadwal("Jumat/29/Januari/2021","09.00 - 10.00", "Anatomi Tubuh", "Dr. Bambang", "3.09", "Menunggu Konfirmasi"));
        list_jadwal.add(new ModelKonfirmasiJadwal("Jumat/29/Januari/2021","09.00 - 10.00", "Anatomi Tubuh", "Dr. Bambang", "3.09", "Menunggu Konfirmasi"));
        list_jadwal.add(new ModelKonfirmasiJadwal("Jumat/29/Januari/2021","09.00 - 10.00", "Anatomi Tubuh", "Dr. Bambang", "3.09", "Menunggu Konfirmasi"));
        list_jadwal.add(new ModelKonfirmasiJadwal("Jumat/29/Januari/2021","09.00 - 10.00", "Anatomi Tubuh", "Dr. Bambang", "3.09", "Menunggu Konfirmasi"));
        list_jadwal.add(new ModelKonfirmasiJadwal("Jumat/29/Januari/2021","09.00 - 10.00", "Anatomi Tubuh", "Dr. Bambang", "3.09", "Menunggu Konfirmasi"));
        Log.d("Jadwal", list_jadwal.toString());

        adapter = new AdapterKonfirmasiJadwal(list_jadwal);
        rv_konfirmasi_jadwal.setAdapter(adapter);

        button_back = findViewById(R.id.btn_back_konfirmasi);
        button_back.setOnClickListener(view -> {
            Intent intent = new Intent(KonfirmasiJadwal.this, Dashboard.class);
            startActivity(intent);
        });
        adapter.onClickListener(new AdapterKonfirmasiJadwal.KonfirmasiClickListener() {
            @Override
            public void onKonfrimasiClick(View v, Integer position) {

            }

            @Override
            public void onTolakClick(View v, Integer position) {
                OpenDialog();
            }
        });
    }

    public void OpenDialog(){
        dialog_konfirmasi_jadwal = new Dialog(KonfirmasiJadwal.this);
        dialog_konfirmasi_jadwal.setContentView(R.layout.dialog_keterangan_tolak);
        dialog_konfirmasi_jadwal.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_konfirmasi = dialog_konfirmasi_jadwal.findViewById(R.id.btn_konfirmasi_tolak);
        Button btn_tolak = dialog_konfirmasi_jadwal.findViewById(R.id.btn_batal_tolak);


        dialog_konfirmasi_jadwal.create();
        dialog_konfirmasi_jadwal.show();

        btn_konfirmasi.setOnClickListener(v -> {
            dialog_konfirmasi_jadwal.dismiss();
        });

        btn_tolak.setOnClickListener(v->{
            dialog_konfirmasi_jadwal.dismiss();
        });
    }
}