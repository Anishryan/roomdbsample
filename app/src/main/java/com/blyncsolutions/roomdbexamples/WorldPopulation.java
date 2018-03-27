package com.blyncsolutions.roomdbexamples;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WorldPopulation {

	public WorldPopulation(String name, String email, String age, String blood, String address,byte[] img) {
		
		this.name = name;
		this.email = email;
		this.age = age;
		this.blood = blood;
		this.address = address;
		this.img = img;
	}
 

	@PrimaryKey (autoGenerate = true)
	private int id;

	@ColumnInfo(name = "student_name")
	private String name;

	@ColumnInfo(name = "student_email")
	private String email;

	@ColumnInfo(name = "student_age")
	private String age;

	@ColumnInfo(name = "student_blood")
	private String blood;

	@ColumnInfo(name = "student_address")
	private String address;

	@ColumnInfo(name = "student_img",typeAffinity = ColumnInfo.BLOB)
	private byte[] img;



	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAge() {
		return this.age;
	}
	public void setAge(String age)
	{
		this.age = age;
	}



	public String getBlood() {
		return this.blood;
	}
	public void setBlood(String blood)
	{
		this.blood = blood;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}

	public byte[] getImg() {
		return this.img;
	}
	public void setImg(byte[] img)
	{
		this.img = img;
	}

	
	
}
