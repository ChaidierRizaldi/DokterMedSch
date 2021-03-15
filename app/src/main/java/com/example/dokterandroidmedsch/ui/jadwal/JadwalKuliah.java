package com.example.dokterandroidmedsch.ui.jadwal;

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
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.ModelJadwalKuliah;
import com.example.dokterandroidmedsch.ui.Dashboard;

import java.util.ArrayList;

public class JadwalKuliah extends AppCompatActivity implements AdapterJadwalKuliah.JadwalClickListener {
    private RecyclerView rv_jadwal;
    private AdapterJadwalKuliah adapter;
    Dialog dialog_ubah_status;
    Button btn_submit;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jadwal_kuliah);

        dialog_ubah_status = new Dialog(JadwalKuliah.this);
        dialog_ubah_status.setContentView(R.layout.dialog_ubah_status);
        btn_submit = findViewById(R.id.btn_submit_status);
        dialog_ubah_status.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_ubah_status.getWindow().getAttributes().windowAnimations = R.style.Animation_Design_BottomSheetDialog;

        rv_jadwal = findViewById(R.id.rv_jadwal_kuliah);
        rv_jadwal.setHasFixedSize(true);
        rv_jadwal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<ModelJadwalKuliah> list_jadwal = new ArrayList<>();
        list_jadwal.add(new ModelJadwalKuliah("08.00-09.00", "Anatomi Tubuh", "Dr. Bambang", "3.04", "Siap Belajar"));
        list_jadwal.add(new ModelJadwalKuliah("08.00-09.00", "Anatomi Tubuh", "Dr. Bambang", "3.04", "Siap Belajar"));
        list_jadwal.add(new ModelJadwalKuliah("08.00-09.00", "Anatomi Tubuh", "Dr. Bambang", "3.04", "Siap Belajar"));
        list_jadwal.add(new ModelJadwalKuliah("08.00-09.00", "Anatomi Tubuh", "Dr. Bambang", "3.04", "Siap Belajar"));
        list_jadwal.add(new ModelJadwalKuliah("08.00-09.00", "Anatomi Tubuh", "Dr. Bambang", "3.04", "Siap Belajar"));
        list_jadwal.add(new ModelJadwalKuliah("08.00-09.00", "Anatomi Tubuh", "Dr. Bambang", "3.04", "Siap Belajar"));

        Log.d("Jadwal",list_jadwal.toString());


        adapter = new AdapterJadwalKuliah(list_jadwal, this);
        rv_jadwal.setAdapter(adapter);

        button_back = findViewById(R.id.btn_back_jadwal);
        button_back.setOnClickListener(view -> {
            Intent intent = new Intent(JadwalKuliah.this, Dashboard.class);
            startActivity(intent);
        });
    }


    @Override
    public void onItemClick(Integer position) {
        dialog_ubah_status.show();
    }
}