package com.example.dokterandroidmedsch.ui.konfirmasi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dokterandroidmedsch.R;
import com.example.dokterandroidmedsch.model.get.JadwalDokter;

import java.util.List;

public class AdapterKonfirmasiJadwal extends RecyclerView.Adapter<AdapterKonfirmasiJadwal.KonfirmasiViewHolder> {
    private KonfirmasiClickListener clickListener;
    private List<JadwalDokter> list_jadwal;

    public AdapterKonfirmasiJadwal(List<JadwalDokter> list_jadwal) {
        this.list_jadwal = list_jadwal;
//        this.clickListener = listener_callback;
    }

    public void setData (List<JadwalDokter> list_jadwal){
        this.list_jadwal = list_jadwal;
        notifyDataSetChanged();
    }

    public void setOnClickListener(KonfirmasiClickListener listenerCallback){
        this.clickListener = listenerCallback;
    }

    @NonNull
    @Override
    public KonfirmasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal, parent, false);
        return new KonfirmasiViewHolder(view);
    }

    public void deleteItem(int position){
        this.list_jadwal.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list_jadwal.size());
    }

    @Override
    public void onBindViewHolder(@NonNull KonfirmasiViewHolder holder, int position) {
        final JadwalDokter data_jadwal = list_jadwal.get(position);

        holder.tv_hari.setText(list_jadwal.get(position).getTanggal());
        holder.tv_jam.setText(list_jadwal.get(position).getJam());
        holder.tv_matkul.setText(list_jadwal.get(position).getMataKuliah());
        holder.tv_ruangKelas.setText(list_jadwal.get(position).getRuangKelas());

        if (list_jadwal.get(position).getStatus() == 0){
            holder.tv_status.setText("PENDING");
        }
        holder.tv_nama_blok.setText(list_jadwal.get(position).getNamaBlok());

        holder.btn_konfirmasi.setOnClickListener(v -> {
            clickListener.onKonfrimasiClick(data_jadwal, position);
            deleteItem(holder.getAdapterPosition());
        });

        holder.btn_tolak.setOnClickListener(v -> {
            clickListener.onTolakClick(data_jadwal, position);
            deleteItem(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return list_jadwal.size();
    }

    public class KonfirmasiViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hari, tv_jam, tv_matkul, tv_nama_blok, tv_ruangKelas, tv_status;
        Button btn_konfirmasi, btn_tolak;

        public KonfirmasiViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hari = itemView.findViewById(R.id.tv_date);
            tv_jam = itemView.findViewById(R.id.tv_jam_dosen);
            tv_matkul = itemView.findViewById(R.id.tv_matkul_dosen);
            tv_ruangKelas = itemView.findViewById(R.id.tv_ruang_kelas_dosen);
            btn_konfirmasi = itemView.findViewById(R.id.btn_konfirmasi_jadwal);
            btn_tolak = itemView.findViewById(R.id.btn_tolak_jadwal);
            tv_status = itemView.findViewById(R.id.tv_status_dosen);
            tv_nama_blok = itemView.findViewById(R.id.tv_nama_blok);
        }
    }

    public interface KonfirmasiClickListener {
        void onKonfrimasiClick(JadwalDokter confirmJadwal, int position);
        void onTolakClick(JadwalDokter rejectJadwal, int position);
    }
}
