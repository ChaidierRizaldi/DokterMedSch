package com.example.dokterandroidmedsch.ui.jadwal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.ModelJadwalKuliah;

import java.util.ArrayList;

public class AdapterJadwalKuliah extends RecyclerView.Adapter<AdapterJadwalKuliah.JadwalKuliahHolder> {

    private ArrayList<ModelJadwalKuliah> list_jadwal;
    private JadwalClickListener clickListener;

    public AdapterJadwalKuliah(ArrayList<ModelJadwalKuliah> list_jadwal, JadwalClickListener clickListener) {
        this.list_jadwal = list_jadwal;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public JadwalKuliahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_jadwal, parent, false);
        return new JadwalKuliahHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalKuliahHolder holder, int position) {
        holder.tv_jam.setText(list_jadwal.get(position).getJamKuliah());
        holder.tv_matkul.setText(list_jadwal.get(position).getMataKuliah());
        holder.tv_pengajar.setText(list_jadwal.get(position).getPengajar());
        holder.tv_ruangKelas.setText(list_jadwal.get(position).getRuangKelas());
        holder.tv_status.setText(list_jadwal.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return list_jadwal.size();
    }

    public class JadwalKuliahHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_jam, tv_matkul, tv_pengajar, tv_ruangKelas, tv_status;
        Button btn_status;
        JadwalClickListener clickListener;

        public JadwalKuliahHolder(@NonNull View itemView, JadwalClickListener clickListener) {
            super(itemView);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            tv_matkul = itemView.findViewById(R.id.tv_matkul);
            tv_pengajar = itemView.findViewById(R.id.tv_pengajar);
            tv_ruangKelas = itemView.findViewById(R.id.tv_ruang_kelas);
            tv_status = itemView.findViewById(R.id.tv_status);
            btn_status = itemView.findViewById(R.id.btn_ubah_status);

            this.clickListener = clickListener;

            btn_status.setOnClickListener(v -> clickListener.onItemClick(getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface JadwalClickListener {
        void onItemClick(Integer position);
    }
}
