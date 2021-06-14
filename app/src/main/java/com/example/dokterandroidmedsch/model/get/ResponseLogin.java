package com.example.dokterandroidmedsch.model.get;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("nama")
	private String nama;

	@SerializedName("idDokter")
	private int idDokter;

	public String getNama(){
		return nama;
	}

	public int getIdDokter(){
		return idDokter;
	}
}