package com.example.dokterandroidmedsch.ui.jadwal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.get.JadwalByTanggal;

import java.util.ArrayList;
import java.util.List;

public class AdapterJadwalKuliah extends RecyclerView.Adapter<AdapterJadwalKuliah.JadwalKuliahHolder> {

    private List<JadwalByTanggal> list_jadwal;
    private JadwalClickListener clickListener;

    public AdapterJadwalKuliah(List<JadwalByTanggal> list_jadwal_dokter) {
        this.list_jadwal = list_jadwal_dokter;
        notifyDataSetChanged();
    }

    public void SetOnChangedStatus(JadwalClickListener listener_callback){
        this.clickListener = listener_callback;
    }

    @NonNull
    @Override
    public JadwalKuliahHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_jadwal, parent, false);
        return new JadwalKuliahHolder(view,clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalKuliahHolder holder, int position) {
        holder.tv_matkul.setText(list_jadwal.get(position).getMataKuliah());
        holder.tv_ruangKelas.setText(list_jadwal.get(position).getRuangKelas());
        if (list_jadwal.get(position).getStatus() == 1){
            holder.tv_status.setText("Siap Belajar");
        }
        holder.tv_jam.setText(list_jadwal.get(position).getJam());

    }

    @Override
    public int getItemCount() {
        return list_jadwal.size();
    }

    public class JadwalKuliahHolder extends RecyclerView.ViewHolder {
        TextView tv_jam, tv_matkul, tv_ruangKelas, tv_status;
        Button btn_status;
        JadwalByTanggal jadwal;

        public JadwalKuliahHolder(@NonNull View itemView, JadwalClickListener clickListener) {
            super(itemView);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            tv_matkul = itemView.findViewById(R.id.tv_matkul);
            tv_ruangKelas = itemView.findViewById(R.id.tv_ruang_kelas);
            tv_status = itemView.findViewById(R.id.tv_status);
            btn_status = itemView.findViewById(R.id.btn_ubah_status);

            btn_status.setOnClickListener(v -> clickListener.onItemClick(jadwal, getAdapterPosition()));
        }
    }

    public interface JadwalClickListener {
        void onItemClick(JadwalByTanggal jadwal_dokter, int position);
    }
}
