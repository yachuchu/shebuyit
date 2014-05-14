package com.shebuyit.vo;
import java.util.ArrayList;
import java.util.List;


public class ProductOption {
	
	private String custom_option_store = "";

	private String custom_option_type = "";

	private String custom_option_title = "";

	private String custom_option_is_required = "";

	private String custom_option_price = "";

	private String custom_option_sku = "";

	private String custom_option_max_characters = "";

	private String custom_option_sort_order = "";
	
	//option row
	private String custom_option_row_title = "";

	private String custom_option_row_price = "";

	private String custom_option_row_sku = "";

	private String custom_option_row_sort = "";
	
	

	private List<ProductOptionRow> option_rows = new ArrayList<ProductOptionRow>();

	public String getCustom_option_is_required() {
		return custom_option_is_required;
	}

	public void setCustom_option_is_required(String custom_option_is_required) {
		this.custom_option_is_required = custom_option_is_required;
	}

	public String getCustom_option_max_characters() {
		return custom_option_max_characters;
	}

	public void setCustom_option_max_characters(String custom_option_max_characters) {
		this.custom_option_max_characters = custom_option_max_characters;
	}

	public String getCustom_option_price() {
		return custom_option_price;
	}

	public void setCustom_option_price(String custom_option_price) {
		this.custom_option_price = custom_option_price;
	}

	public List<ProductOptionRow> getOption_rows() {
		return option_rows;
	}

	public void setOption_rows(List<ProductOptionRow> option_rows) {
		this.option_rows = option_rows;
	}

	public String getCustom_option_sku() {
		return custom_option_sku;
	}

	public void setCustom_option_sku(String custom_option_sku) {
		this.custom_option_sku = custom_option_sku;
	}

	public String getCustom_option_sort_order() {
		return custom_option_sort_order;
	}

	public void setCustom_option_sort_order(String custom_option_sort_order) {
		this.custom_option_sort_order = custom_option_sort_order;
	}

	public String getCustom_option_store() {
		return custom_option_store;
	}

	public void setCustom_option_store(String custom_option_store) {
		this.custom_option_store = custom_option_store;
	}

	public String getCustom_option_title() {
		return custom_option_title;
	}

	public void setCustom_option_title(String custom_option_title) {
		this.custom_option_title = custom_option_title;
	}

	public String getCustom_option_type() {
		return custom_option_type;
	}

	public void setCustom_option_type(String custom_option_type) {
		this.custom_option_type = custom_option_type;
	}

	public String getCustom_option_row_price() {
		return custom_option_row_price;
	}

	public void setCustom_option_row_price(String custom_option_row_price) {
		this.custom_option_row_price = custom_option_row_price;
	}

	public String getCustom_option_row_sku() {
		return custom_option_row_sku;
	}

	public void setCustom_option_row_sku(String custom_option_row_sku) {
		this.custom_option_row_sku = custom_option_row_sku;
	}

	public String getCustom_option_row_sort() {
		return custom_option_row_sort;
	}

	public void setCustom_option_row_sort(String custom_option_row_sort) {
		this.custom_option_row_sort = custom_option_row_sort;
	}

	public String getCustom_option_row_title() {
		return custom_option_row_title;
	}

	public void setCustom_option_row_title(String custom_option_row_title) {
		this.custom_option_row_title = custom_option_row_title;
	}
	
	

}
