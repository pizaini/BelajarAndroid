package org.pizaini.mahasiswaonline.entities;

public class Mahasiswa {
	private Integer id;
	private String nim;
	private String nama;
	private String telp;
	private String alamat;
	
	public Mahasiswa() {
		super();
	}

	public Mahasiswa(Integer id, String nim, String nama, String telp,
			String alamat) {
		super();
		this.id = id;
		this.nim = nim;
		this.nama = nama;
		this.telp = telp;
		this.alamat = alamat;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNim() {
		return nim;
	}

	public void setNim(String nim) {
		this.nim = nim;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
}