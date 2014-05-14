package com.shebuyit.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="productmedia")
public class ProductMedia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;
	
	private String media_attribute_id = "703";

	private String media_image = "";
	
	private String media_position = "";
	
	private String media_lable = "";
	
	private String media_is_disabled = "0";
	
	private Product product;
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 30)
	public String getMedia_attribute_id() {
		return media_attribute_id;
	}

	public void setMedia_attribute_id(String media_attribute_id) {
		this.media_attribute_id = media_attribute_id;
	}

	@Column(length = 500)
	public String getMedia_image() {
		return media_image;
	}

	public void setMedia_image(String media_image) {
		this.media_image = media_image;
	}

	@Column(length = 30)
	public String getMedia_is_disabled() {
		return media_is_disabled;
	}

	public void setMedia_is_disabled(String media_is_disabled) {
		this.media_is_disabled = media_is_disabled;
	}

	@Column(length = 30)
	public String getMedia_lable() {
		return media_lable;
	}

	public void setMedia_lable(String media_lable) {
		this.media_lable = media_lable;
	}

	@Column(length = 30)
	public String getMedia_position() {
		return media_position;
	}

	public void setMedia_position(String media_position) {
		this.media_position = media_position;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "product_id")})
	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
}
