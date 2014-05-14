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
			productSku: {
				required: true
			},
			stock: {
				required: true
			},
			shopSku: {
				required: true
			},
			productUrl: {
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
			description: {
				required: true
			}
			

		},
		messages: {
			productSku: {
				required: "<fmt:message key='equired.productSku'/>"
			},
			stock: {
				required: "<fmt:message key='required.stock'/>"
			},
			shopSku: {
				required: "<fmt:message key='required.shopSku' />"
			},
			productUrl: {
				required: "<fmt:message key='required.productUrl' />"
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
					<li><a HREF="<c:url value='/product/search.do'/>"><fmt:message key='navigation.productManagement'/></a>
						<ul>
							<li>
								<a HREF="<c:url value='/product/search.do'/>"><fmt:message key='navigation.products'/></a>
							</li>
							<li>
								<a HREF="<c:url value='/storage/search.do'/>"><fmt:message key='navigation.outofstockProducts'/></a>
							</li>	
							<li  class="current">
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
					        <p>
					        <label><fmt:message key='product.productId'/><fmt:message key='common.colon'/></label>
						    <input type="hidden" id="productId" class="" value="${product.id}" name="productId"/>
						    ${product.id}						    
						    </p>
						    
							<p>
							  <label class="required" for="productSku"><fmt:message key='product.productSku'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="productSku" class="" value="${product.sku}" name="productSku"/>
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
							  <label class="required" for="productUrl"><fmt:message key='product.productUrl'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="productUrl" class="half" value="${product.sb_shop}" name="productUrl"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.chineseName'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="chineseName" class="half" value="${product.chineseName}" name="chineseName"/>
							</p>

							<p>
							  <label class="required" for="englishName"><fmt:message key='product.englishName'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="englishName" class="half" value="${product.englishName}" name="englishName"/>
							</p>
							
							<p>
							  <label class="required" for="category"><fmt:message key='product.category'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="category" class="" value="${product.category}" name="category"/>
							</p>
							
							<p>
							  <label class="required" for="attributeSet"><fmt:message key='product.attributeSet'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="attributeSet" class="" value="${product.attribute_set}" name="attributeSet"/>
							</p>
							
							<p>
							  <label class="required" for="price"><fmt:message key='product.price'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="price" class="" value="${product.price}" name="price"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.specialPrice'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="specialPrice" class="" value="${product.special_price}" name="specialPrice"/>
							</p>

							
							<p>
							  <label class="required" for="description"><fmt:message key='product.description'/><fmt:message key='common.colon'/></label><br/>
							  <textArea id="description" name="description" rows="12" cols="80" class="half" style="overflow: auto">${product.description}</textArea>
							</p>
							
							<p>
							<label><fmt:message key='product.sizeDescription'/><fmt:message key='common.colon'/></label><br/>							  
							<textarea name="sizeDescription" style="width:800px;height:300px;visibility:hidden;">${product.sizeDescription}</textarea> 
							<script type="text/javascript">
								var editor;  
					            KindEditor.ready(function(K) {  
					                editor = K.create('textarea[name="sizeDescription"]', {  
					                    allowFileManager : true  
					                });  
					            }); 
							</script>
							</p>
							
							<p>
							  <label><fmt:message key='product.brand'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="brand" class="" value="${product.sb_brand}" name="brand"/>
							</p>
														
							<p>
							  <label><fmt:message key='product.color'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="color" class="" value="${product.sb_color}" name="color"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.material'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="material" class="" value="${product.sb_material}" name="material"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.style'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="style" class="" value="${product.sb_style}" name="style"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.types'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="types" class="" value="${product.sb_types}" name="types"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.item'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="item" class="" value="${product.sb_item}" name="item"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.sleeveLength'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="sleeveLength" class="" value="${product.sb_sleeve_length}" name="sleeveLength"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.decoration'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="decoration" class="" value="${product.sb_decoration}" name="decoration"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.patternType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="patternType" class="" value="${product.sb_pattern_type}" name="patternType"/>
							</p>				
							
							<p>
							  <label><fmt:message key='product.length'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="length" class="" value="${product.sb_length}" name="length"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.collar'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="collar" class="" value="${product.sb_collar}" name="collar"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.season'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="season" class="" value="${product.sb_season}" name="season"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.neckline'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="neckline" class="" value="${product.sb_neckline}" name="neckline"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.placket'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="placket" class="" value="${product.sb_placket}" name="placket"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.fabric'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="fabric" class="" value="${product.sb_fabric}" name="fabric"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.silhouette'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="silhouette" class="" value="${product.sb_silhouette}" name="silhouette"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.pantLength'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="pantLength" class="" value="${product.sb_pant_length}" name="pantLength"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.dressesLength'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="dressesLength" class="" value="${product.sb_dresses_length}" name="dressesLength"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.fitType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="fitType" class="" value="${product.sb_fit_type}" name="fitType"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.waistType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="waistType" class="" value="${product.sb_waist_type}" name="waistType"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.closureType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="closureType" class="" value="${product.sb_closure_type}" name="closureType"/>
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
