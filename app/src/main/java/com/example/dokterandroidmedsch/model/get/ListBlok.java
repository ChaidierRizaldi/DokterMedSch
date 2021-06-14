package com.example.dokterandroidmedsch.model.get;

import com.google.gson.annotations.SerializedName;

public class ListBlok{

	@SerializedName("id_blok")
	private int idBlok;

	@SerializedName("nama_blok")
	private String namaBlok;

	public int getIdBlok(){
		return idBlok;
	}

	public String getNamaBlok(){
		return namaBlok;
	}
}