<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/deployJava.js'/>"></script>
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
	var validator = $("#shopform").validate({
		rules: {
			shopSku: {
				required: true
			},
			description: {
				required: true
			},
			shopUrl: {
				required: true
			},
			shopBrand: {
				required: true
			},
			shopName: {
				required: true
			},
			category: {
				required: true
			},
			attributeSet: {
				required: true
			},			

		},
		messages: {
			shopSku: {
				required: "<fmt:message key='required.shopSku' />"
			},
			description: {
				required: "<fmt:message key='required.description' />"
			},
			shopUrl: {
				required: "<fmt:message key='required.shopUrl' />"
			},
			shopBrand: {
				required: "<fmt:message key='required.shopBrand' />"
			},
			shopName: {
				required: "<fmt:message key='required.shopName' />"
			},
			category: {
				required: "<fmt:message key='required.category' />"
			},
			attributeSet: {
				required: "<fmt:message key='required.attributeSet' />"
			},
			
		},
		// the errorPlacement has to take the layout into account
		errorPlacement: function(error, element) {
			error.insertAfter(element.parent().find('label:first'));
		},
		// specifying a submitHandler prevents the default submit, good for the demo
		submitHandler: function() {
						dojo.io.iframe.send({
			        		url: "<c:url value='/shop/save.do'/>",  
				       		form: dojo.byId("shopform"), 
				       		handleAs: "json",
				       		timeout:"15000",
				       		handle: function(response, ioArgs){
				       		if(response.returnCode == "000" || response.returnCode==000){	       					
				       			document.getElementById("successBox").style.display = "block";
				       			window.location.href= "<c:url value='/shop/search.do'/>";		
			       			}else{
			       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
			       				document.getElementById("errorBox").style.display = "block";
			       			}         
				   		},
				   		method: 'POST'
				   		});
		}

	});
	

});

function searchShop(){
	window.location.href="<c:url value='/shop/search.do'/>";
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
					<li   class="current"><a HREF="<c:url value='/shop/search.do'/>"><fmt:message key='navigation.shopManagement'/></a>
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
			<h1><fmt:message key="navigation.shopManagement" /> &rarr; <span><fmt:message key="navigation.shopManagement" /></span></h1>
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
					<div class="clear">&nbsp;</div>

					
					<form id="shopform" method="post" action="<c:url value='/shop/save.do'/>">	
					        <p>
					        <label><fmt:message key='shop.shopId'/><fmt:message key='common.colon'/></label>
						    <input type="hidden" id="shopId" class="" value="${shop.id}" name="shopId"/>
						    ${shop.id}						    
						    </p>
						    
							<p>
							  <label class="required" for="shopSku"><fmt:message key='shop.shopSku'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shopSku" class="" value="${shop.shopSku}" name="shopSku"/>
							</p>	
							
							<p>
							  <label class="required" for="description"><fmt:message key='product.description'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="description" class="half" value="${shop.description}" name="description"/>
							</p>
												
							<p>
							  <label class="required" for="shopUrl"><fmt:message key='shop.shopUrl'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shopUrl" class="half" value="${shop.shopUrl}" name="shopUrl"/>
							</p>
							
							<p>
							  <label class="required" for="shopBrand"><fmt:message key='shop.shopBrand'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shopBrand" class="" value="${shop.shopBrand}" name="shopBrand"/>
							</p>

							<p>
							  <label class="required" for="shopName"><fmt:message key='shop.shopName'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shopName" class="" value="${shop.shopName}" name="shopName"/>
							</p>
							
							<p>
							  <label class="required" for="category"><fmt:message key='product.category'/><fmt:message key='common.colon'/></label><br/>
							  <select id="category" name="category" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${categoryMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==shop.category}">
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
							               <c:when test="${entity.key==shop.attribute_set}">
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
							  <label><fmt:message key='shop.shopCanal'/><fmt:message key='common.colon'/></label><br/>
							  <select id="shopCanal" name="shopCanal" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${shopCanalMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==shop.shopCanal}">
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
							  <label><fmt:message key='product.material'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="material" class="" value="${shop.sb_material}" name="material"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.style'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="style" class="" value="${shop.sb_style}" name="style"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.types'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="types" class="" value="${shop.sb_types}" name="types"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.item'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="item" class="" value="${shop.sb_item}" name="item"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.sleeveLength'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="sleeveLength" class="" value="${shop.sb_sleeve_length}" name="sleeveLength"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.decoration'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="decoration" class="" value="${shop.sb_decoration}" name="decoration"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.patternType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="patternType" class="" value="${shop.sb_pattern_type}" name="patternType"/>
							</p>				
							
							<p>
							  <label><fmt:message key='product.length'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="length" class="" value="${shop.sb_length}" name="length"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.collar'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="collar" class="" value="${shop.sb_collar}" name="collar"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.season'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="season" class="" value="${shop.sb_season}" name="season"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.neckline'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="neckline" class="" value="${shop.sb_neckline}" name="neckline"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.placket'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="placket" class="" value="${shop.sb_placket}" name="placket"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.fabric'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="fabric" class="" value="${shop.sb_fabric}" name="fabric"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.silhouette'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="silhouette" class="" value="${shop.sb_silhouette}" name="silhouette"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.pantLength'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="pantLength" class="" value="${shop.sb_pant_length}" name="pantLength"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.dressesLength'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="dressesLength" class="" value="${shop.sb_dresses_length}" name="dressesLength"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.fitType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="fitType" class="" value="${shop.sb_fit_type}" name="fitType"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.waistType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="waistType" class="" value="${shop.sb_waist_type}" name="waistType"/>
							</p>
							
							<p>
							  <label><fmt:message key='product.closureType'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="closureType" class="" value="${shop.sb_closure_type}" name="closureType"/>
							</p>												
							
																										
							
						    <p class="box">
						    <input type="submit" class="btn btn-green big" value="<fmt:message key='button.submit'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;						    
						    <input type="button" class="btn btn-green big" onclick="searchShop()" value="<fmt:message key='button.back'/>"/>
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
