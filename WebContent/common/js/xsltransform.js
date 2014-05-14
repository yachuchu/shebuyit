/*==============================================================
 * THIS PRODUCT CONTAINS RESTRICTED MATERIALS OF IBM              *
 * (C) COPYRIGHT IBM Corp., 2010                                  *
 * All Rights Reserved * Licensed Materials - Property of IBM     *
 * ============================================================   */

  function  processXsltForIE(sourceXml,sourceXslt){  
	 var doc = new ActiveXObject("MSXML2.DOMDocument.3.0");
     doc.loadXML(sourceXml);
     var docStyle = new ActiveXObject("MSXML2.FreeThreadedDOMDocument");
     docStyle.async = false;
     docStyle.load(sourceXslt);
     
     var docTemplate = new ActiveXObject("MSXML2.XSLTemplate");
     docTemplate.stylesheet = docStyle;             
    
     var processor = docTemplate.createProcessor();
     processor.input = doc;
     processor.transform();
     return processor.output;           
  }  
       
  function   processXsltForFirefox(sourceXml,sourceXslt){  
	 var oParser = new DOMParser();
     var xmlDoc = oParser.parseFromString(sourceXml,"text/xml");        
     xslDoc = document.implementation.createDocument("", "", null);
     xslDoc.async = false;  
     xslDoc.load(sourceXslt);     
	 var xsltProcessor = new XSLTProcessor();
     xsltProcessor.importStylesheet(xslDoc);
     var result = xsltProcessor.transformToDocument(xmlDoc);
     var xmls = new XMLSerializer();
     return xmls.serializeToString(result); 
  }
  
  function  processXslt(sourceXml,sourceXslt){  
	  if(window.navigator.userAgent.toLowerCase().indexOf('msie')   !=   -1){ 
	      return   processXsltForIE(sourceXml,sourceXslt);  
	  }else{
	      return   processXsltForFirefox(sourceXml,sourceXslt);	        
	  }
  }  
