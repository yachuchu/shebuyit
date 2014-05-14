package com.shebuyit.crawler.jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.google.api.GoogleAPI;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.shebuyit.po.Shop;

/**
 *  
 */
public abstract class Crawler  implements Runnable{
	
	public Shop shop;
	
	/**
	 * bing api
	 * 
	 * @param en
	 * @return
	 */
	public String translateEnToCineseGoogle(String ch) {		
		String result = "";
		GoogleAPI.setHttpReferrer("www.shedressup.com");

	    // Set the Google Translate API key
	    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
	    GoogleAPI.setKey("AIzaSyA0S7HnSRlkFBq1I_zVU_DBBqhQyy9rRuQ");
	    try {
	    	result = com.google.api.translate.Translate.DEFAULT.execute("Bonjour le monde", com.google.api.translate.Language.ENGLISH, com.google.api.translate.Language.CHINESE_SIMPLIFIED);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--google--result---"+result);
		return result;
	}

	/**
	 * bing api
	 * 
	 * @param en
	 * @return
	 */
	public String translateEnToCineseBing(String ch) {		
		String result = "";
		//Translate.setClientId("4d33ff74-7ffb-4ef0-809d-9f8fd696a854");
		//Translate.setClientSecret("pM+imUADlH2yjdHveIZ9bfc9WzqAkKaJuD5L9SF2ofc=");
		Translate.setClientId("4d33ff74-7ffb-4ef0-809d-9f8fd696a854");
		Translate.setClientSecret("tybdRQptgcPCfy+/FUsEoEQHn2dBxP0Wo9eEbIx7Hzc=");
		try {
			 result =  Translate.execute(ch, Language.CHINESE_SIMPLIFIED, Language.ENGLISH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--bing--result---"+result);
		return result;
	}
	
	/**
	 * youdao api
	 * 
	 * @param en
	 * @return
	 */
	public  String youDaoTranslateEnToCinese(String chName) {		
		System.out.println("--youdao--name---"+chName);		
		String result = "";
		try {
			String url = "http://fanyi.youdao.com/openapi.do?keyfrom=shebuy&key=903461930&type=data&doctype=xml&version=1.1&q="+chName;			
			String content = doGet(url);
			Document doc = Jsoup.parse(content);
			Elements listPages = doc.getElementsByTag("translation");
			if (listPages != null) {
				result = listPages.first().text();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = chName;
		}
		System.out.println("--youdao--result---"+result);
		return result;
	}

	/**
	 * Map
	 * 
	 * @return
	 */
	public Map<String, Object> getMap() {
		return new HashMap<String, Object>(0);
	}

	/**
	 * 
	 * 
	 * @param url
	 *          ַ
	 * @param dir
	 *            
	 * @throws IOException
	 */
	public void downloadFile(String url, String dir) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpProtocolParams.setUserAgent(httpClient.getParams(),"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		HttpGet httpGet = new HttpGet();
		httpGet.setURI(new java.net.URI(url));
		
		InputStream input = null;
		FileOutputStream output = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			input = entity.getContent();
			File file = new File(dir);
			output = FileUtils.openOutputStream(file);
			IOUtils.copy(input, output);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(output);
			IOUtils.closeQuietly(input);
		}
	}

	/**
	 * 
	 * 
	 * @param url
	 *            ַ
	 * @param params
	 *            
	 * @return
	 * @throws Exception
	 */
	public synchronized String doGet(String url, String... params)
			throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient(); //  
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 15000);  
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 15000);
		HttpProtocolParams.setUserAgent(httpClient.getParams(),"Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");
		String charset = "UTF-8";
		if (null != params && params.length >= 1) {
			charset = params[0];
		}
		HttpGet httpGet = new HttpGet(); // 
		String content = "";
		httpGet.setURI(new java.net.URI(url));
		try {
			HttpResponse response = httpClient.execute(httpGet); // ִ
			int resStatu = response.getStatusLine().getStatusCode(); // 
			if (resStatu == HttpStatus.SC_OK) { // 
				HttpEntity entity = response.getEntity(); // 
				if (entity != null) {
					
					if(entity.getContentEncoding()!=null&&entity.getContentEncoding().getValue().equals("gzip")) 
					{
						System.out.println(entity.getContentEncoding().getValue());//压缩类型 
						content = EntityUtils.toString(new GzipDecompressingEntity(entity)); 
					}else{
						content = EntityUtils.toString(entity, charset);
					}
					
				}
			}
		} catch (Exception e) {
			System.out.println("解析" + url + "异常!");
			e.printStackTrace();
		} finally {
			//
			httpGet.abort();
			httpClient.getConnectionManager().shutdown();
		}
		return content;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
