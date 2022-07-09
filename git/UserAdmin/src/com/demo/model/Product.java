package com.demo.model;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Length;

public class Product {
    protected int id;
    protected String name;
    protected String description;
    protected String category;
    protected String image;
	protected int price;
    

	public Product() {}

    public Product(String name, String description, String category,String image,int price) {
        super();
        this.name = name;
        this.description = description;
        this.category = category;
        this.image= image;
        this.price = price;
    }

    public Product(int id, String name, String description, String category,String image,int price) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.image=image;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @NotEmpty(message="Name cannot be empty")
    @Pattern(regexp="([A-Z][a-z0-9]*\s*)*",message=": First letter of each word must be uppercase")
    @Length(min=3, max=50,message="Must be 3-50 Character")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @NotEmpty(message="Description cannot be empty")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @NotEmpty(message="Category cannot be empty")
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}