package com.example.dokterandroidmedsch.ui.konfirmasi;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.ModelKonfirmasiJadwal;

import java.util.ArrayList;

public class AdapterKonfirmasiJadwal extends RecyclerView.Adapter<AdapterKonfirmasiJadwal.KonfirmasiViewHolder> {
    private KonfirmasiClickListener clickListener;
    private ArrayList<ModelKonfirmasiJadwal> list_jadwal;

    public AdapterKonfirmasiJadwal(ArrayList<ModelKonfirmasiJadwal> list_jadwal) {
        this.list_jadwal = list_jadwal;
        this.list_jadwal.clear();
        this.list_jadwal.addAll(list_jadwal);
    }

    public void onClickListener(KonfirmasiClickListener listenerCallback){
        this.clickListener = listenerCallback;
    }
    @NonNull
    @Override
    public KonfirmasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal, parent, false);
        return new KonfirmasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KonfirmasiViewHolder holder, int position) {
        holder.tv_hari.setText(list_jadwal.get(position).getHariKuliah());
        holder.tv_jam.setText(list_jadwal.get(position).getJamKuliah());
        holder.tv_matkul.setText(list_jadwal.get(position).getMataKuliah());
        holder.tv_pengajar.setText(list_jadwal.get(position).getPengajar());
        holder.tv_ruangKelas.setText(list_jadwal.get(position).getRuangKelas());
        holder.tv_status.setText(list_jadwal.get(position).getStatus());
//        holder.btn_konfirmasi.setOnClickListener(v -> {
//            clickListener.onKonfrimasiClick(v, position);
//        });
//        holder.btn_tolak.setOnClickListener(v->{
//            clickListener.onTolakClick(v, position);
//        });
    }

    @Override
    public int getItemCount() {
        return list_jadwal.size();
    }

    public class KonfirmasiViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hari, tv_jam, tv_matkul, tv_pengajar, tv_ruangKelas, tv_status;
        Button btn_konfirmasi, btn_tolak;
        KonfirmasiClickListener clickListener;

        public KonfirmasiViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hari = itemView.findViewById(R.id.tv_date);
            tv_jam = itemView.findViewById(R.id.tv_jam_dosen);
            tv_matkul = itemView.findViewById(R.id.tv_matkul_dosen);
            tv_pengajar = itemView.findViewById(R.id.tv_pengajar_dosen);
            tv_ruangKelas = itemView.findViewById(R.id.tv_ruang_kelas_dosen);
            btn_konfirmasi = itemView.findViewById(R.id.btn_konfirmasi_jadwal);
            btn_tolak = itemView.findViewById(R.id.btn_tolak_jadwal);
            tv_status = itemView.findViewById(R.id.tv_status_dosen);

            btn_konfirmasi.setOnClickListener(v -> {
                clickListener.onKonfrimasiClick(itemView, getAdapterPosition());
            });

            btn_tolak.setOnClickListener(v -> {
                clickListener.onTolakClick(itemView, getAdapterPosition());
            });
//            this.clickListener = clickListener;
//
//            btn_konfirmasi.setOnClickListener(v -> clickListener.onKonfrimasiClick(getAdapterPosition()));
//            btn_tolak.setOnClickListener(v -> clickListener.onTolakClick(getAdapterPosition()));

        }

//        @Override
//        public void onClick(View v) {
//            clickListener.onKonfrimasiClick(getAdapterPosition());
//            clickListener.onTolakClick(getAdapterPosition());
//        }

//        @Override
//        public void onKonfrimasiClick(Integer position) {
//            clickListener.onKonfrimasiClick(getAdapterPosition());
//            tv_status.setText("Siap Belajar");
//
//        }
//
//        @Override
//        public void onTolakClick(Integer position) {
//            clickListener.onTolakClick(getAdapterPosition());
//        }
    }

    public interface KonfirmasiClickListener {
        void onKonfrimasiClick(View v, Integer position);
        void onTolakClick(View v, Integer position);
    }
}
