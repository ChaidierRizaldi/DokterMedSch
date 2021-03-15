package com.example.dokterandroidmedsch.model;

public class ModelJadwalKuliah {

    private String JamKuliah;
    private String MataKuliah;
    private String Pengajar;
    private String RuangKelas;
    private String Status;

    public ModelJadwalKuliah(String jamKuliah, String mataKuliah, String pengajar, String ruangKelas, String status) {
        JamKuliah = jamKuliah;
        MataKuliah = mataKuliah;
        Pengajar = pengajar;
        RuangKelas = ruangKelas;
        Status = status;
    }
    public String getJamKuliah() { return JamKuliah; }

    public String getMataKuliah() {
        return MataKuliah;
    }

    public String getPengajar() {
        return Pengajar;
    }

    public  String getRuangKelas() { return RuangKelas; }

    public String getStatus() { return Status;}
}
