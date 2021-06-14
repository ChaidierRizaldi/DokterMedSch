package com.example.dokterandroidmedsch.model.get;

import com.google.gson.annotations.SerializedName;

public class JadwalDokter{

	@SerializedName("ruangKelas")
	private String ruangKelas;

	@SerializedName("idJadwal")
	private int idJadwal;

	@SerializedName("jam")
	private String jam;

	@SerializedName("mataKuliah")
	private String mataKuliah;

	@SerializedName("namaBlok")
	private String namaBlok;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("idBlok")
	private int idBlok;

	@SerializedName("namaPengajar")
	private String namaPengajar;

	@SerializedName("status")
	private int status;

	@SerializedName("idPengajar")
	private int idPengajar;

	public String getRuangKelas(){
		return ruangKelas;
	}

	public int getIdJadwal(){
		return idJadwal;
	}

	public String getJam(){
		return jam;
	}

	public String getMataKuliah(){
		return mataKuliah;
	}

	public String getNamaBlok(){
		return namaBlok;
	}

	public String getTanggal(){
		return tanggal;
	}

	public int getIdBlok(){
		return idBlok;
	}

	public String getNamaPengajar(){
		return namaPengajar;
	}

	public int getStatus(){
		return status;
	}

	public int getIdPengajar(){
		return idPengajar;
	}
}