package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="groupuser")
public class GroupUser {
	@Id
	@Column(name="id")
	private long id;
	@NotEmpty(message="khong de trong ma nhom")
	@Size(max = 150,message="ten ma nhom qua dai")
	@Column(name="magroup")
	private String magroup;
	@NotEmpty(message="khong de trong ten nhom")
	@Size(max = 150,message="ten nhom qua dai")
	@Column(name="tengroup")
	private String tengroup;
	@Column(name="chucnang")
	private String chucnang;
	@Column(name="isdelete")
	private int isdelete;
	
	
	
	public GroupUser(long id,
			@NotEmpty(message = "khong de trong ma nhom") @Size(max = 150, message = "ten ma nhom qua dai") String magroup,
			@NotEmpty(message = "khong de trong ten nhom") @Size(max = 150, message = "ten nhom qua dai") String tengroup,
			String chucnang, int isdelete) {
		super();
		this.id = id;
		this.magroup = magroup;
		this.tengroup = tengroup;
		this.chucnang = chucnang;
		this.isdelete = isdelete;
	}
	public String getChucnang() {
		return chucnang;
	}
	public void setChucnang(String chucnang) {
		this.chucnang = chucnang;
	}
	public GroupUser(long id, String magroup, String tengroup, int isdelete) {
		super();
		this.id = id;
		this.magroup = magroup;
		this.tengroup = tengroup;
		this.isdelete = isdelete;
	}
	public GroupUser() {
		super();
	}
	public GroupUser(long id) {
		super();
		this.id = id;
	}
	public GroupUser(long id, int isdelete) {
		super();
		this.id = id;
		this.isdelete = isdelete;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMagroup() {
		return magroup;
	}
	public void setMagroup(String magroup) {
		this.magroup = magroup;
	}
	public String getTengroup() {
		return tengroup;
	}
	public void setTengroup(String tengroup) {
		this.tengroup = tengroup;
	}
	
	public int getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	
}
