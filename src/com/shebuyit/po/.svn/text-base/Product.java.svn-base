
package com.shebuyit.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author john
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
@Entity
@Table(name="product")
public class Product implements Serializable {
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private static final long serialVersionUID = 0;

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public Product() {
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	private Long id;
	
	private String sku;
	
	private String attribute_set;

	private String category;

	private Date  created_time;
	
	private String chineseName;
	
	private String englishName;
	
	private String price;
	
	private String special_price;	

	private String description;
	
	private String shopSku;
	
	private Integer stock;
	
	private String productSku;
	
	private String orl_price;	
	
	private String sizeDescription= "<p><strong>Size in Detail:</strong></p><p>	<strong>NOTE:</strong> </p><div></div><ul><li>	Because of measuring by hands  errors must have been existed  controlled from 1 to 5 cm	</li>	<li>These dimensions are for reference only. Specific dimension varies from person to person</li></ul>";
		
	

	// zidingyi
	private Integer saleCount;
	
	private String shopCanal;
	
	private Integer isExport;
	
	private Integer isFreeSize;
	
	private Integer editStatus;
	
	private Integer sizeStatus;
	
	private String sb_brand= "";

	private String sb_shop= "";
	
	private String sb_stock_url= "";
	
	private String sb_color= "";

	private String sb_material= "";

	private String sb_style= "";
		
	private String sb_types= "";
	
	private String sb_season= "";
	
	private String sb_item= "";
	
	private String sb_neckline= "";
	
	private String sb_sleeve_length= "";

	private String sb_decoration= "";
	
	private String sb_pattern_type= "";
		
	private String sb_length= "";
	
	private String sb_collar= "";
	
	private String sb_placket= "";	
	
	private String sb_fabric= "";
	
	private String sb_silhouette= "";
	
	private String sb_pant_length= "";	
	
	private String sb_dresses_length= "";
	
	private String sb_fit_type= "";
	
	private String sb_waist_type= "";
		
	private String sb_closure_type= "";
	
	
	private String sb_gender= "";
	
	private String sb_sleeveType= "";
	
	private String sb_componentContent= "";
	
	private String sb_closureType= "";
	
	
	//magento
	private String store = "";
	
	private String type = "simple";
	
	private String root_category = "Root Catalog";
	
	private String product_websites = "base";

	private String country_of_manufacture = "";

	private String custom_design = "";

	private String custom_design_from = "";

	private String custom_design_to = "";

	private String custom_layout_update = "";

	private String enable_googlecheckout = "1";

	private String gallery = "";

	private String gift_message_available = "";

	private String has_options = "";

	private String image = "";

	private String image_label = "";

	private String media_gallery = "Media_gallery";

	private String meta_description = "";

	private String meta_keyword = "";

	private String meta_title = "";

	private String minimal_price = "";

	private String msrp = "";

	private String msrp_display_actual_price_type = "Use config";

	private String msrp_enabled = "Use config";

	private String name = "";

	private String news_from_date = "";

	private String news_to_date = "";

	private String options_container = "Product Info Column";

	private String page_layout = "";

	private String required_options = "";

	private String short_description = "";

	private String small_image = "";

	private String small_image_label = "";

	private String special_from_date = "";

	private String special_to_date = "";

	private String status = "1";

	private String tax_class_id = "0";

	private String thumbnail = "";

	private String thumbnail_label = "";

	private String updated_at = "";

	private String url_key = "";

	private String url_path = "";

	private String visibility = "4";

	private String weight = "";

	private String qty = "";

	private String min_qty = "0";

	private String use_config_min_qty = "1";

	private String is_qty_decimal = "0";

	private String backorders = "0";

	private String use_config_backorders = "1";

	private String min_sale_qty = "1";

	private String use_config_min_sale_qty = "1";

	private String max_sale_qty = "0";

	private String use_config_max_sale_qty = "1";

	private String is_in_stock = "1";

	private String notify_stock_qty = "";

	private String use_config_notify_stock_qty = "1";

	private String manage_stock = "0";

	private String use_config_manage_stock = "1";

	private String stock_status_changed_auto = "0";

	private String use_config_qty_increments = "1";

	private String qty_increments = "0";

	private String use_config_enable_qty_inc = "1";

	private String enable_qty_increments = "0";

	private String is_decimal_divided = "0";

	private String links_related_sku = "";

	private String links_related_position = "";

	private String links_crosssell_sku = "";

	private String links_crosssell_position = "";

	private String links_upsell_sku = "";

	private String links_upsell_position = "";

	private String associated_sku = "";

	private String associated_default_qty = "";

	private String associated_position = "";

	private String tier_price_website = "";

	private String tier_price_customer_group = "";

	private String tier_price_qty = "";

	private String tier_price_price = "";

	private String group_price_website = "";

	private String group_price_customer_group = "";

	private String group_price_price = "";
	
	
	private String time_start;
	
	private String time_end;
	
	
	private List<ProductMedia> product_medias =  new ArrayList<ProductMedia>();
	
	private List<ProductOption> product_options=  new ArrayList<ProductOption>();
	

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Long getId() {
		// begin-user-code
		return id;
		// end-user-code
	}

	/** 
	 * @param batch_ID the batch_ID to set
	 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
	 */
	public void setId(Long id) {
		// begin-user-code
		this.id = id;
		// end-user-code
	}

	@Column(length = 30)
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}
	
	
	@Column(length = 30)
	public String getShopSku() {
		return shopSku;
	}

	public void setShopSku(String shopSku) {
		this.shopSku = shopSku;
	}
		
	@Column(length = 30)
	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	@Column(scale = 8)
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}	
	
	@Column(scale = 8)	
	public Integer getIsExport() {
		return isExport;
	}

	public void setIsExport(Integer isExport) {
		this.isExport = isExport;
	}
	
	
	@Column(scale = 8)
	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	@Column(scale = 8)
	public Integer getIsFreeSize() {
		return isFreeSize;
	}

	public void setIsFreeSize(Integer isFreeSize) {
		this.isFreeSize = isFreeSize;
	}

	@Column(scale = 8)
	public void setEditStatus(Integer editStatus) {
		this.editStatus = editStatus;
	}
	
	public Integer getEditStatus() {
		return editStatus;
	}
	
	@Column(scale = 8)
	public void setSizeStatus(Integer sizeStatus) {
		this.sizeStatus = sizeStatus;
	}
	
	public Integer getSizeStatus() {
		return sizeStatus;
	}


	@Column(length = 30)
	public String getAttribute_set() {
		return attribute_set;
	}


	public void setAttribute_set(String attribute_set) {
		this.attribute_set = attribute_set;
	}
	

	@Column(length = 30)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated_time() {
		return created_time;
	}

	public void setCreated_time(Date  created_time) {
		this.created_time = created_time;
	}

	@Column(length = 500)
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Column(length = 500)
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	@Column(length = 30)
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column(length = 30)
	public String getSpecial_price() {
		return special_price;
	}

	public void setSpecial_price(String special_price) {
		this.special_price = special_price;
	}
	
	@Column(length = 30)
	public String getOrl_price() {
		return orl_price;
	}

	public void setOrl_price(String orl_price) {
		this.orl_price = orl_price;
	}
	

	@Lob 
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Lob
	public String getSizeDescription() {
		return sizeDescription;
	}

	public void setSizeDescription(String sizeDescription) {
		this.sizeDescription = sizeDescription;
	}

	@Column(length = 30)
	public String getSb_brand() {
		return sb_brand;
	}

	public void setSb_brand(String sb_brand) {
		this.sb_brand = sb_brand;
	}

	@Column(length = 500)
	public String getSb_shop() {
		return sb_shop;
	}

	public void setSb_shop(String sb_shop) {
		this.sb_shop = sb_shop;
	}	
	
	@Column(length = 500)
	public String getSb_stock_url() {
		return sb_stock_url;
	}

	public void setSb_stock_url(String sb_stock_url) {
		this.sb_stock_url = sb_stock_url;
	}

	@Column(length = 30)
	public String getSb_color() {
		return sb_color;
	}

	public void setSb_color(String sb_color) {
		this.sb_color = sb_color;
	}

	@Column(length = 30)
	public String getSb_material() {
		return sb_material;
	}

	public void setSb_material(String sb_material) {
		this.sb_material = sb_material;
	}

	@Column(length = 30)
	public String getSb_style() {
		return sb_style;
	}

	public void setSb_style(String sb_style) {
		this.sb_style = sb_style;
	}

	@Column(length = 30)
	public String getSb_types() {
		return sb_types;
	}

	public void setSb_types(String sb_types) {
		this.sb_types = sb_types;
	}

	@Column(length = 30)
	public String getSb_season() {
		return sb_season;
	}

	public void setSb_season(String sb_season) {
		this.sb_season = sb_season;
	}

	@Column(length = 30)
	public String getSb_item() {
		return sb_item;
	}

	public void setSb_item(String sb_item) {
		this.sb_item = sb_item;
	}

	@Column(length = 30)
	public String getSb_neckline() {
		return sb_neckline;
	}

	public void setSb_neckline(String sb_neckline) {
		this.sb_neckline = sb_neckline;
	}

	@Column(length = 30)
	public String getSb_sleeve_length() {
		return sb_sleeve_length;
	}

	public void setSb_sleeve_length(String sb_sleeve_length) {
		this.sb_sleeve_length = sb_sleeve_length;
	}

	@Column(length = 30)
	public String getSb_decoration() {
		return sb_decoration;
	}

	public void setSb_decoration(String sb_decoration) {
		this.sb_decoration = sb_decoration;
	}

	@Column(length = 30)
	public String getSb_pattern_type() {
		return sb_pattern_type;
	}

	public void setSb_pattern_type(String sb_pattern_type) {
		this.sb_pattern_type = sb_pattern_type;
	}

	@Column(length = 30)
	public String getSb_length() {
		return sb_length;
	}

	public void setSb_length(String sb_length) {
		this.sb_length = sb_length;
	}

	@Column(length = 30)
	public String getSb_collar() {
		return sb_collar;
	}

	public void setSb_collar(String sb_collar) {
		this.sb_collar = sb_collar;
	}

	@Column(length = 30)
	public String getSb_placket() {
		return sb_placket;
	}

	public void setSb_placket(String sb_placket) {
		this.sb_placket = sb_placket;
	}

	@Column(length = 30)
	public String getSb_fabric() {
		return sb_fabric;
	}

	public void setSb_fabric(String sb_fabric) {
		this.sb_fabric = sb_fabric;
	}

	@Column(length = 30)
	public String getSb_silhouette() {
		return sb_silhouette;
	}

	public void setSb_silhouette(String sb_silhouette) {
		this.sb_silhouette = sb_silhouette;
	}

	@Column(length = 30)
	public String getSb_pant_length() {
		return sb_pant_length;
	}

	public void setSb_pant_length(String sb_pant_length) {
		this.sb_pant_length = sb_pant_length;
	}

	@Column(length = 30)
	public String getSb_dresses_length() {
		return sb_dresses_length;
	}

	public void setSb_dresses_length(String sb_dresses_length) {
		this.sb_dresses_length = sb_dresses_length;
	}

	@Column(length = 30)
	public String getSb_fit_type() {
		return sb_fit_type;
	}

	public void setSb_fit_type(String sb_fit_type) {
		this.sb_fit_type = sb_fit_type;
	}

	@Column(length = 30)
	public String getSb_waist_type() {
		return sb_waist_type;
	}

	public void setSb_waist_type(String sb_waist_type) {
		this.sb_waist_type = sb_waist_type;
	}

	@Column(length = 30)
	public String getSb_closure_type() {
		return sb_closure_type;
	}

	public void setSb_closure_type(String sb_closure_type) {
		this.sb_closure_type = sb_closure_type;
	}
	
	

	@Column(length = 30)
	public String getSb_gender() {
		return sb_gender;
	}

	public void setSb_gender(String sb_gender) {
		this.sb_gender = sb_gender;
	}

	@Column(length = 30)
	public String getSb_sleeveType() {
		return sb_sleeveType;
	}

	public void setSb_sleeveType(String sb_sleeveType) {
		this.sb_sleeveType = sb_sleeveType;
	}

	@Column(length = 30)
	public String getSb_componentContent() {
		return sb_componentContent;
	}

	public void setSb_componentContent(String sb_componentContent) {
		this.sb_componentContent = sb_componentContent;
	}

	@Column(length = 30)
	public String getSb_closureType() {
		return sb_closureType;
	}

	public void setSb_closureType(String sb_closureType) {
		this.sb_closureType = sb_closureType;
	}

	@OneToMany(mappedBy = "product", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<ProductMedia> getProduct_medias() {
		return product_medias;
	}

	public void setProduct_medias(List<ProductMedia> product_medias) {
		this.product_medias = product_medias;
	}

	@OneToMany(mappedBy = "product", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	public List<ProductOption> getProduct_options() {
		return product_options;
	}

	public void setProduct_options(List<ProductOption> product_options) {
		this.product_options = product_options;
	}
	
	
	
	
	
	
	//magento

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRoot_category() {
		return root_category;
	}

	public void setRoot_category(String root_category) {
		this.root_category = root_category;
	}

	public String getProduct_websites() {
		return product_websites;
	}

	public void setProduct_websites(String product_websites) {
		this.product_websites = product_websites;
	}

	public String getCountry_of_manufacture() {
		return country_of_manufacture;
	}

	public void setCountry_of_manufacture(String country_of_manufacture) {
		this.country_of_manufacture = country_of_manufacture;
	}

	public String getCustom_design() {
		return custom_design;
	}

	public void setCustom_design(String custom_design) {
		this.custom_design = custom_design;
	}

	public String getCustom_design_from() {
		return custom_design_from;
	}

	public void setCustom_design_from(String custom_design_from) {
		this.custom_design_from = custom_design_from;
	}

	public String getCustom_design_to() {
		return custom_design_to;
	}

	public void setCustom_design_to(String custom_design_to) {
		this.custom_design_to = custom_design_to;
	}

	public String getCustom_layout_update() {
		return custom_layout_update;
	}

	public void setCustom_layout_update(String custom_layout_update) {
		this.custom_layout_update = custom_layout_update;
	}

	public String getEnable_googlecheckout() {
		return enable_googlecheckout;
	}

	public void setEnable_googlecheckout(String enable_googlecheckout) {
		this.enable_googlecheckout = enable_googlecheckout;
	}

	public String getGallery() {
		return gallery;
	}

	public void setGallery(String gallery) {
		this.gallery = gallery;
	}

	public String getGift_message_available() {
		return gift_message_available;
	}

	public void setGift_message_available(String gift_message_available) {
		this.gift_message_available = gift_message_available;
	}

	public String getHas_options() {
		return has_options;
	}

	public void setHas_options(String has_options) {
		this.has_options = has_options;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImage_label() {
		return image_label;
	}

	public void setImage_label(String image_label) {
		this.image_label = image_label;
	}

	public String getMedia_gallery() {
		return media_gallery;
	}

	public void setMedia_gallery(String media_gallery) {
		this.media_gallery = media_gallery;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	public String getMeta_keyword() {
		return meta_keyword;
	}

	public void setMeta_keyword(String meta_keyword) {
		this.meta_keyword = meta_keyword;
	}

	public String getMeta_title() {
		return meta_title;
	}

	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}

	public String getMinimal_price() {
		return minimal_price;
	}

	public void setMinimal_price(String minimal_price) {
		this.minimal_price = minimal_price;
	}

	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String getMsrp_display_actual_price_type() {
		return msrp_display_actual_price_type;
	}

	public void setMsrp_display_actual_price_type(
			String msrp_display_actual_price_type) {
		this.msrp_display_actual_price_type = msrp_display_actual_price_type;
	}

	public String getMsrp_enabled() {
		return msrp_enabled;
	}

	public void setMsrp_enabled(String msrp_enabled) {
		this.msrp_enabled = msrp_enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNews_from_date() {
		return news_from_date;
	}

	public void setNews_from_date(String news_from_date) {
		this.news_from_date = news_from_date;
	}

	public String getNews_to_date() {
		return news_to_date;
	}

	public void setNews_to_date(String news_to_date) {
		this.news_to_date = news_to_date;
	}

	public String getOptions_container() {
		return options_container;
	}

	public void setOptions_container(String options_container) {
		this.options_container = options_container;
	}

	public String getPage_layout() {
		return page_layout;
	}

	public void setPage_layout(String page_layout) {
		this.page_layout = page_layout;
	}

	public String getRequired_options() {
		return required_options;
	}

	public void setRequired_options(String required_options) {
		this.required_options = required_options;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getSmall_image() {
		return small_image;
	}

	public void setSmall_image(String small_image) {
		this.small_image = small_image;
	}

	public String getSmall_image_label() {
		return small_image_label;
	}

	public void setSmall_image_label(String small_image_label) {
		this.small_image_label = small_image_label;
	}

	public String getSpecial_from_date() {
		return special_from_date;
	}

	public void setSpecial_from_date(String special_from_date) {
		this.special_from_date = special_from_date;
	}

	public String getSpecial_to_date() {
		return special_to_date;
	}

	public void setSpecial_to_date(String special_to_date) {
		this.special_to_date = special_to_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTax_class_id() {
		return tax_class_id;
	}

	public void setTax_class_id(String tax_class_id) {
		this.tax_class_id = tax_class_id;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnail_label() {
		return thumbnail_label;
	}

	public void setThumbnail_label(String thumbnail_label) {
		this.thumbnail_label = thumbnail_label;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUrl_key() {
		return url_key;
	}

	public void setUrl_key(String url_key) {
		this.url_key = url_key;
	}

	public String getUrl_path() {
		return url_path;
	}

	public void setUrl_path(String url_path) {
		this.url_path = url_path;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getMin_qty() {
		return min_qty;
	}

	public void setMin_qty(String min_qty) {
		this.min_qty = min_qty;
	}

	public String getUse_config_min_qty() {
		return use_config_min_qty;
	}

	public void setUse_config_min_qty(String use_config_min_qty) {
		this.use_config_min_qty = use_config_min_qty;
	}

	public String getIs_qty_decimal() {
		return is_qty_decimal;
	}

	public void setIs_qty_decimal(String is_qty_decimal) {
		this.is_qty_decimal = is_qty_decimal;
	}

	public String getBackorders() {
		return backorders;
	}

	public void setBackorders(String backorders) {
		this.backorders = backorders;
	}

	public String getUse_config_backorders() {
		return use_config_backorders;
	}

	public void setUse_config_backorders(String use_config_backorders) {
		this.use_config_backorders = use_config_backorders;
	}

	public String getMin_sale_qty() {
		return min_sale_qty;
	}

	public void setMin_sale_qty(String min_sale_qty) {
		this.min_sale_qty = min_sale_qty;
	}

	public String getUse_config_min_sale_qty() {
		return use_config_min_sale_qty;
	}

	public void setUse_config_min_sale_qty(String use_config_min_sale_qty) {
		this.use_config_min_sale_qty = use_config_min_sale_qty;
	}

	public String getMax_sale_qty() {
		return max_sale_qty;
	}

	public void setMax_sale_qty(String max_sale_qty) {
		this.max_sale_qty = max_sale_qty;
	}

	public String getUse_config_max_sale_qty() {
		return use_config_max_sale_qty;
	}

	public void setUse_config_max_sale_qty(String use_config_max_sale_qty) {
		this.use_config_max_sale_qty = use_config_max_sale_qty;
	}

	public String getIs_in_stock() {
		return is_in_stock;
	}

	public void setIs_in_stock(String is_in_stock) {
		this.is_in_stock = is_in_stock;
	}

	public String getNotify_stock_qty() {
		return notify_stock_qty;
	}

	public void setNotify_stock_qty(String notify_stock_qty) {
		this.notify_stock_qty = notify_stock_qty;
	}

	public String getUse_config_notify_stock_qty() {
		return use_config_notify_stock_qty;
	}

	public void setUse_config_notify_stock_qty(String use_config_notify_stock_qty) {
		this.use_config_notify_stock_qty = use_config_notify_stock_qty;
	}

	public String getManage_stock() {
		return manage_stock;
	}

	public void setManage_stock(String manage_stock) {
		this.manage_stock = manage_stock;
	}

	public String getUse_config_manage_stock() {
		return use_config_manage_stock;
	}

	public void setUse_config_manage_stock(String use_config_manage_stock) {
		this.use_config_manage_stock = use_config_manage_stock;
	}

	public String getStock_status_changed_auto() {
		return stock_status_changed_auto;
	}

	public void setStock_status_changed_auto(String stock_status_changed_auto) {
		this.stock_status_changed_auto = stock_status_changed_auto;
	}

	public String getUse_config_qty_increments() {
		return use_config_qty_increments;
	}

	public void setUse_config_qty_increments(String use_config_qty_increments) {
		this.use_config_qty_increments = use_config_qty_increments;
	}

	public String getQty_increments() {
		return qty_increments;
	}

	public void setQty_increments(String qty_increments) {
		this.qty_increments = qty_increments;
	}

	public String getUse_config_enable_qty_inc() {
		return use_config_enable_qty_inc;
	}

	public void setUse_config_enable_qty_inc(String use_config_enable_qty_inc) {
		this.use_config_enable_qty_inc = use_config_enable_qty_inc;
	}

	public String getEnable_qty_increments() {
		return enable_qty_increments;
	}

	public void setEnable_qty_increments(String enable_qty_increments) {
		this.enable_qty_increments = enable_qty_increments;
	}

	public String getIs_decimal_divided() {
		return is_decimal_divided;
	}

	public void setIs_decimal_divided(String is_decimal_divided) {
		this.is_decimal_divided = is_decimal_divided;
	}

	public String getLinks_related_sku() {
		return links_related_sku;
	}

	public void setLinks_related_sku(String links_related_sku) {
		this.links_related_sku = links_related_sku;
	}

	public String getLinks_related_position() {
		return links_related_position;
	}

	public void setLinks_related_position(String links_related_position) {
		this.links_related_position = links_related_position;
	}

	public String getLinks_crosssell_sku() {
		return links_crosssell_sku;
	}

	public void setLinks_crosssell_sku(String links_crosssell_sku) {
		this.links_crosssell_sku = links_crosssell_sku;
	}

	public String getLinks_crosssell_position() {
		return links_crosssell_position;
	}

	public void setLinks_crosssell_position(String links_crosssell_position) {
		this.links_crosssell_position = links_crosssell_position;
	}

	public String getLinks_upsell_sku() {
		return links_upsell_sku;
	}

	public void setLinks_upsell_sku(String links_upsell_sku) {
		this.links_upsell_sku = links_upsell_sku;
	}

	public String getLinks_upsell_position() {
		return links_upsell_position;
	}

	public void setLinks_upsell_position(String links_upsell_position) {
		this.links_upsell_position = links_upsell_position;
	}

	public String getAssociated_sku() {
		return associated_sku;
	}

	public void setAssociated_sku(String associated_sku) {
		this.associated_sku = associated_sku;
	}

	public String getAssociated_default_qty() {
		return associated_default_qty;
	}

	public void setAssociated_default_qty(String associated_default_qty) {
		this.associated_default_qty = associated_default_qty;
	}

	public String getAssociated_position() {
		return associated_position;
	}

	public void setAssociated_position(String associated_position) {
		this.associated_position = associated_position;
	}

	public String getTier_price_website() {
		return tier_price_website;
	}

	public void setTier_price_website(String tier_price_website) {
		this.tier_price_website = tier_price_website;
	}

	public String getTier_price_customer_group() {
		return tier_price_customer_group;
	}

	public void setTier_price_customer_group(String tier_price_customer_group) {
		this.tier_price_customer_group = tier_price_customer_group;
	}

	public String getTier_price_qty() {
		return tier_price_qty;
	}

	public void setTier_price_qty(String tier_price_qty) {
		this.tier_price_qty = tier_price_qty;
	}

	public String getTier_price_price() {
		return tier_price_price;
	}

	public void setTier_price_price(String tier_price_price) {
		this.tier_price_price = tier_price_price;
	}

	public String getGroup_price_website() {
		return group_price_website;
	}

	public void setGroup_price_website(String group_price_website) {
		this.group_price_website = group_price_website;
	}

	public String getGroup_price_customer_group() {
		return group_price_customer_group;
	}

	public void setGroup_price_customer_group(String group_price_customer_group) {
		this.group_price_customer_group = group_price_customer_group;
	}

	public String getGroup_price_price() {
		return group_price_price;
	}

	public void setGroup_price_price(String group_price_price) {
		this.group_price_price = group_price_price;
	}
	

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
	@Column(length = 30)
	public String getShopCanal() {
		return shopCanal;
	}

	public void setShopCanal(String shopCanal) {
		this.shopCanal = shopCanal;
	}

}