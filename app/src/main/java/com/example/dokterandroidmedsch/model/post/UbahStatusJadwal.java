package com.example.dokterandroidmedsch.model.post;

import com.google.gson.annotations.SerializedName;

public class UbahStatusJadwal{

	@SerializedName("idJadwal")
	private int idJadwal;

	@SerializedName("status")
	private int status;

	@SerializedName("note")
	private String note;

	public void setIdJadwal(int idJadwal){
		this.idJadwal = idJadwal;
	}

	public int getIdJadwal(){
		return idJadwal;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setNote(String note) { this.note = note; }
}