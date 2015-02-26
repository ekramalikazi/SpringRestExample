package com.ekram.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

@XmlRootElement
public class Employee implements Serializable{

	private static final long serialVersionUID = -7788619177798333712L;
	
	private int id;
	
	@NotEmpty(message = "Please enter name.")
    @Size(min = 6, max = 15, message = "name must between 6 and 15 characters")
	private String name;
	
	
	private Date createdDate;
	
	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonSerialize(using=DateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}
	
	@XmlElement
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
