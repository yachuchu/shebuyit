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
@Table(name="productoptionrow")
public class ProductOptionRow implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String custom_option_row_title = "";

	private String custom_option_row_price = "0";

	private String custom_option_row_sku = "";

	private String custom_option_row_sort = "";
	
	private ProductOption productOption;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 30)
	public String getCustom_option_row_price() {
		return custom_option_row_price;
	}

	public void setCustom_option_row_price(String custom_option_row_price) {
		this.custom_option_row_price = custom_option_row_price;
	}

	@Column(length = 30)
	public String getCustom_option_row_sku() {
		return custom_option_row_sku;
	}

	
	public void setCustom_option_row_sku(String custom_option_row_sku) {
		this.custom_option_row_sku = custom_option_row_sku;
	}

	@Column(length = 30)
	public String getCustom_option_row_sort() {
		return custom_option_row_sort;
	}

	public void setCustom_option_row_sort(String custom_option_row_sort) {
		this.custom_option_row_sort = custom_option_row_sort;
	}

	@Column(length = 30)
	public String getCustom_option_row_title() {
		return custom_option_row_title;
	}

	public void setCustom_option_row_title(String custom_option_row_title) {
		this.custom_option_row_title = custom_option_row_title;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "option_id")})
	public ProductOption getProductOption() {
		return productOption;
	}

	public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}
	
	
	
	

}
