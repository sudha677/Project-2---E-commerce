package com.sudha.ecommerce.dto;

import java.util.Objects;

public class Product {
	private String name;
	private String imageUrl;
	private String price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", imageUrl=" + imageUrl + ", price=" + price + "]";
	}

	public Product(String name, String imageUrl, String price) {
		super();
		this.name = name;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Product))
			return false;

		Product product = (Product) o;
		return Objects.equals(name, product.name) && Objects.equals(price, product.price);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, price);
	}

}