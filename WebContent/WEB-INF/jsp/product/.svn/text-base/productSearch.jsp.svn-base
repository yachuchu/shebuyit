<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<%@ page import="com.shebuyit.controller.ProductController" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/common/dojo-1.4.3/dojox/grid/resources/tundraGrid.css'/>" />  		
<link rel="stylesheet" type="text/css" href="<c:url value='/common/dojo-1.4.3/dijit/themes/tundra/tundra.css'/>" />		
<link rel=stylesheet href="<c:url value='/common/wdsoppmstyle/scatax_theme.css'/>">
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"
	djConfig="parseOnLoad: true, usePlainJson:true, extraLocale: ['en-us']">
</script>		
<script type="text/javascript">
		<%@ include file="/common/js/common.js" %>
		<%@ include file="/common/js/pageInfo.js" %>
		$(document).ready(function(){
        	
        	/* setup navigation, content boxes, etc... */
        	Administry.setup();

        });
		
		$(function() {
			$( "#datepicker" ).datepicker({dateFormat : 'yy-mm-dd '});
			$( "#datepicker1" ).datepicker({dateFormat : 'yy-mm-dd '});
		});

		dojo.require("dojo.data.ItemFileWriteStore");
	    dojo.require("dojox.grid.DataGrid");
	    dojo.require("dojo.parser");
	    dojo.require("dijit.form.ComboBox");
	    dojo.require("dojo.io.iframe");
	    dojo.addOnLoad( function() {					
	    	setDefSort("productGrid", "id", "DESC", 9, 1, '<fmt:message key="common.id" />',false);
	    	
		});

	    var gridList = { identifier:'',  items:${dojoGridVO.jsonData} };
		var initStore=new dojo.data.ItemFileWriteStore({data:gridList});
		var layout = [
			{name:'<input type="checkbox" name="all" id="all" onclick="selectAll(this)">', width:2, get:getSelect},
			{id:'id', name:'<fmt:message key="common.id" />', width:5, field:"id"},
			{id:'sku', name:'<fmt:message key="product.productSku" />',width:10, get:getSku},
			{id:'sb_shop',name:'<fmt:message key="product.productUrl" />', width:5, get:getShop},
			{id:'shopSku', name:'<fmt:message key="product.shopSku" />',width:6, field:"shopSku"},
			{id:'category',name:'<fmt:message key="product.category" />', width:14, field:"category"},		
			{id:'englishName',name:'<fmt:message key="product.englishName" />', width:'auto', field:"englishName"},				
			{id:'created_time',name:'<fmt:message key="product.createdTime" />', width:11, field:"created_time"},
			{id:'saleCount',name:'<fmt:message key="product.saleCount" />', width:5, field:"saleCount"}
		];	
		
		function getSelect(inRowIndex){
			var grid = dijit.byId("productGrid");
			if (grid.getItem(inRowIndex)){
				return '<input type="checkbox" value="'+grid.getItem(inRowIndex).id+'" name="productId" />';
			}
		}
		
		
		function getSku(inRowIndex) {
			var grid = dijit.byId("productGrid");
			var model = grid.getItem(inRowIndex);
			var sku = model.sku;
			var editStatus = model.editStatus;
			var productId = model.id;		
			if(model){
				if(editStatus!=null&&editStatus==1){
					return "<a href='<c:url value='/product/update.do?productId="+productId+"'/>' target='_blank' ><span style='color:red'>"+sku+"</span></a>";
				}else{
					return "<a href='<c:url value='/product/update.do?productId="+productId+"'/>' target='_blank' >"+sku+"</a>";
				}
			}			
			
		}
		
		function getShop(inRowIndex) {
			var grid = dijit.byId("productGrid");
			var model = grid.getItem(inRowIndex);
			var shopStr = model.sb_shop;
			var sizeStatus = model.sizeStatus;
			if(model){	
				if(sizeStatus!=null&&sizeStatus==1){
					return "<a href='"+shopStr+"' target='_blank'><span style='color:red'>HTTP</span></a>";				
				}else{
					return "<a href='"+shopStr+"' target='_blank'>HTTP</a>";
				}
				
			}
			
		}		

		function updateProduct(productId) {
			window.location.href= "<c:url value='/product/update.do?productId="+productId+"'/>";						
		}

		function addProduct() {
			window.location.href= "<c:url value='/product/add.do'/>";				
		}

		function loadGrid(formId, gridId) {
			cleanGridSort("id", "DESC", 8, 1, '<fmt:message key="common.id" />',false);		
			var form = document.getElementById(formId);
			var url = "<c:url value='/product/search.do'/>";	
			url = url + "?method=search";
			loadGridDataAJAX(formId, gridId, url, 8, 1, '<fmt:message key="common.id" />',false);
		} 
		
		function selectAll(obj){
			var ids = document.getElementsByName("productId");
			dojo.forEach(ids, function(item){
				item.checked = obj.checked;
			});
		}	
		
		function deleteProduct(){
			showProgressBar();
			var productIds="";
			var _checks=checkMultiValue("productGrid","productId");
			if(_checks.length<=0){
				alert("<fmt:message key='product.selectProductTip'/>");
				hideProgressBar();
				return;
			}
			productIds=dojo.toJson(_checks);						
	 		if(confirm("<fmt:message key='common.message.enable'/>")){				     			    
		 		dojo.xhrGet({   
		       		 url: "<c:url value='/product/delete.do?productIds="+productIds+"'/>",  
		       		 handleAs: "json",
		       		 preventCache: true, 
		       		 load: function(response){ 
		       			if(response.returnCode == "000" || response.returnCode==000){	
			       			//document.getElementById("successBox").style.display = "block";
			       			window.location.href= "<c:url value='/product/search.do'/>";
		       			}else{
		       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
		       				document.getElementById("errorBox").style.display = "block";
		       				hideProgressBar();
		       			} 
		       			
		       		 } 		   
		   		 });
			}																 
		}
		
		function exportSelectProduct(){
			//showProgressBar();
			var productIds="";
			var _checks=checkMultiValue("productGrid","productId");
			if(_checks.length<=0){
				alert("<fmt:message key='product.selectProductTip'/>");
				hideProgressBar();
				return;
			}
			productIds=dojo.toJson(_checks);		
			
			document.searchProduct.action="<c:url value='/product/exportSelectProduct.do?productIds="+productIds+"'/>";
			document.searchProduct.submit(); 
																	 
		}
		
		function exportProduct(){
			document.searchProduct.action="<c:url value='/product/exportProduct.do'/>";
			document.searchProduct.submit(); 
		}
		
		function updateProducts(){
			showProgressBar();
			dojo.io.iframe.send({
        		url: "<c:url value='/product/updateProducts.do'/>",  
	       		form: dojo.byId("searchProduct"), 
	       		handleAs: "json",
	       		timeout:"15000",
	       		handle: function(response, ioArgs){
	       		if(response.returnCode == "000" || response.returnCode==000){	       					
	       			document.getElementById("successBox").style.display = "block";
       			}else{
       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
       				document.getElementById("errorBox").style.display = "block";
       			}    
	       		hideProgressBar();
	   		},
	   		method: 'POST'
	   		});																			 
		}
		
		function deleteSearch(){
			showProgressBar();
			dojo.io.iframe.send({
        		url: "<c:url value='/product/deleteSearch.do'/>",  
	       		form: dojo.byId("searchProduct"), 
	       		handleAs: "json",
	       		timeout:"15000",
	       		handle: function(response, ioArgs){
	       		if(response.returnCode == "000" || response.returnCode==000){	       					
	       			document.getElementById("successBox").style.display = "block";
       			}else{
       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
       				document.getElementById("errorBox").style.display = "block";
       			}    
	       		hideProgressBar();
	   		},
	   		method: 'POST'
	   		});																			 
		}
		
		
		
		
			
	
	</script>
	</head>
	
	<body>
	<!-- Header -->
	<header id="top">
		<div class="wrapper">
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
	   <div class="wrapper">
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
			
			<form id="searchProduct" name="searchProduct" method="post" action="<c:url value='/product/search.do'/>">	
			<input type="hidden" name="doSearch" value="true">
			<select id="product_search_param" name="<%=ProductController.SEARCH_PARAM %>"  style="font:12px">	
				<option lang="" value=""><fmt:message key="common.searcyby"/></option>  	       						      
	   			<option lang="" maxLength="15" value="<%=ProductController.SEARCH_PARAM_SKU %>"><fmt:message key="product.productSku"/></option>  
	   			<option lang="" maxLength="15" value="<%=ProductController.SEARCH_PARAM_ORLSKU %>"><fmt:message key="product.productOrlSku"/></option>
	   			<option lang="" maxLength="15" value="<%=ProductController.SEARCH_PARAM_NAME %>"><fmt:message key="product.englishName"/></option>   
	   		</select>:
	        <input type="text" id="" name="<%=ProductController.SEARCH_PARAM_VALUE %>" size="15"/> &nbsp;&nbsp;	
	        
	        <select id="shopSku_search_param" name="<%=ProductController.SEARCH_PARAM_SHOPSKU %>" style="font:12px;width:120px">	
				<option lang="" value=""><fmt:message key="product.shopSku"/></option>  	
				 <c:if test="${not empty shopSkuMap}"> 
			     	<c:forEach var="shopSku" items="${shopSkuMap}"> 
			     	<option lang="" maxLength="15" value="${shopSku.key}">${shopSku.key}  ${shopSku.value}</option>  
			     	</c:forEach> 
				</c:if>      						         			
	   		</select>
	   		&nbsp;&nbsp;	 	
	        <select id="category" name="<%=ProductController.SEARCH_PARAM_CATEGORY %>" style="font:12px;width:150px" >
           	  <option lang="" value=""><fmt:message key="product.category"/></option> 
              <c:forEach items="${categoryMap}" var="entity">  
		           <option  value="${entity.key}">${entity.value}</option> 				
			  </c:forEach>			             
            </select>
            &nbsp;&nbsp;	 	
	        <select id="category" name="<%=ProductController.SEARCH_PARAM_FREESIZE %>" style="font:12px" >
           	  <option lang="" value=""><fmt:message key="product.freeSize"/></option> 
           	  <option lang="" value="1"><fmt:message key="common.message.yes"/></option> 
           	  <option lang="" value="0"><fmt:message key="common.message.no"/></option> 		             
            </select>
            &nbsp;&nbsp;	 	
	        <select id="category" name="<%=ProductController.SEARCH_PARAM_ISEXPORT %>" style="font:12px" >
           	  <option lang="" value=""><fmt:message key="common.isExport"/></option> 
           	  <option lang="" value="1"><fmt:message key="common.message.yes"/></option> 
           	  <option lang="" value="0"><fmt:message key="common.message.no"/></option> 		             
            </select>

	       <fmt:message key='common.period'/><fmt:message key='common.colon'/>	           		       						
			<input type="text" id="datepicker" name="<%=ProductController.SEARCH_PARAM_START %>" size="18"> 
			-
			<input type="text" id="datepicker1" name="<%=ProductController.SEARCH_PARAM_END %>" size="18">
			<br></br>
			<div style="text-align:right;">
	        <input type="button" class="btn" value="<fmt:message key='button.search'/>" onclick="loadGrid('searchProduct','productGrid');"/>           							    
		    <input type="button" class="btn" value="<fmt:message key='button.reset'/>" onclick="this.form.reset();resetQuery();"/>   
		    <c:if test="${sessionScope.user.type=='admin'}">
		    <input type="button" class="btn" value="<fmt:message key='button.export'/>" onclick="exportProduct()"/>    
		    <input type="button" class="btn" value="<fmt:message key="button.update" />" onclick="updateProducts()"/>
		    <input type="button" class="btn" value="<fmt:message key="button.delete" />" onclick="deleteSearch()"/>		    
		    </c:if>          							
			</div>
			<br></br>
			<div style="text-align:right;">
			<input type="button" class="btn btn-green big" onclick="addProduct()" value="<fmt:message key="button.add" />" />
			<c:if test="${sessionScope.user.type=='admin'}">
			<input type="button" class="btn btn-green big" onclick="deleteProduct()" value="<fmt:message key="button.delete" />" />
			<input type="button" class="btn btn-green big" onclick="exportSelectProduct()" value="<fmt:message key="button.export" />" />
			 </c:if> 
			</div>
			<div class="tundra" id="productGrid" dojoType="dojox.grid.DataGrid" selectable="true" style="height: 290px;border:0px solid #777777"
			 store="initStore"  onHeaderCellClick="myGridSort" structure="layout" escapeHTMLInData="false"   
			 noDataMessage='<fmt:message key="grid.noData" />' autoHeight="true">
			</div>
			</form> 

			<jsp:include page="/WEB-INF/jsp/common/includePageInfo.jsp">
				<jsp:param name="gridId" value="productGrid" />
				<jsp:param name="url" value="/product/search.do" />
				<jsp:param name="formId" value="searchProduct" />
			</jsp:include>			
			 <div class="clear">&nbsp;</div>
			</section>
			<!-- End of Left column/section -->
		</div>
		<!-- End of Wrapper -->
	 </div>
	<!-- End of Page content -->
	
	<!-- Page footer -->
	<footer id="bottom">
		<div class="wrapper">
			<p>* This system is based on HTML5. Firefox/Chrome supports better. We don't recommend IE.</p>
			<p>Copyright &copy; 2012 Pierson Capital Technology LLC,</p>
		</div>
	</footer>
	<!-- End of Page footer -->
	
	<!-- Animated footer -->
	<footer id="animated">

	</footer>
	<!-- End of Animated footer -->
	
	<!-- Scroll to top link -->
	<a href="#" id="totop">^ scroll to top</a>

<!-- Admin template javascript load -->
<script type="text/javascript" SRC="<c:url value='/js/administry.js'/>"></script>
</body>
</html>

