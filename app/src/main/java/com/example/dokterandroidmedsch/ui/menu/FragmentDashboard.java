package com.example.dokterandroidmedsch.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.dokterandroidmedsch.databinding.FragmentDashboardBinding;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.ui.jadwal.JadwalKuliah;
import com.example.dokterandroidmedsch.ui.konfirmasi.KonfirmasiJadwal;

public class FragmentDashboard extends Fragment {

    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnJadwalKuliah.setOnClickListener(v -> {
            Intent intent = new Intent(FragmentDashboard.this.requireContext(), JadwalKuliah.class);
            startActivity(intent);
        });

        binding.btnKonfirmasiJadwal.setOnClickListener(v->{
            Intent intent = new Intent(FragmentDashboard.this.requireContext(), KonfirmasiJadwal.class);
            startActivity(intent);
        });

        binding.btnExitApps.setOnClickListener(v->{
            FragmentDashboard.this.getActivity().finish();
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
