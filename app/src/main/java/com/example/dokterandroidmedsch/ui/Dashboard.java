package com.example.dokterandroidmedsch.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.ui.jadwal.JadwalKuliah;
import com.example.dokterandroidmedsch.ui.konfirmasi.KonfirmasiJadwal;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class Dashboard extends AppCompatActivity{

    ImageButton button_jadwal_kuliah;
    ImageButton button_konfirmasi_jadwal;
    ImageButton button_keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        button_jadwal_kuliah =findViewById(R.id.btn_jadwal_kuliah);
        button_konfirmasi_jadwal = findViewById(R.id.btn_daftar_dokter);
        button_keluar = findViewById(R.id.btn_keluar);

        button_jadwal_kuliah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, JadwalKuliah.class);
                startActivity(intent);
            }
        });

        button_konfirmasi_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, KonfirmasiJadwal.class);
                startActivity(intent);
            }
        });

        button_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

}

