package com.example.dokterandroidmedsch.model;

public class ModelKonfirmasiJadwal {
    private String hariDosen;
    private String jamKuliahDosen;
    private String mataKuliahDosen;
    private String namaPengajar;
    private String ruangKelasDosen;
    private String statusDosen;

    public ModelKonfirmasiJadwal(String hari, String jamKuliah, String mataKuliah, String pengajar, String ruangKelas, String status) {
        hariDosen = hari;
        jamKuliahDosen = jamKuliah;
        mataKuliahDosen = mataKuliah;
        namaPengajar = pengajar;
        ruangKelasDosen = ruangKelas;
        statusDosen = status;

    }
    public String getHariKuliah() { return hariDosen; }

    public String getJamKuliah() { return jamKuliahDosen; }

    public String getMataKuliah() {
        return mataKuliahDosen;
    }

    public String getPengajar() { return namaPengajar; }

    public  String getRuangKelas() { return ruangKelasDosen; }

    public String getStatus() { return statusDosen;}
}
