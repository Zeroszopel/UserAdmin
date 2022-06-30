package com.demo.model;

import javax.validation.constraints.*;

public class Category {
	private int id;
	private String name;
	public Category() {}
	public Category(String name) {
		this.name=name;
	}
	public Category(int id,String name) {
		this.id=id;
		this.name=name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotEmpty(message=" cannot be empty!")
	@Pattern(regexp="([A-Z][a-z]+)(\\s[A-Z][a-z]+)*",message=" first letter of each word must be capitalized")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
