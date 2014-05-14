<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/deployJava.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ckeditor/ckeditor.js'/>" ></script>

<script type="text/javascript" src="<c:url value='/kindeditor-4.0.1/kindeditor-min.js'/>"></script>
<script type="text/javascript">
<%@ include file="/common/js/common.js" %>
<%@ include file="/common/js/pageInfo.js" %>
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojo.parser");
dojo.require("dijit.form.ComboBox");
dojo.require("dojo.io.iframe");
$(document).ready(function(){
	
	/* setup navigation, content boxes, etc... */
	Administry.setup();
	
	// validate form on keyup and submit
	var validator = $("#productform").validate({
		rules: {
			stock: {
				required: true
			},
			shopSku: {
				required: true
			},
			englishName: {
				required: true
			},
			category: {
				required: true
			},
			attributeSet: {
				required: true
			},
			price: {
				required: true
			},
			specialPrice: {
				required: true
			},
			description: {
				required: true
			}
			

		},
		messages: {
			stock: {
				required: "<fmt:message key='required.stock'/>"
			},
			shopSku: {
				required: "<fmt:message key='required.shopSku' />"
			},
			englishName: {
				required: "<fmt:message key='required.englishName' />"
			},
			category: {
				required: "<fmt:message key='required.category'/>"
			},
			attributeSet: {
				required: "<fmt:message key='required.attributeSet' />"
			},
			price: {
				required: "<fmt:message key='required.price' />"
			},
			specialPrice: {
				required: "<fmt:message key='required.specialPrice' />"
			},
			description: {
				required: "<fmt:message key='required.description' />"
			}
			
		},
		// the errorPlacement has to take the layout into account
		errorPlacement: function(error, element) {
			error.insertAfter(element.parent().find('label:first'));
		},
		// specifying a submitHandler prevents the default submit, good for the demo
		//submitHandler: function() {
					//	dojo.io.iframe.send({
			        	//	url: "<c:url value='/product/save.do'/>",  
				       	//	form: dojo.byId("productform"), 
				       	//	handleAs: "json",
				       	//	timeout:"15000",
				       	//	handle: function(response, ioArgs){
				       	//	if(response.returnCode == "000" || response.returnCode==000){	       					
				       	//		document.getElementById("successBox").style.display = "block";
			       		///	}else{
			       		//		document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
			       		//		document.getElementById("errorBox").style.display = "block";
			       		//	}         
				   		//},
				   		//method: 'POST'
				   	//	});
		//}

	});
	

});

function searchProduct(){
	window.location.href= "<c:url value='/product/search.do'/>";		
}

function sizeDemo(){
	window.open ('../sizeDemo.html','newwindow','height=450,width=800,top=300,left=500,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=yes, status=yes'); 	
}

function freeSizeDemo(){
	window.open ('../freeSizeDemo.html','newwindow','height=450,width=800,top=300,left=500,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=yes, status=yes'); 	
}

function translationList(){
	window.open ('<c:url value="/translation/list.do"/>','newwindow','height=450,width=810,top=300,left=400,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=yes, status=yes');	
}

function generatePrice(){
	var orlPricr = document.getElementById("orlPrice").value;
	showProgressBar();
	dojo.io.iframe.send({
		url: "<c:url value='/product/generatePrice.do'/>",  
   		form: dojo.byId("productform"), 
   		handleAs: "json",
   		timeout:"15000",
   		handle: function(response, ioArgs){
   		if(response.returnCode == "000" || response.returnCode==000){	       					
   			document.getElementById("successBox").style.display = "block";
   			document.getElementById("specialPrice").value=response.specialPrice;	
   			document.getElementById("price").value=response.price;
			}else{
				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
				document.getElementById("errorBox").style.display = "block";
			}    
   		hideProgressBar();
   		setTimeout('hiddenViewMessage()',5000);
		},
		method: 'POST'
		});																			 
}

function hiddenViewMessage() {	
	document.getElementById("successBox").style.display = "none";
	document.getElementById("errorBox").style.display = "none";			
}
</script>

</head>
<body>
<!-- Header -->
	<header id="top">
		<div class="wrapper">
			<!-- Title/Logo - can use text instead of image -->
			<div id="title"><span><fmt:message key="menu.logo" /></span></div>
			<!-- Top navigation -->
			<div id="topnav">
				<a href="<c:url value='/user/userInfo.do'/>"><img class="avatar" SRC="<c:url value='/img/user_32.png'/>" alt="" /></a>
				<fmt:message key="menu.logged_in_as" /> <b>${sessionScope.user.userName}</b>
				<span>|</span>  <a href="<c:url value='/login/logout.do'/>"><fmt:message key="menu.logout" /></a><br />
				
			</div>
			<!-- End of Top navigation -->
			<!-- Main navigation -->
			<nav id="menu">
				<ul class="sf-menu">
					<li><a HREF="<c:url value='/main/main.do'/>"><fmt:message key='navigation.home'/></a></li>
					<c:if test="${sessionScope.user.type=='admin'}">
					<li><a HREF="<c:url value='/shop/search.do'/>"><fmt:message key='navigation.shopManagement'/></a>
					</li>
					</c:if>
					<li class="current"><a HREF="<c:url value='/product/search.do'/>"><fmt:message key='navigation.productManagement'/></a>
						<ul>
							<li class="current">
								<a HREF="<c:url value='/product/search.do'/>"><fmt:message key='navigation.products'/></a>
							</li>
							<li>
								<a HREF="<c:url value='/storage/search.do'/>"><fmt:message key='navigation.outofstockProducts'/></a>
							</li>	
							<li>
								<a HREF="<c:url value='/quick/search.do'/>"><fmt:message key='navigation.quickEdit'/></a>
							</li>				
						</ul>
                    </li>
                    <li><a HREF="<c:url value='/order/search.do'/>"><fmt:message key='navigation.orderManagement'/></a>
						<ul>
							<li>
								<a HREF="<c:url value='/order/search.do'/>"><fmt:message key='navigation.orders'/></a>
							</li>
							<li>
								<a HREF="<c:url value='/item/search.do'/>"><fmt:message key='navigation.orderItem'/></a>
							</li>					
						</ul>
                    </li>	
                    <li><a HREF="<c:url value='/dict/search.do'/>"><fmt:message key='navigation.system'/></a>
                    	<ul>
							<li>
								<a HREF="<c:url value='/translation/search.do'/>"><fmt:message key='navigation.translation'/></a>
							</li>
							<c:if test="${sessionScope.user.type=='admin'}">
							<li>
								<a HREF="<c:url value='/dict/search.do'/>"><fmt:message key='navigation.dict'/></a>
							</li>	
							<li>
								<a HREF="<c:url value='/user/search.do'/>"><fmt:message key='navigation.user'/></a>
							</li>
							</c:if>				
						</ul>
                    </li>		
				</ul>
			</nav>
			<!-- End of Main navigation -->
			<!-- Aside links -->
			<aside>
			<c:choose>
               <c:when test="${sessionScope.GLOBAL_LANGUAGE=='zh'}">
                  <a href="#" onclick="changeLanguage('English')"><fmt:message key='common.english'/></a> &middot; <b><fmt:message key='common.chinese'/></b> 
               </c:when>
               <c:when test="${sessionScope.GLOBAL_LANGUAGE=='en'}">
                  <b><fmt:message key='common.english'/></b> &middot; <a href="#" onclick="changeLanguage('Chinese')"><fmt:message key='common.chinese'/></a> 
               </c:when>
               <c:otherwise>
                  <a href="#" onclick="changeLanguage('English')"><fmt:message key='common.english'/></a> &middot; <b><fmt:message key='common.chinese'/></b> 
               </c:otherwise>
             </c:choose>
			</aside>
			<!-- End of Aside links -->
		</div>
	</header>	
	<!-- End of Header -->
	<!-- Page title -->
	<div id="pagetitle">
		<div class="wrapper">
			<h1><fmt:message key="navigation.productManagement" /> &rarr; <span><fmt:message key="navigation.products" /></span></h1>
			<!-- Quick search box -->
			<form action="" method="get"><input class="" type="text" id="q" name="q" /></form>
		</div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
		<div class="wrapper">
				<!-- Left column/section -->
				<section class="column width6 first">					

					<div class="clear">&nbsp;</div>	
					<div id="successBox" class="box box-success"  style="display:none;">
					<table  cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
						<td width="90%" align="left"><span><fmt:message key='common.success'/></span></td>
						<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenSuccessBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
						</tr>
					</table>
					</div>
					<div id="errorBox" class="box box-error"  style="display:none;">
					<table  cellspacing="0" cellpadding="0" width="100%" border="0">
						<tr>
						<td width="90%" align="left"><span id="errorBoxMesg"></span></td>
						<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenErrorBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
						</tr>
					</table>
					</div>	
					<c:choose>
		               <c:when test="${returnCode=='000'}">
		                  <div id="successBox" class="box box-success" >
							<table  cellspacing="0" cellpadding="0" width="100%" border="0">
								<tr>
								<td width="90%" align="left"><span><fmt:message key='common.success'/></span></td>
								<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenSuccessBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
								</tr>
							</table>
							</div>
		               </c:when>
		               <c:when test="${returnCode!=null&&returnCode!='000'}">
		                   <div id="errorBox" class="box box-error">
								<table  cellspacing="0" cellpadding="0" width="100%" border="0">
									<tr>
									<td width="90%" align="left"><span id="errorBoxMesg">${returnCode}</span></td>
									<td width="10%" align="right"><a href="#" style="border-bottom: 0px solid;a:hover{ border-bottom: 0px; }" onclick="hiddenErrorBox()"><img src="<c:url value='/img/cancel.png'/>"></a></td>
									</tr>
								</table>
							</div>
		               </c:when>
		               <c:otherwise>
		                  
		               </c:otherwise>
		             </c:choose>		
					
						
					<div class="clear">&nbsp;</div>

					
					<form id="productform" method="post" action="<c:url value='/product/save.do'/>">	
							<p class="box">
						    <input type="submit" class="btn btn-green big" value="<fmt:message key='button.submit'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;						    
						    <input type="button" class="btn btn-green big" onclick="searchProduct()" value="<fmt:message key='button.back'/>"/>
						    </p>
					
					        <p>
					        <label><fmt:message key='product.productId'/><fmt:message key='common.colon'/></label>
						    <input type="hidden" id="productId" class="" value="${product.id}" name="productId"/>
						    ${product.id}						    
						    </p>
						    
							<p>
							  <label><fmt:message key='product.productSku'/><fmt:message key='common.colon'/></label>
							  ${product.sku}
							</p>
							
							<p>
							  <label><fmt:message key='product.productUrl'/><fmt:message key='common.colon'/></label>
							  <a href="${product.sb_shop}" target="_blank">${product.sb_shop}</a>
							</p>
							
							<p>
							  <label class="required" for="stock"><fmt:message key='product.stock'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="stock" class="" value="${product.stock}" name="stock"/>
							</p>
							
							<p>
							  <label class="required" for="shopSku"><fmt:message key='product.shopSku'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shopSku" class="half" value="${product.shopSku}" name="shopSku"/>
							</p>
							
							
							
							<p>
							  <label><fmt:message key='product.chineseName'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="chineseName" class="half" value="${product.chineseName}" name="chineseName"/>
							</p>

							<p>
							  <label class="required" for="englishName"><fmt:message key='product.englishName'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="englishName" class="half" value="${product.englishName}" name="englishName"/>
							  <c:choose>
				               <c:when test="${product.editStatus==1}">
				                  <input type="checkbox" name="editStatus" value ="1" checked="checked">
				               </c:when>	
				               <c:otherwise>
				                  <input type="checkbox" name="editStatus" value ="1">
				               </c:otherwise>
				             </c:choose>
				             <fmt:message key='common.message.english_name_check'/>&nbsp;&nbsp;
				             <a href="#" onclick="translationList()"><fmt:message key='navigation.translation'/></a>
							 
							</p>
							
							<p>
							  <label class="required" for="category"><fmt:message key='product.category'/><fmt:message key='common.colon'/></label><br/>
							  <select id="category" name="category" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${categoryMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.category}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
							<p>
							  <label class="required" for="attributeSet"><fmt:message key='product.attributeSet'/><fmt:message key='common.colon'/></label><br/>
							  <select id="attributeSet" name="attributeSet" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${attributeSetMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.attribute_set}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
													    
						    <p>
							  <label><fmt:message key='product.orlPrice'/><fmt:message key='common.colon'/>&nbsp;&nbsp;( ¥ )</label><br/>
							  <input type="text" id="orlPrice" class="" value="${product.orl_price}" name="orlPrice"/>
							  &nbsp;&nbsp;	
							  <input type="button" class="btn" value="<fmt:message key='button.generatePrice'/>" onclick="generatePrice()"/>  
							</p>
							
							
							<p>
							  <label class="required" for="price"><fmt:message key='product.price'/><fmt:message key='common.colon'/>&nbsp;&nbsp;( $ )</label><br/>
							  <input type="text" id="price" class="" value="${product.price}" name="price"/>
							</p>
							
							<p>
							  <label class="required"><fmt:message key='product.specialPrice'/><fmt:message key='common.colon'/>&nbsp;&nbsp;( $ )</label><br/>
							  <input type="text" id="specialPrice" class="" value="${product.special_price}" name="specialPrice"/>
							</p>
							
							
							<p>
							  <label><fmt:message key='product.stockurl'/><fmt:message key='common.colon'/></label><br/>
							  <textArea id="stockurl" name="stockurl" rows="6" cols="80" class="half" style="overflow: auto">${product.sb_stock_url}</textArea>
							</p>
							
							
							<p>
							  <label class="required" for="description"><fmt:message key='product.description'/><fmt:message key='common.colon'/></label><br/>
							  <textArea id="description" name="description" rows="12" cols="80" class="half" style="overflow: auto">${product.description}</textArea>
							</p>
							
							<p>
							<label><fmt:message key='product.sizeDescription'/><fmt:message key='common.colon'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<fmt:message key='product.sizeDemo'/>&nbsp;&nbsp;(<a href="#" onclick="sizeDemo()"><fmt:message key='product.commonSize'/></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="freeSizeDemo()"><fmt:message key='product.freeSize'/></a>)
							</label><br/>
							<table >
							<tr>
							<td>							  
							<textarea name="sizeDescription" style="width:800px;height:300px;visibility:hidden;">${product.sizeDescription}</textarea> 
							<script type="text/javascript">
								var editor;  
					            KindEditor.ready(function(K) {  
					                editor = K.create('textarea[name="sizeDescription"]', {  
					                    allowFileManager : true  
					                });  
					            }); 
							</script>
							</td>
							<td align="left">
							 <c:choose>
				               <c:when test="${product.sizeStatus==1}">
				                  <input type="checkbox" name="sizeStatus" value ="1" checked="checked">
				               </c:when>	
				               <c:otherwise>
				                  <input type="checkbox" name="sizeStatus" value ="1">
				               </c:otherwise>
				             </c:choose>
				             <fmt:message key='common.message.size_check'/>
				             <br>
				             <br>
				             &nbsp;&nbsp;Size(cm)
				             <br>
				             <br>
							 &nbsp;&nbsp;<fmt:message key='common.length'/> &nbsp;&nbsp;Length
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.bust'/>  &nbsp;&nbsp;Bust
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.shoulder'/> &nbsp;&nbsp;Shoulder
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.sleeveLength'/> &nbsp;&nbsp;Sleeve Length
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.waist'/>  &nbsp;&nbsp;Waist
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.hips'/>  &nbsp;&nbsp;Hips
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.thighWidth'/>   &nbsp;&nbsp;Thigh Width
							 <br>
							 &nbsp;&nbsp;<fmt:message key='common.sweepWidth'/>  &nbsp;&nbsp;Sweep Width
							 <br>
				             </td>
				             </tr>
				             <tr>
				             <td align="left">				            							 
							 <fmt:message key='common.sizeTable'/><fmt:message key='common.colon'/>&nbsp;&nbsp;<xmp><table border="1" style="border-collapse:collapse;"  height="200" width="600"></xmp> 
				             </td>
				             </tr>
				             </table>
							</p>
							
							<br></br>
							<br></br>
							
							<c:choose>
				               <c:when test="${product.shopSku=='1001'}">
					                 <p>
									  <label><fmt:message key='product.color'/><fmt:message key='common.colon'/></label><br/>								   
									   <select id="color" name="color" class="edit_input" >
						            	  <option value=""><fmt:message key="common.select"/></option>
							              <c:forEach items="${colorMap}" var="entity">  
							              	   <c:choose>
									               <c:when test="${entity.key==product.sb_color}">
									                 <option value="${entity.key}" selected="selected">${entity.value}</option>
									               </c:when>	
									               <c:otherwise>
									                  <option value="${entity.key}">${entity.value}</option>
									               </c:otherwise>
									            </c:choose>    
											
										  </c:forEach>			             
						             </select>
									</p>
				               </c:when>	
				               <c:otherwise>
				                  <p>
									  <label><fmt:message key='product.color'/><fmt:message key='common.colon'/></label><br/>
									  <c:forEach items="${colorSet}" var="color">
									  	${color.custom_option_row_sort}
									  	<select id="color" name="color" class="edit_input" >
							            	  <option value=""><fmt:message key="common.select"/></option>
								              <c:forEach items="${colorMap}" var="entity">  
								              	   <c:choose>
										               <c:when test="${entity.key==color.custom_option_row_title}">
										                 <option value="${entity.key}" selected="selected">${entity.value}</option>
										               </c:when>	
										               <c:otherwise>
										                  <option value="${entity.key}">${entity.value}</option>
										               </c:otherwise>
										            </c:choose>    
												
											  </c:forEach>			             
							             </select>						  
									  
									  </c:forEach>  
									</p>
				               </c:otherwise>
				            </c:choose>
							
							<p>
							  <label><fmt:message key='product.material'/><fmt:message key='common.colon'/></label><br/>
							  <select id="material" name="material" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${materialMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_material}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
							<p>
							  <label><fmt:message key='product.componentContent'/><fmt:message key='common.colon'/></label><br/>
							  <select id="componentContent" name="componentContent" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${componentContentMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_componentContent}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
							<p>
							  <label><fmt:message key='product.style'/><fmt:message key='common.colon'/></label><br/>
							  <select id="style" name="style" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${styleMap}" var="style">  
					              	   <c:choose>
							               <c:when test="${style.key==product.sb_style}">
							                 <option value="${style.key}" selected="selected">${style.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${style.key}">${style.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>	
							</p>
							
							
							<c:if test="${fn:startsWith(product.category, 'Tops')||fn:startsWith(product.category, 'Dresses')}">
							<p>
							  <label><fmt:message key='product.sleeveLength'/><fmt:message key='common.colon'/></label><br/>
							  <select id="sleeveLength" name="sleeveLength" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${sleeveLengthMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_sleeve_length}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
							
							
							<p>
							  <label><fmt:message key='product.sleeveType'/><fmt:message key='common.colon'/></label><br/>
							  <select id="sleeveType" name="sleeveType" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${sleeveTypeMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_sleeveType}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
							<p>
							  <label><fmt:message key='product.collar'/><fmt:message key='common.colon'/></label><br/>
							  <select id="collar" name="collar" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${collarMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_collar}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							</c:if>
							
							
							<c:if test="${fn:startsWith(product.category, 'Tops')}">
							<p>
							  <label><fmt:message key='product.closureType'/><fmt:message key='common.colon'/></label><br/>
							  <select id="closureType" name="closureType" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${closureTypeMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_closure_type}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							</c:if>
							
							<p>
							  <label><fmt:message key='product.patternType'/><fmt:message key='common.colon'/></label><br/>
							  <select id="patternType" name="patternType" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${patternMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_pattern_type}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							
														
							<p>
							  <label><fmt:message key='product.gender'/><fmt:message key='common.colon'/></label><br/>							  
			                   		<select id="gender" name="gender" class="edit_input" >
					            	  <option value=""><fmt:message key="common.select"/></option>
						              <c:forEach items="${genderMap}" var="entity">  
						              	   <c:choose>
								               <c:when test="${entity.key=='Women'}">
								                 <option value="${entity.key}" selected="selected">${entity.value}</option>
								               </c:when>	
								               <c:otherwise>
								                  <option value="${entity.key}">${entity.value}</option>
								               </c:otherwise>
								            </c:choose>    
										
									  </c:forEach>			             
					             	</select>						 
							</p>
																																										
														
							<p>
							  <label><fmt:message key='product.season'/><fmt:message key='common.colon'/></label><br/>
							  <select id="season" name="season" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${seasonMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_season}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>												
							
							<p>
							  <label><fmt:message key='product.decoration'/><fmt:message key='common.colon'/></label><br/>
							  <select id="decoration" name="decoration" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${decorationMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_decoration}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>	
							
							
							<c:if test="${fn:startsWith(product.category, 'Dresses')||fn:startsWith(product.category, 'Bottoms/Skirts')}">
							<p>
							  <label><fmt:message key='product.dressesLength'/><fmt:message key='common.colon'/></label><br/>
							  <select id="dressesLength" name="dressesLength" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${dressesLengthMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_dresses_length}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>	
							</c:if>							
							
							
							<c:if test="${fn:startsWith(product.category, 'Bottoms')}">
							<p>
							  <label><fmt:message key='product.waistType'/><fmt:message key='common.colon'/></label><br/>
							  <select id="waistType" name="waistType" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${waistTypeMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==product.sb_waist_type}">
							                 <option value="${entity.key}" selected="selected">${entity.value}</option>
							               </c:when>	
							               <c:otherwise>
							                  <option value="${entity.key}">${entity.value}</option>
							               </c:otherwise>
							            </c:choose>    
									
								  </c:forEach>			             
				             </select>
							</p>
							</c:if>
							
							
							
							
							
							
							
							<c:choose>
				               <c:when test="${fn:startsWith(product.category, 'Tops')}">
				                 <p>
								  <label><fmt:message key='product.types'/><fmt:message key='common.colon'/></label><br/>
								  <select id="types" name="types" class="edit_input" >
					            	  <option value=""><fmt:message key="common.select"/></option>
						              <c:forEach items="${topsTypesMap}" var="entity">  
						              	   <c:choose>
								               <c:when test="${entity.key==product.sb_types}">
								                 <option value="${entity.key}" selected="selected">${entity.value}</option>
								               </c:when>	
								               <c:otherwise>
								                  <option value="${entity.key}">${entity.value}</option>
								               </c:otherwise>
								            </c:choose>    
										
									  </c:forEach>			             
					             </select>
								</p>			                 
				               </c:when>
				               <c:when test="${fn:startsWith(product.category, 'Bottoms/Pants')}">
				                 <p>
								  <label><fmt:message key='product.types'/><fmt:message key='common.colon'/></label><br/>
								  <select id="types" name="types" class="edit_input" >
					            	  <option value=""><fmt:message key="common.select"/></option>
						              <c:forEach items="${bottomsTypesMap}" var="entity">  
						              	   <c:choose>
								               <c:when test="${entity.key==product.sb_types}">
								                 <option value="${entity.key}" selected="selected">${entity.value}</option>
								               </c:when>	
								               <c:otherwise>
								                  <option value="${entity.key}">${entity.value}</option>
								               </c:otherwise>
								            </c:choose>    
										
									  </c:forEach>			             
					             </select>
								</p>			                 
				               </c:when>	
				               <c:otherwise>				                 				                  
				               </c:otherwise>
				            </c:choose>
							
																					
							
							<br></br><br></br>
							<p>
							<fmt:message key='common.message.product_image'/>
							</p>
							<p>
							  <label>Image<fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="image" class="half" value="${product.image}" name="image"/>
							</p>
							
							<p>
							  <label>Small Image<fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="smallImage" class="half" value="${product.small_image}" name="smallImage"/>
							</p>
							
							<p>
							  <label>Thumbnail<fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="thumbnail" class="half" value="${product.thumbnail}" name="thumbnail"/>
							</p>											
						    <p class="box">
						    <input type="submit" class="btn btn-green big" value="<fmt:message key='button.submit'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;						    
						    <input type="button" class="btn btn-green big" onclick="searchProduct()" value="<fmt:message key='button.back'/>"/>
						    </p>
							
					</form>
											
					
				</section>
				<!-- End of Left column/section -->
				
				<!-- Right column/section -->
		  
				<!-- End of Right column/section -->
				
		</div>
		<!-- End of Wrapper -->
	</div>
	<!-- End of Page content -->
	
	<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper">
			
			<p>* This system is based on HTML5. Firefox/Chrome supports better.</p>
			<p>Copyright &copy; 2012 Pierson Capital Technology LLC,</p>
		</div>
	</footer>
	<!-- End of Page footer -->
	
	<!-- Animated footer -->

	<!-- End of Animated footer -->
	
	<!-- Scroll to top link -->
	<a href="#" id="totop">^ scroll to top</a>

<!-- Admin template javascript load -->
<script type="text/javascript" SRC="<c:url value='/js/administry.js'/>"></script>
</body>
</html>
