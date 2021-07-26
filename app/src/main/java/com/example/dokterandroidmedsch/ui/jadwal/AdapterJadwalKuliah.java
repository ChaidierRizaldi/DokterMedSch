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

import java.util.List;

public class AdapterJadwalKuliah extends RecyclerView.Adapter<AdapterJadwalKuliah.JadwalKuliahHolder> {

    private List<JadwalByTanggal> list_jadwal;
    public JadwalClickListener clickListener;

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
        return new JadwalKuliahHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalKuliahHolder holder, final int position) {
        final JadwalByTanggal data_jadwal = list_jadwal.get(position);

        holder.tv_matkul.setText(list_jadwal.get(position).getMataKuliah());
        holder.tv_ruangKelas.setText(list_jadwal.get(position).getRuangKelas());

        if (list_jadwal.get(position).getStatus() == 1){
            holder.tv_status.setText("Siap Belajar");
        }else if (list_jadwal.get(position).getStatus() == 2){
            holder.tv_status.setText("Jadwal Dibatalkan");
        }

        holder.tv_jam.setText(list_jadwal.get(position).getJam());
        holder.tv_blok.setText(list_jadwal.get(position).getNamaBlok());

        holder.btn_status.setOnClickListener(v -> {
            clickListener.onItemClick(data_jadwal, position);
        });
    }

    @Override
    public int getItemCount() {
        return list_jadwal.size();
    }

    public class JadwalKuliahHolder extends RecyclerView.ViewHolder {
        TextView tv_jam, tv_matkul, tv_ruangKelas, tv_status, tv_blok;
        Button btn_status;

        public JadwalKuliahHolder(@NonNull View itemView) {
            super(itemView);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            tv_matkul = itemView.findViewById(R.id.tv_matkul);
            tv_ruangKelas = itemView.findViewById(R.id.tv_ruang_kelas);
            tv_status = itemView.findViewById(R.id.tv_status);
            btn_status = itemView.findViewById(R.id.btn_update_status);
            tv_blok = itemView.findViewById(R.id.tv_blok_jadwal);
        }
    }

    public interface JadwalClickListener {
        void onItemClick(JadwalByTanggal jadwal_dokter, int position);
    }
}
