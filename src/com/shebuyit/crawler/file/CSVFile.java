package com.shebuyit.crawler.file;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.shebuyit.crawler.util.DataDictionary;
import com.shebuyit.crawler.util.DataUtils;
import com.shebuyit.crawler.util.SystemConfig;
import com.shebuyit.po.Product;
import com.shebuyit.po.ProductMedia;
import com.shebuyit.po.ProductOption;
import com.shebuyit.po.ProductOptionRow;


public class CSVFile {
		
	public static File createCSVFile(String category, List<Product> productList, String outPutPath,String filename) {
		System.out.println("---------createCSVFile--------------------------------------------------------------"+productList.size());
		
		LinkedList<String> relatedProductList = new LinkedList<String>() ;  
		
		if(productList.size()>5){
			for(int i =0;i<5;i++){
				relatedProductList.add(productList.get(i).getSku());
			}
			
		}

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(outPutPath + filename + ".csv");
			// csvFile.getParentFile().mkdir();
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// 
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile)), 1024);
			// cvs文件头部
			csvFileOutputStream.write(DataUtils.csvHeader(category));
			csvFileOutputStream.newLine();

			// 
			int productINum = 0;
			int num = 0;
			for (Iterator rowDataIterator = productList.iterator(); rowDataIterator.hasNext();) {
				num++;
				Product product = (Product) rowDataIterator.next();
				if(product!=null){
					// product基本信息
					writeProductBase(product, csvFileOutputStream);
	
					// product图片信息
					List<ProductMedia> product_medias = product.getProduct_medias();
					for (ProductMedia productMedia : product_medias) {
						writeProductMedia(product, productMedia, csvFileOutputStream);
					}
	
					// product option 信息
					List<ProductOption> product_options = product.getProduct_options();
					for (ProductOption option : product_options) {						
						List<ProductOptionRow> option_rows = option.getOption_rows();
						if(option_rows.size()>0){
							int i = 1;
							for (ProductOptionRow optionRow : option_rows) {
								if (i > 1) {
									option = new ProductOption();
								}
								option.setCustom_option_row_title(optionRow
										.getCustom_option_row_title());
								option.setCustom_option_row_price(optionRow
										.getCustom_option_row_price());
								option.setCustom_option_row_sku(optionRow
										.getCustom_option_row_sku());
								option.setCustom_option_row_sort(optionRow
										.getCustom_option_row_sort());
								writeProductOption(option, csvFileOutputStream);
								i++;
							}
						}else{
							writeProductOption(option, csvFileOutputStream);
						}
						
	
					}
					
					// 
					if(productINum>4){
						for(String productSku: relatedProductList){
							writeProductRelated(productSku, csvFileOutputStream);
						}
					}
					if(relatedProductList.size()>0){
					  relatedProductList.removeFirst();	
					}				
					relatedProductList.addLast(productList.get(productINum).getSku());
					
					productINum++;
				}
				
				csvFileOutputStream.flush();
			}
			System.out.println("---------export product:-----"+num);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}
	
	public static File createOutOfStockCSVFile(String category, List<Product> productList, String outPutPath,String filename) {
		System.out.println("---------createOutOfStockCSVFile--------------------------------------------------------------"+productList.size());
		
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(outPutPath + filename + ".csv");
			// csvFile.getParentFile().mkdir();
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// 
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile)), 1024);
			// cvs文件头部
			csvFileOutputStream.write(DataUtils.csvHeader(category));
			csvFileOutputStream.newLine();

			// 
			for (Iterator rowDataIterator = productList.iterator(); rowDataIterator.hasNext();) {

				Product product = (Product) rowDataIterator.next();
				if(product!=null){
					// product基本信息
					writeOutOfStockProductBase(product, csvFileOutputStream);
	
					// product图片信息
					List<ProductMedia> product_medias = product.getProduct_medias();
					for (ProductMedia productMedia : product_medias) {
						writeProductMedia(product, productMedia, csvFileOutputStream);
					}
	
					// product option 信息
					List<ProductOption> product_options = product.getProduct_options();
					for (ProductOption option : product_options) {						
						List<ProductOptionRow> option_rows = option.getOption_rows();
						if(option_rows.size()>0){
							int i = 1;
							for (ProductOptionRow optionRow : option_rows) {
								if (i > 1) {
									option = new ProductOption();
								}
								option.setCustom_option_row_title(optionRow
										.getCustom_option_row_title());
								option.setCustom_option_row_price(optionRow
										.getCustom_option_row_price());
								option.setCustom_option_row_sku(optionRow
										.getCustom_option_row_sku());
								option.setCustom_option_row_sort(optionRow
										.getCustom_option_row_sort());
								writeProductOption(option, csvFileOutputStream);
								i++;
							}
						}else{
							writeProductOption(option, csvFileOutputStream);
						}
						
	
					}
					
					// 
				}
				
				csvFileOutputStream.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}
	

	public static void writeProductBase(Product product,
			BufferedWriter csvFileOutputStream) throws IOException {
		// base info
		csvFileOutputStream.write(SystemConfig.site_sku+product.getSku());	
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_shop());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_brand());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getShopSku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getProductSku());
		csvFileOutputStream.write(",");	
		//product.getSb_stock_url()
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");		
		csvFileOutputStream.write(product.getStore());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAttribute_set());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getType());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCategory());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getRoot_category());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getProduct_websites());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCountry_of_manufacture());
		csvFileOutputStream.write(",");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		csvFileOutputStream.write(format.format(new Date()));
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_design());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_design_from());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_design_to());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_layout_update());
		csvFileOutputStream.write(",");
	

		//
		String orldescription = product.getDescription();				
		StringBuilder attributeMetaSb = new StringBuilder();
		attributeMetaSb.append(" ");
		if(!product.getAttribute_set().equals("Jewelry_set")){
			StringBuilder attributeSb = new StringBuilder();			
			attributeSb.append("<div class='smallTitle'><h1>"+product.getEnglishName()+"</h1></div><br><div><ul>");
			List<ProductOption> inproduct_options = product.getProduct_options();
			StringBuilder colorSb = new StringBuilder();
			for (ProductOption option : inproduct_options) {	
				String title = option.getCustom_option_title();
				if(title.equals("Color")){
					List<ProductOptionRow> option_rows = option.getOption_rows();
					if(option_rows.size()>0){
						for (ProductOptionRow optionRow : option_rows) {
							colorSb.append(optionRow.getCustom_option_row_title()).append(" / ");
						}
						
					}
				}
				
				

			}
			if(product.getSb_gender()!=null&&!product.getSb_gender().equals("")){
				attributeSb.append("<li><strong>Gender: </strong><i>"+product.getSb_gender()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_gender()).append("]").append(" ");
			}
			if(colorSb.length()>0){
				attributeSb.append("<li><strong>Color: </strong><i>"+colorSb.delete(colorSb.length()-3,colorSb.length()).toString()+"</i></li>");
			}else{
				if(product.getSb_color()!=null&&!product.getSb_color().equals("")){
					attributeSb.append("<li><strong>Color: </strong><i>"+product.getSb_color()+"</i></li>");
				}	
			}
					
			if(product.getSb_material()!=null&&!product.getSb_material().equals("")){
				attributeSb.append("<li><strong>Fabric composition: </strong><i>"+product.getSb_material()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_material()).append("]").append(" ");
			}
			if(product.getSb_componentContent()!=null&&!product.getSb_componentContent().equals("")){
				attributeSb.append("<li><strong>Component Content: </strong><i>"+product.getSb_componentContent()+"</i></li>");
			}
			if(product.getSb_style()!=null&&!product.getSb_style().equals("")){
				attributeSb.append("<li><strong>Style: </strong><i>"+product.getSb_style()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_style()).append("]").append(" ");
			}
			if(product.getSb_types()!=null&&!product.getSb_types().equals("")){
				attributeSb.append("<li><strong>Type: </strong><i>"+product.getSb_types()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_types()).append("]").append(" ");
			}
			boolean collar = true;
			if(product.getSb_collar()!=null&&!product.getSb_collar().equals("")){
				attributeSb.append("<li><strong>Collar: </strong><i>"+product.getSb_collar()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_collar()).append("]").append(" ");
				collar =false;
			}
			if(collar&&product.getSb_neckline()!=null&&!product.getSb_neckline().equals("")){
				attributeSb.append("<li><strong>Neckline: </strong><i>"+product.getSb_neckline()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_neckline()).append("]").append(" ");
			}
			if(product.getSb_sleeveType()!=null&&!product.getSb_sleeveType().equals("")){
				attributeSb.append("<li><strong>Sleeve Type: </strong><i>"+product.getSb_sleeveType()+"</i></li>");
			}
			if(product.getSb_sleeve_length()!=null&&!product.getSb_sleeve_length().equals("")){
				attributeSb.append("<li><strong>Sleeve Length: </strong><i>"+product.getSb_sleeve_length()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_sleeve_length()).append("]").append(" ");
			}
			if(product.getSb_closureType()!=null&&!product.getSb_closureType().equals("")){
				attributeSb.append("<li><strong>Closure Type: </strong><i>"+product.getSb_closure_type()+"</i></li>");
			}
			if(product.getSb_pattern_type()!=null&&!product.getSb_pattern_type().equals("")){
				attributeSb.append("<li><strong>Pattern Type: </strong><i>"+product.getSb_pattern_type()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_pattern_type()).append("]").append(" ");
			}
			if(product.getSb_decoration()!=null&&!product.getSb_decoration().equals("")){
				attributeSb.append("<li><strong>Decoration: </strong><i>"+product.getSb_decoration()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_decoration()).append("]").append(" ");
			}
			if(product.getSb_dresses_length()!=null&&!product.getSb_dresses_length().equals("")){
				attributeSb.append("<li><strong>Dress Length: </strong><i>"+product.getSb_dresses_length()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_dresses_length()).append("]").append(" ");
			}
			if(product.getSb_season()!=null&&!product.getSb_season().equals("")){
				attributeSb.append("<li><strong>Season: </strong><i>"+product.getSb_season()+"</i></li>");
			}
			attributeSb.append("</ul></div></br></br>");
			attributeSb.append(product.getDescription().substring(product.getDescription().indexOf("<div style='clear:both;'></div>")));
			orldescription = attributeSb.toString();
		}
		
		
		
		if(product.getShopSku().equals("1001")||product.getShopSku().equals("1002")){///sheinside

			csvFileOutputStream.write("<a name='size'></a><div style='clear:both;'></div>"+product.getDescription().replaceAll("，", " ").replaceAll("：", " ").replaceAll(",", " ").replaceAll("－", "-"));
			csvFileOutputStream.write(",");
		}
		else{				
			if(product.getSizeDescription()!=null&&product.getSizeDescription().length()>380){
				orldescription = orldescription.replaceAll("<a name='size'></a>", "");
				String orldescriptionUp = orldescription.substring(0, orldescription.lastIndexOf("<div style='clear:both;'></div>"));
				String orldescriptionDown = orldescription.substring(orldescription.lastIndexOf("<div style='clear:both;'></div>"));
				String description = orldescriptionUp+"<div style='clear:both;'></div><div><a name='size'></a>"+product.getSizeDescription()+"</div></br></br>"+orldescriptionDown;
				csvFileOutputStream.write(description);
				csvFileOutputStream.write(",");
			}else{
				csvFileOutputStream.write(orldescription);
				csvFileOutputStream.write(",");
			}
		}
		
		
		csvFileOutputStream.write(product.getEnable_googlecheckout());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGallery());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGift_message_available());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getHas_options());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getImage());
		csvFileOutputStream.write(",");
		//Image_label
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMedia_gallery());
		csvFileOutputStream.write(",");
		//Meta_description		
		csvFileOutputStream.write(product.getEnglishName().trim()+SystemConfig.site_csv);
		//csvFileOutputStream.write(product.getEnglishName()+" "+attributeMetaSb.toString()+" on sale at www.sheshining.com");
		csvFileOutputStream.write(",");
		//Meta_keyword
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		//Meta_title
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMinimal_price());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMsrp());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMsrp_display_actual_price_type());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMsrp_enabled());
		csvFileOutputStream.write(",");
		//Name
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getNews_from_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getNews_to_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getOptions_container());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getPage_layout());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getPrice().toString());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getRequired_options());
		csvFileOutputStream.write(",");
		//Short_description
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSmall_image());
		csvFileOutputStream.write(",");
		//Small_image_label
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSpecial_from_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSpecial_price()==null?"":product.getSpecial_price().toString());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSpecial_to_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getStatus());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTax_class_id());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getThumbnail());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getThumbnail_label());
		csvFileOutputStream.write(",");
		//Updated_at
		csvFileOutputStream.write(format.format(product.getCreated_time()));
		csvFileOutputStream.write(",");
		//Url_key
		csvFileOutputStream.write(product.getEnglishName().toLowerCase().trim().replaceAll("   ", "-").replaceAll("  ", "-").replaceAll(" ", "-"));
		csvFileOutputStream.write(",");
		//Url_path
		csvFileOutputStream.write(product.getEnglishName().toLowerCase().trim().replaceAll("   ", "-").replaceAll("  ", "-").replaceAll(" ", "-")+ ".html");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getVisibility());
		csvFileOutputStream.write(",");
		String weight = DataDictionary.getWeight(product.getCategory().replaceAll(" ", "_"));
		csvFileOutputStream.write(weight);
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getQty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMin_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_min_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getIs_qty_decimal());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getBackorders());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_backorders());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMin_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_min_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMax_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_max_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getIs_in_stock());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getNotify_stock_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_notify_stock_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getManage_stock());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_manage_stock());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getStock_status_changed_auto());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_qty_increments());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getQty_increments());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_enable_qty_inc());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getEnable_qty_increments());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getIs_decimal_divided());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_related_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_related_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_crosssell_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_crosssell_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_upsell_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_upsell_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAssociated_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAssociated_default_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAssociated_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_website());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_customer_group());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_price());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGroup_price_website());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGroup_price_customer_group());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGroup_price_price());
		csvFileOutputStream.write(",");
		
		
		//自定义属性
		csvFileOutputStream.write(product.getSb_color());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_material());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_style());
		csvFileOutputStream.write(",");	
		csvFileOutputStream.write(product.getSb_types());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_season());
		csvFileOutputStream.write(",");	
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");				
		csvFileOutputStream.write(product.getSb_neckline());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_sleeve_length());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_decoration());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_pattern_type());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_length());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_collar());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_placket());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_silhouette());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_dresses_length());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_waist_type());
		csvFileOutputStream.write(",");
		

		

		// media info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");

		// option info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.newLine();
	}

	public static void writeProductMedia(Product product, ProductMedia media,
			BufferedWriter csvFileOutputStream) throws IOException {
		// base info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		
		
		//自定义属性
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");

		

		// media info
		csvFileOutputStream.write(media.getMedia_attribute_id());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(media.getMedia_image());
		csvFileOutputStream.write(",");
		////Media_lable()
		csvFileOutputStream.write(product.getEnglishName());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(media.getMedia_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(media.getMedia_is_disabled());
		csvFileOutputStream.write(",");

		// option info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.newLine();
	}

	public static void writeProductOption(ProductOption option,
			BufferedWriter csvFileOutputStream) throws IOException {
//		 base info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		
		
		//自定义属性
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");


		// media info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");

		// option info
		csvFileOutputStream.write(option.getCustom_option_store());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_type());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_title());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_is_required());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_price());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_max_characters());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_sort_order());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_row_title());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_row_price());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_row_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(option.getCustom_option_row_sort());
		csvFileOutputStream.newLine();
	}
	
	public static void writeProductRelated(String productSku,BufferedWriter csvFileOutputStream) throws IOException {
//		 base info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(productSku);
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		
		
		//自定义属性
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");

		// media info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");

		// option info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.newLine();
		

	}
	
	public static void writeOutOfStockProductBase(Product product,
			BufferedWriter csvFileOutputStream) throws IOException {
		// base info
		csvFileOutputStream.write(SystemConfig.site_sku+product.getSku());	
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_shop());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_brand());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getShopSku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getProductSku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_stock_url().replaceAll("[^x00-xff]*", ""));
		csvFileOutputStream.write(",");		
		csvFileOutputStream.write(product.getStore());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAttribute_set());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getType());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCategory());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getRoot_category());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getProduct_websites());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCountry_of_manufacture());
		csvFileOutputStream.write(",");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		csvFileOutputStream.write(format.format(new Date()));
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_design());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_design_from());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_design_to());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getCustom_layout_update());
		csvFileOutputStream.write(",");
	

		//
		String orldescription = product.getDescription();				
		StringBuilder attributeMetaSb = new StringBuilder();
		attributeMetaSb.append(" ");
		if(!product.getAttribute_set().equals("Jewelry_set")){
			StringBuilder attributeSb = new StringBuilder();			
			attributeSb.append("<div class='smallTitle'><h1>"+product.getEnglishName()+"</h1></div><br><div><ul>");
			List<ProductOption> inproduct_options = product.getProduct_options();
			StringBuilder colorSb = new StringBuilder();
			for (ProductOption option : inproduct_options) {	
				String title = option.getCustom_option_title();
				if(title.equals("Color")){
					List<ProductOptionRow> option_rows = option.getOption_rows();
					if(option_rows.size()>0){
						for (ProductOptionRow optionRow : option_rows) {
							colorSb.append(optionRow.getCustom_option_row_title()).append(" / ");
						}
						
					}
				}
				
				

			}
			if(product.getSb_gender()!=null&&!product.getSb_gender().equals("")){
				attributeSb.append("<li><strong>Gender: </strong><i>"+product.getSb_gender()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_gender()).append("]").append(" ");
			}
			if(colorSb.length()>0){
				attributeSb.append("<li><strong>Color: </strong><i>"+colorSb.delete(colorSb.length()-3,colorSb.length()).toString()+"</i></li>");
			}else{
				if(product.getSb_color()!=null&&!product.getSb_color().equals("")){
					attributeSb.append("<li><strong>Color: </strong><i>"+product.getSb_color()+"</i></li>");
				}	
			}
					
			if(product.getSb_material()!=null&&!product.getSb_material().equals("")){
				attributeSb.append("<li><strong>Fabric composition: </strong><i>"+product.getSb_material()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_material()).append("]").append(" ");
			}
			if(product.getSb_componentContent()!=null&&!product.getSb_componentContent().equals("")){
				attributeSb.append("<li><strong>Component Content: </strong><i>"+product.getSb_componentContent()+"</i></li>");
			}
			if(product.getSb_style()!=null&&!product.getSb_style().equals("")){
				attributeSb.append("<li><strong>Style: </strong><i>"+product.getSb_style()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_style()).append("]").append(" ");
			}
			if(product.getSb_types()!=null&&!product.getSb_types().equals("")){
				attributeSb.append("<li><strong>Type: </strong><i>"+product.getSb_types()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_types()).append("]").append(" ");
			}
			boolean collar = true;
			if(product.getSb_collar()!=null&&!product.getSb_collar().equals("")){
				attributeSb.append("<li><strong>Collar: </strong><i>"+product.getSb_collar()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_collar()).append("]").append(" ");
				collar =false;
			}
			if(collar&&product.getSb_neckline()!=null&&!product.getSb_neckline().equals("")){
				attributeSb.append("<li><strong>Neckline: </strong><i>"+product.getSb_neckline()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_neckline()).append("]").append(" ");
			}
			if(product.getSb_sleeveType()!=null&&!product.getSb_sleeveType().equals("")){
				attributeSb.append("<li><strong>Sleeve Type: </strong><i>"+product.getSb_sleeveType()+"</i></li>");
			}
			if(product.getSb_sleeve_length()!=null&&!product.getSb_sleeve_length().equals("")){
				attributeSb.append("<li><strong>Sleeve Length: </strong><i>"+product.getSb_sleeve_length()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_sleeve_length()).append("]").append(" ");
			}
			if(product.getSb_closureType()!=null&&!product.getSb_closureType().equals("")){
				attributeSb.append("<li><strong>Closure Type: </strong><i>"+product.getSb_closure_type()+"</i></li>");
			}
			if(product.getSb_pattern_type()!=null&&!product.getSb_pattern_type().equals("")){
				attributeSb.append("<li><strong>Pattern Type: </strong><i>"+product.getSb_pattern_type()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_pattern_type()).append("]").append(" ");
			}
			if(product.getSb_decoration()!=null&&!product.getSb_decoration().equals("")){
				attributeSb.append("<li><strong>Decoration: </strong><i>"+product.getSb_decoration()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_decoration()).append("]").append(" ");
			}
			if(product.getSb_dresses_length()!=null&&!product.getSb_dresses_length().equals("")){
				attributeSb.append("<li><strong>Dress Length: </strong><i>"+product.getSb_dresses_length()+"</i></li>");
				attributeMetaSb.append("[").append(product.getSb_dresses_length()).append("]").append(" ");
			}
			if(product.getSb_season()!=null&&!product.getSb_season().equals("")){
				attributeSb.append("<li><strong>Season: </strong><i>"+product.getSb_season()+"</i></li>");
			}
			attributeSb.append("</ul></div></br></br>");
			attributeSb.append(product.getDescription().substring(product.getDescription().indexOf("<div style='clear:both;'></div>")));
			orldescription = attributeSb.toString();
		}
		
		
		
		if(product.getShopSku().equals("1001")){///sheinside
			csvFileOutputStream.write("<a name='size'></a><div style='clear:both;'></div>"+product.getDescription());
			csvFileOutputStream.write(",");
		}
		else{				
			if(product.getSizeDescription()!=null&&product.getSizeDescription().length()>380){
				orldescription = orldescription.replaceAll("<a name='size'></a>", "");
				String orldescriptionUp = orldescription.substring(0, orldescription.lastIndexOf("<div style='clear:both;'></div>"));
				String orldescriptionDown = orldescription.substring(orldescription.lastIndexOf("<div style='clear:both;'></div>"));
				String description = orldescriptionUp+"<div style='clear:both;'></div><div><a name='size'></a>"+product.getSizeDescription()+"</div></br></br>"+orldescriptionDown;
				csvFileOutputStream.write(description);
				csvFileOutputStream.write(",");
			}else{
				csvFileOutputStream.write(orldescription);
				csvFileOutputStream.write(",");
			}
		}
		
		
		csvFileOutputStream.write(product.getEnable_googlecheckout());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGallery());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGift_message_available());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getHas_options());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getImage());
		csvFileOutputStream.write(",");
		//Image_label
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMedia_gallery());
		csvFileOutputStream.write(",");
		//Meta_description		
		csvFileOutputStream.write(product.getEnglishName().trim()+SystemConfig.site_csv);
		//csvFileOutputStream.write(product.getEnglishName()+" "+attributeMetaSb.toString()+" on sale at www.sheshining.com");
		csvFileOutputStream.write(",");
		//Meta_keyword
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		//Meta_title
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMinimal_price());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMsrp());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMsrp_display_actual_price_type());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMsrp_enabled());
		csvFileOutputStream.write(",");
		//Name
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getNews_from_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getNews_to_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getOptions_container());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getPage_layout());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getPrice().toString());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getRequired_options());
		csvFileOutputStream.write(",");
		//Short_description
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSmall_image());
		csvFileOutputStream.write(",");
		//Small_image_label
		csvFileOutputStream.write(product.getEnglishName().trim());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSpecial_from_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSpecial_price()==null?"":product.getSpecial_price().toString());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSpecial_to_date());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getStatus());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTax_class_id());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getThumbnail());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getThumbnail_label());
		csvFileOutputStream.write(",");
		//Updated_at
		csvFileOutputStream.write(format.format(product.getCreated_time()));
		csvFileOutputStream.write(",");
		//Url_key
		csvFileOutputStream.write(product.getEnglishName().toLowerCase().trim().replaceAll("   ", "-").replaceAll("  ", "-").replaceAll(" ", "-"));
		csvFileOutputStream.write(",");
		//Url_path
		csvFileOutputStream.write(product.getEnglishName().toLowerCase().trim().replaceAll("   ", "-").replaceAll("  ", "-").replaceAll(" ", "-")+ ".html");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getVisibility());
		csvFileOutputStream.write(",");
		String weight = DataDictionary.getWeight(product.getCategory().replaceAll(" ", "_"));
		csvFileOutputStream.write(weight);
		csvFileOutputStream.write(",");
		//qty
		csvFileOutputStream.write("o");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMin_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_min_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getIs_qty_decimal());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getBackorders());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_backorders());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMin_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_min_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getMax_sale_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_max_sale_qty());
		csvFileOutputStream.write(",");
		//is in_stock
		csvFileOutputStream.write("0");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getNotify_stock_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_notify_stock_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getManage_stock());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_manage_stock());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getStock_status_changed_auto());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_qty_increments());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getQty_increments());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getUse_config_enable_qty_inc());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getEnable_qty_increments());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getIs_decimal_divided());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_related_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_related_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_crosssell_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_crosssell_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_upsell_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getLinks_upsell_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAssociated_sku());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAssociated_default_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getAssociated_position());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_website());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_customer_group());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_qty());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getTier_price_price());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGroup_price_website());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGroup_price_customer_group());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getGroup_price_price());
		csvFileOutputStream.write(",");
		
		
		//自定义属性
		csvFileOutputStream.write(product.getSb_color());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_material());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_style());
		csvFileOutputStream.write(",");	
		csvFileOutputStream.write(product.getSb_types());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_season());
		csvFileOutputStream.write(",");	
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");				
		csvFileOutputStream.write(product.getSb_neckline());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_sleeve_length());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_decoration());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_pattern_type());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_length());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_collar());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_placket());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_silhouette());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_dresses_length());
		csvFileOutputStream.write(",");
		csvFileOutputStream.write(product.getSb_waist_type());
		csvFileOutputStream.write(",");
		

		

		// media info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");

		// option info
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.write(",");
		csvFileOutputStream.write("");
		csvFileOutputStream.newLine();
	}
}
