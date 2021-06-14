package com.example.dokterandroidmedsch.model.get;

import com.google.gson.annotations.SerializedName;

public class ResponseUbahStatus{

	@SerializedName("idJadwal")
	private int idJadwal;

	@SerializedName("status")
	private int status;

	@SerializedName("note")
	private String note;

	public int getIdJadwal(){
		return idJadwal;
	}

	public int getStatus(){
		return status;
	}

	public String getNote(){ return note; }
}