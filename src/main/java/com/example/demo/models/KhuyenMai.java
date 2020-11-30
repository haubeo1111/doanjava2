package com.example.demo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="Khuyenmai")
public class KhuyenMai {
@Id
@Column(name="id")
private Long id;
@Column(name="iddt")
private Long iddt;
@Column(name="idpk")
private Long idpk;
@Column(name="makm")
private String makm;
@Column(name="tenkm")
private String tenkm;
@DateTimeFormat(pattern = "dd/MM/yyyy")
@Temporal(TemporalType.DATE)
private Date ngaybatdau;
@DateTimeFormat(pattern = "dd/MM/yyyy")
@Temporal(TemporalType.DATE)
private Date ngayketthuc;
@Column(name="maloaikm")
private String maloaikm;
@Column(name="tien")
private Long tien;
@Column(name="maloaidt")
private String maloaidt;
@Column(name="isdelete")
private int isdelete;

public Long getIdpk() {
	return idpk;
}
public void setIdpk(Long idpk) {
	this.idpk = idpk;
}
public KhuyenMai(Long id, Long iddt, Long idpk, String makm, String tenkm, Date ngaybatdau, Date ngayketthuc,
		String maloaikm, Long tien, String maloaidt, int isdelete) {
	super();
	this.id = id;
	this.iddt = iddt;
	this.idpk = idpk;
	this.makm = makm;
	this.tenkm = tenkm;
	this.ngaybatdau = ngaybatdau;
	this.ngayketthuc = ngayketthuc;
	this.maloaikm = maloaikm;
	this.tien = tien;
	this.maloaidt = maloaidt;
	this.isdelete = isdelete;
}
public Long getTien() {
	return tien;
}
public void setTien(Long tien) {
	this.tien = tien;
}
public KhuyenMai(Long id, Long iddt, String makm, String tenkm, Date ngaybatdau, Date ngayketthuc, String maloaikm,
		Long tien, String maloaidt, int isdelete) {
	super();
	this.id = id;
	this.iddt = iddt;
	this.makm = makm;
	this.tenkm = tenkm;
	this.ngaybatdau = ngaybatdau;
	this.ngayketthuc = ngayketthuc;
	this.maloaikm = maloaikm;
	this.tien = tien;
	this.maloaidt = maloaidt;
	this.isdelete = isdelete;
}
public String getMaloaidt() {
	return maloaidt;
}
public void setMaloaidt(String maloaidt) {
	this.maloaidt = maloaidt;
}

public Long getIddt() {
	return iddt;
}
public void setIddt(Long iddt) {
	this.iddt = iddt;
}
public KhuyenMai(Long id, Long iddt, String makm, String tenkm, Date ngaybatdau, Date ngayketthuc, String maloaikm,
		String maloaidt, int isdelete) {
	super();
	this.id = id;
	this.iddt = iddt;
	this.makm = makm;
	this.tenkm = tenkm;
	this.ngaybatdau = ngaybatdau;
	this.ngayketthuc = ngayketthuc;
	this.maloaikm = maloaikm;
	this.maloaidt = maloaidt;
	this.isdelete = isdelete;
}
public KhuyenMai(Long id, String makm, String tenkm, Date ngaybatdau, Date ngayketthuc, String maloaikm,
		String maloaidt, int isdelete) {
	super();
	this.id = id;
	this.makm = makm;
	this.tenkm = tenkm;
	this.ngaybatdau = ngaybatdau;
	this.ngayketthuc = ngayketthuc;
	this.maloaikm = maloaikm;
	this.maloaidt = maloaidt;
	this.isdelete = isdelete;
}
public KhuyenMai(Long id, String makm, String tenkm, Date ngaybatdau, Date ngayketthuc, String maloaikm, int isdelete) {
	super();
	this.id = id;
	this.makm = makm;
	this.tenkm = tenkm;
	this.ngaybatdau = ngaybatdau;
	this.ngayketthuc = ngayketthuc;
	this.maloaikm = maloaikm;
	this.isdelete = isdelete;
}
public KhuyenMai() {
	super();
}
public KhuyenMai(Long id, int isdelete) {
	super();
	this.id = id;
	this.isdelete = isdelete;
}
public KhuyenMai(Long id) {
	super();
	this.id = id;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getMakm() {
	return makm;
}
public void setMakm(String makm) {
	this.makm = makm;
}
public String getTenkm() {
	return tenkm;
}
public void setTenkm(String tenkm) {
	this.tenkm = tenkm;
}
public Date getNgaybatdau() {
	return ngaybatdau;
}
public void setNgaybatdau(Date ngaybatdau) {
	this.ngaybatdau = ngaybatdau;
}
public Date getNgayketthuc() {
	return ngayketthuc;
}
public void setNgayketthuc(Date ngayketthuc) {
	this.ngayketthuc = ngayketthuc;
}
public String getMaloaikm() {
	return maloaikm;
}
public void setMaloaikm(String maloaikm) {
	this.maloaikm = maloaikm;
}
public int getIsdelete() {
	return isdelete;
}
public void setIsdelete(int isdelete) {
	this.isdelete = isdelete;
}


}
