
package com.shebuyit.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.web.servlet.ModelAndView;

import com.shebuyit.crawler.parser.StockCrawler;
import com.shebuyit.crawler.util.DataUtils;
import com.shebuyit.menu.RoleXmlTool;
import com.shebuyit.po.Product;
import com.shebuyit.po.Shop;
import com.shebuyit.po.User;
import com.shebuyit.service.IProductService;
import com.shebuyit.service.IShopService;
import com.shebuyit.vo.DojoGridVO;

public class MainController extends BaseMultiActionController {
	static final long serialVersionUID = 1L;
	
	private IProductService productService;
	
	private IShopService shopService;

	private String mainView;
	
	public static String SEARCH_PARAM_CATEGORY = "category";
	
	
	public IProductService getProductService() {
		return productService;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}


	public IShopService getShopService() {
		return shopService;
	}

	public void setShopService(IShopService shopService) {
		this.shopService = shopService;
	}
	
	
	public String getMainView() {
		return mainView;
	}

	public void setMainView(String mainView) {
		this.mainView = mainView;
	}

	public MainController() {
		super();
	}
	
	/**
	 * Return the main page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		User seeionUser = (User) request.getSession().getAttribute("user");	
		ModelAndView mv = new ModelAndView(mainView);	
		Map categoryMap = DataUtils.categoryMap();
		List<Shop> shops = shopService.queryShop(new Shop());
		Map<String,String> shopSkuMap = new HashMap<String,String>();
		for(Shop shop:shops){
			if(!shopSkuMap.containsKey(shop.getShopSku())){
				shopSkuMap.put(shop.getShopSku(),shop.getShopBrand());
			}
		}
		
		int inStockProduct = 0;
		int outOfStockProduct = 0;
//		if(seeionUser.getType()!=null&&seeionUser.getType().equals("admin")){
//			Product product = new Product();
//			product.setStock(1);
//			List<Product> inStockProducts = productService.queryProduct(product);
//			inStockProduct = inStockProducts.size();
//			
//			product.setStock(0);
//			List<Product> outOfStockProducts = productService.queryProduct(product);
//			outOfStockProduct = outOfStockProducts.size();
//			
//			
//			mv.addObject("inStockProduct", inStockProduct);
//			mv.addObject("outOfStockProduct", outOfStockProduct);
//		}
		mv.addObject("shopSkuMap", shopSkuMap);	
		mv.addObject("categoryMap", categoryMap);
		return mv;	
	}

	/**
	 * Return the menu in xml, using ajax.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView menu(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		response.setHeader("Cache-Control ", "no-cache ");
		response.setLocale(request.getLocale());
		
		try{
			PrintWriter out = response.getWriter();
			InputStream xml = null;
			InputStream xsl = null;
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null) {
				classLoader = this.getClass().getClassLoader();
			}		
			
			String locale = request.getLocale().toString();
		
			// get user function by role
			//String fileName = "roleProfile-" + locale.substring(0, 2) + ".xml";
			String fileName = "roleProfile-" + locale +".xml";	
			//it is hard coded now.
			String[] roles = {"Admin"};
			RoleXmlTool rxTool = new RoleXmlTool();
			rxTool.initFile(fileName);
			rxTool.initRole(roles);
			String xmlFile = rxTool.getXml().replaceAll("<url/>", "<url></url>");
			xml = new ByteArrayInputStream(xmlFile.getBytes("UTF-8"));
			
			xsl = classLoader.getResourceAsStream("menu.xsl");
			StreamSource xsltSource = new StreamSource(xsl);
			StreamSource xmlSource = new StreamSource(xml);

			TransformerFactory factory = TransformerFactory.newInstance();
			Templates temp;
			try {
				temp = factory.newTemplates(xsltSource);
				Transformer transf;
				transf = temp.newTransformer();
				transf.transform(xmlSource, new StreamResult(out));
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ModelAndView runStockCrawler(HttpServletRequest request, HttpServletResponse response, DojoGridVO dojoGridVO) throws IOException {
		String shopSku = request.getParameter("shopSku");
		String category = request.getParameter("category");		
		StockCrawler stockCrawler = new StockCrawler();
		Product product = new Product();
		product.setShopSku(shopSku);
		product.setCategory(category);
		List<Product> productList = productService.queryProduct(product);
		stockCrawler.setProductList(productList);
		new Thread(stockCrawler).start();		
		response.getWriter().print("000");
		return null; 
	}
	
}