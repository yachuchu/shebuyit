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
	var validator = $("#parserProduct").validate({
		rules: {
			shopSku: {
				required: true
			},
			category: {
				required: true
			},
			shopCanal: {
				required: true
			},
			productUrl: {
				required: true
			},
						

		},
		messages: {
			shopSku: {
				required: "<fmt:message key='required.shopSku' />"
			},
			category: {
				required: "<fmt:message key='required.category' />"
			},
			shopCanal: {
				required: "<fmt:message key='required.shopCanal' />"
			},
			productUrl: {
				required: "<fmt:message key='required.shopUrl' />"
			},
			
		},
		// the errorPlacement has to take the layout into account
		errorPlacement: function(error, element) {
			error.insertAfter(element.parent().find('label:first'));
		},
		// specifying a submitHandler prevents the default submit, good for the demo
		submitHandler: function() {
			dojo.io.iframe.send({
				url: "<c:url value='/product/parserProduct.do'/>",  
		   		form: dojo.byId("parserProduct"), 
		   		handleAs: "json",
		   		timeout:"15000",
		   		handle: function(response, ioArgs){
		   		if(response.returnCode == "000" || response.returnCode==000){	       					
		   			document.getElementById("successBox").style.display = "block";
		   			window.location.href= "<c:url value='/product/update.do?productId="+response.productId+"'/>";	
					}else{
						document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
						document.getElementById("errorBox").style.display = "block";
					}    
		   		hideProgressBar();
				},
				method: 'POST'
				});	
		}

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
					
					<form id="parserProduct" method="post" action="<c:url value='/product/parserProduct.do'/>">		
						<p>
				        <label class="required" for="shopSku"><fmt:message key='product.shopSku'/><fmt:message key='common.colon'/></label><br/>
					    <select id="shopSku" name="shopSku" style="font:12px">	
							<option lang="" value=""><fmt:message key="common.select"/></option>  	
							 <c:if test="${not empty shopSkuMap}"> 
						     	<c:forEach var="entity" items="${shopSkuMap}"> 
						     		<c:choose>
						               <c:when test="${entity.key=='1000'}"> 
						                 <option value="${entity.key}" selected="selected">${entity.key}  ${entity.value}</option>
						               </c:when>	
						               <c:otherwise>
						                  <option value="${entity.key}">${entity.key}  ${entity.value}</option>
						               </c:otherwise>
						            </c:choose> 
						     	</c:forEach> 
							</c:if>      						         			
				   		</select>						    
					    </p>
								        
				        
				   		<p>
				        <label class="required" for="category"><fmt:message key='product.category'/><fmt:message key='common.colon'/></label><br/>
					    <select id="category" name="category" style="font:12px" >
			           	  <option lang="" value=""><fmt:message key="common.select"/></option> 
			              <c:forEach items="${categoryMap}" var="entity">  
					           <option value="${entity.key}">${entity.value}</option> 				
						  </c:forEach>			             
			            </select>						    
					    </p>	 	
				         
			            <p>
				        <label class="required" for="shopCanal"><fmt:message key='shop.shopCanal'/><fmt:message key='common.colon'/></label><br/>
					    <select id="shopCanal" name="shopCanal" style="font:12px" >
			           	  <option lang="" value=""><fmt:message key="common.select"/></option> 
			              <c:forEach items="${shopCanalMap}" var="entity">  
					           <option value="${entity.key}">${entity.value}</option> 				
						  </c:forEach>			             
			            </select>						    
					    </p>
					    	 	
						
						<p>
						  <label class="required" for="productUrl"><fmt:message key='product.productUrl'/><fmt:message key='common.colon'/></label><br/>
						  <input type="text" id="productUrl" class="half"  name="productUrl"/>
						</p>	           		       						


						<p class="box">
			   				<input type="submit" class="btn btn-green big" value="<fmt:message key='shop.run'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;
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
