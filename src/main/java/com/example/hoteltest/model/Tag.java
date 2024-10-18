package com.example.hoteltest.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tag {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name; // e.g., "food", "drink", "service", etc.

    @Column(nullable = true)
    private String whoCreatedTheTagHuh;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    // Constructors, getters, setters
    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getWhoCreatedTheTagHuh() {
		return whoCreatedTheTagHuh;
	}

	public void setWhoCreatedTheTagHuh(String whoCreatedTheTagHuh) {
		this.whoCreatedTheTagHuh = whoCreatedTheTagHuh;
	}

    

}
