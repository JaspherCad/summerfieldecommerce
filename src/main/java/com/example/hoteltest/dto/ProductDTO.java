package com.example.hoteltest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.hoteltest.model.Product;
import com.example.hoteltest.model.Tag;
import com.example.hoteltest.model.User;


public class ProductDTO {

	private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String category;
    private StoreResponseDTO storeResponseDTO;
    private List<String> tagNames;
    private LocalDateTime createdAt;
    private boolean promoted;
    private Integer block;
    private Integer lot;
    private boolean proExtraB; //false
    private Integer proExtra; //99
    private String imgSrc; //null
    private boolean offB; //true
    private boolean gcash;
    private boolean doDelivery;

    
    public ProductDTO(Product product) {
		super();
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.quantity = product.getQuantity();
		this.category = product.getCategory();
		
		if (product.getStore().getId() != null) {
            this.sellerId = product.getStore().getId(); // Set the sellerId if the seller is not null
        }
//		this.storeResponseDTO = new StoreResponseDTO(product.getStore()); causes recursion
		this.tagNames = product.getTags().stream()
                 .map(Tag::getName) // Extracting the tag names
                 .collect(Collectors.toList());
		this.promoted = product.isFeatured();
		this.createdAt = product.getCreatedAt();
		this.block = product.getStore().getBlock();
		this.lot = product.getStore().getLot();
		
		//defaults
		this.proExtraB = false;
		this.proExtra = 99;
		this.imgSrc = product.getImgSrc();
		this.offB= true;
		
		this.gcash = product.getStore().isGcash();
		this.doDelivery = product.getStore().isDoDelivery();

	}

    public ProductDTO() {}
	private Long sellerId; // The seller associated with the product

	private Long storeId;

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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public StoreResponseDTO getStoreResponseDTO() {
		return storeResponseDTO;
	}

	public void setStoreResponseDTO(StoreResponseDTO storeResponseDTO) {
		this.storeResponseDTO = storeResponseDTO;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isFeatured() {
		return promoted;
	}

	public void setFeatured(boolean isFeatured) {
		this.promoted = isFeatured;
	}

	public Integer getBlock() {
		return block;
	}

	public void setBlock(Integer block) {
		this.block = block;
	}

	public Integer getLot() {
		return lot;
	}

	public void setLot(Integer lot) {
		this.lot = lot;
	}

	public boolean isPromoted() {
		return promoted;
	}

	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	public boolean isProExtraB() {
		return proExtraB;
	}

	public void setProExtraB(boolean proExtraB) {
		this.proExtraB = proExtraB;
	}

	public Integer getProExtra() {
		return proExtra;
	}

	public void setProExtra(Integer proExtra) {
		this.proExtra = proExtra;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public boolean isOffB() {
		return offB;
	}

	public void setOffB(boolean offB) {
		this.offB = offB;
	}

	public boolean isGcash() {
		return gcash;
	}

	public void setGcash(boolean gcash) {
		this.gcash = gcash;
	}

	public boolean isDoDelivery() {
		return doDelivery;
	}

	public void setDoDelivery(boolean doDelivery) {
		this.doDelivery = doDelivery;
	}


	
    
    
}
