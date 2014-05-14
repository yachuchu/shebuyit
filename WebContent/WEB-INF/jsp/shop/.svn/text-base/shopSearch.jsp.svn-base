<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<%@ page import="com.shebuyit.controller.ShopController"%>
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
			$( "#datepicker" ).datepicker();
			$( "#datepicker1" ).datepicker();
		});

		dojo.require("dojo.data.ItemFileWriteStore");
	    dojo.require("dojox.grid.DataGrid");
	    dojo.require("dojo.parser");
	    dojo.require("dijit.form.ComboBox");
	    dojo.require("dojo.io.iframe");
	    dojo.addOnLoad( function() {					
	    	setDefSort("shopGrid", "id", "DESC", 8, 1, '<fmt:message key="common.id" />',false);
		});

	    var gridList = { identifier:'',  items:${dojoGridVO.jsonData}};
		var initStore=new dojo.data.ItemFileWriteStore({data:gridList});
		var layout = [
			{name:'<input type="checkbox" name="all" id="all" onclick="selectAll(this)">', width:2, get:getSelect},
			{id:'id', name:'<fmt:message key="common.id" />', width:3, field:"id"},
			{id:'description',name:'<fmt:message key="product.description" />', width:'auto', get:getDescription},
			{id:'shopSku', name:'<fmt:message key="shop.shopSku" />',width:8, field:"shopSku"},
			{id:'shopBrand',name:'<fmt:message key="shop.shopBrand" />', width:8, field:"shopBrand"},  
			{id:'category',name:'<fmt:message key="shop.category" />', width:14, field:"category"},
			{id:'shopUrl',name:'<fmt:message key="shop.shopUrl" />', width:'auto', get:getShop}, 										
			{id:'shop_ID',name:'<fmt:message key="shop.crawler" />', width:6, get:getCrawler}
			
		];	
		
		function getSelect(inRowIndex){
			var grid = dijit.byId("shopGrid");
			if (grid.getItem(inRowIndex)){
				return '<input type="checkbox" value="'+grid.getItem(inRowIndex).id+'" name="shopId" />';
			}
		}
		
		function getSku(inRowIndex) {
		 
			var grid = dijit.byId("shopGrid");
			var model = grid.getItem(inRowIndex);
			var shopSku = model.shopSku;
			var shopId = model.id;		
			if(model){					
				return "<a href='#' onclick='updateShop(\""+shopId+"\")'>"+shopSku+"</a>";
			}
		}
		
		function getDescription(inRowIndex) {
			 
			var grid = dijit.byId("shopGrid");
			var model = grid.getItem(inRowIndex);
			var description = model.description;
			var shopId = model.id;		
			if(model){					
				return "<a href='#' onclick='updateShop(\""+shopId+"\")'>"+description+"</a>";
			}
		}
		
		function getShop(inRowIndex) {
			var grid = dijit.byId("shopGrid");
			var model = grid.getItem(inRowIndex);
			var shopUrl = model.shopUrl;
			if(model){					
				return "<a href='"+shopUrl+"' >"+shopUrl+"</a>";
			}
			
		}		
		
		function getCrawler(inRowIndex) {
			var grid = dijit.byId("shopGrid");
			var model = grid.getItem(inRowIndex);			
			var shopId = model.id;	
			return  "<a href='#' id='row"+inRowIndex+"' class='btn btn-green' onclick='runCrawler(\""+shopId+"\",\""+inRowIndex+"\")'><fmt:message key='shop.run' /></a>";

		}
		
		function runCrawler(shopId,rowIndex) {
			var img = document.getElementById("row"+rowIndex);
			var status;		
			if(img.className == "btn btn-green"){
				status = "enable";
			}if(img.className == "btn btn-red"){
				status = "disable";
			}
			if(status == "enable"){
				dojo.xhrGet({   
		       		 url: "<c:url value='/shop/runCrawler.do?shopId="+shopId+"'/>",
		       		 handleAs: "text",
		       		 preventCache: true, 
		       		 load: function(resp){  
		       			document.getElementById("successBox").style.display = "none";
		       			document.getElementById("errorBox").style.display = "none";
		       			if(resp == "000" || resp==000){
		       					if(status == "disable"){
		       							img.className = "btn btn-green";
		       							//img.innerHTML = "<fmt:message key='card.enabled' />";
		       					}else if(status == "enable"){
		       							img.className = "btn btn-red";
	      								//img.innerHTML = "<fmt:message key='card.disabled' />";
		       					}
		       					document.getElementById("successBoxMesg").innerHTML="Start running";
		       					document.getElementById("successBox").style.display = "block";
		       			}else{
		       				document.getElementById("errorBoxMesg").innerHTML=resp;
		       				document.getElementById("errorBox").style.display = "block";
		       			}		            
		       		 } 		   
		   		});	
			}
							
		}

		function updateShop(shopId) {
			window.location.href= "<c:url value='/shop/update.do?shopId="+shopId+"'/>";						
		}

		function addShop() {
			window.location.href= "<c:url value='/shop/add.do'/>";				
		}

		function loadGrid(formId, gridId) {
			cleanGridSort("id", "DESC", 8, 1, '<fmt:message key="common.id" />',false);				
			var form = document.getElementById(formId);
			var url = form.action;				
			url = url + "?method=search";
			loadGridDataAJAX(formId, gridId, url, 8, 1, '<fmt:message key="common.id" />',false);
		} 
		
		function selectAll(obj){
			var ids = document.getElementsByName("shopId");
			dojo.forEach(ids, function(item){
				item.checked = obj.checked;
			});
		}	
		
		function deleteShop(){
			var shopIds="";
			var _checks=checkMultiValue("shopGrid","shopId");
			if(_checks.length<=0){
				alert("<fmt:message key='shop.selectShopTip'/>");
				hideProgressBar();
				return;
			}
			shopIds=dojo.toJson(_checks);					
			if(confirm("<fmt:message key='common.message.enable'/>")){				     			    
		 		dojo.xhrGet({   
		       		 url: "<c:url value='/shop/delete.do?shopIds="+shopIds+"'/>",  
		       		 handleAs: "json",
		       		 preventCache: true, 
		       		 load: function(response){ 
		       			if(response.returnCode == "000" || response.returnCode==000){	
		       				window.location.href= "<c:url value='/shop/search.do'/>";
		       			}else{
		       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
		       				document.getElementById("errorBox").style.display = "block";
		       			} 
		       			hideProgressBar();
		       		 } 		   
		   		 });
			}																 
		}
		
		function runSelectShop(){
			showProgressBar();
			var shopIds="";
			var _checks=checkMultiValue("shopGrid","shopId");
			if(_checks.length<=0){
				alert("<fmt:message key='shop.selectShopTip'/>");
				hideProgressBar();
				return;
			}
			shopIds=dojo.toJson(_checks);								     			    
		 		dojo.xhrGet({   
		       		 url: "<c:url value='/shop/runSelectCrawler.do?shopIds="+shopIds+"'/>",  
		       		 handleAs: "text",
		       		 preventCache: true, 
		       		 load: function(response){ 
		       			if(response == "000" || response==000){	
			       			document.getElementById("successBox").style.display = "block";
		       			}else{
		       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
		       				document.getElementById("errorBox").style.display = "block";
		       			} 
		       			hideProgressBar();
		       		 } 		   
		   		 });																 
		}
		
		function stockCrawler(){
			showProgressBar();
			dojo.io.iframe.send({
        		url: "<c:url value='/shop/runStockCrawler.do'/>",  
	       		form: dojo.byId("searchShop"), 
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
	   <div class="wrapper">
	      <section class="column width6 first">
	   	
			<div class="clear">&nbsp;</div>				
					<div id="successBox" class="box box-success"  style="display:none;">
						<table  cellspacing="0" cellpadding="0" width="100%" border="0">
							<tr>
							<td width="90%" align="left"><span id="successBoxMesg"><fmt:message key='common.success'/></span></td>
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
			
			<form id="searchShop" method="post" action="<c:url value='/shop/search.do'/>">	
			<input type="hidden" name="doSearch" value="true">
			<fmt:message key="product.shopSku" /><fmt:message key='common.colon'/> 
			<select id="shopSku_search_param" name="<%=ShopController.SEARCH_PARAM_SHOPSKU %>" style="font:12px">	
				<option lang="" value=""><fmt:message key="common.select"/></option>  	
				 <c:if test="${not empty shopSkuMap}"> 
			     	<c:forEach var="shopSku" items="${shopSkuMap}"> 
			     	<option lang="" maxLength="20" value="${shopSku.key}">${shopSku.key}  ${shopSku.value}</option>  
			     	</c:forEach> 
				</c:if>      						         			
	   		</select>
	   		&nbsp;&nbsp;
	        &nbsp;&nbsp;	 	
	        <fmt:message key="shop.category" /><fmt:message key='common.colon'/> 
	        <select id="category" name="<%=ShopController.SEARCH_PARAM_CATEGORY %>" style="font:12px" >
           	  <option lang="" value=""><fmt:message key="common.select"/></option> 
              <c:forEach items="${categoryMap}" var="entity">  
		           <option value="${entity.key}">${entity.value}</option> 				
			  </c:forEach>			             
            </select>&nbsp;&nbsp;	 	           		       						
			
	        <input type="button" class="btn" value="<fmt:message key='button.search'/>" onclick="loadGrid('searchShop','shopGrid');"/>           					
		    <input type="button" class="btn" value="<fmt:message key='button.reset'/>" onclick="this.form.reset();resetQuery();"/>    
		     <input type="button" class="btn" value="StockCrawler" onclick="stockCrawler()"/>        							
			
			<br></br>
			<div style="text-align:right;">
			<input type="button" class="btn btn-green big" onclick="addShop()" value="<fmt:message key="button.add" />" />
			<input type="button" class="btn btn-green big" onclick="deleteShop()" value="<fmt:message key="button.delete" />" />
			<input type="button" class="btn btn-green big" onclick="runSelectShop()" value="<fmt:message key="shop.run" />" />
			</div>
			<div class="tundra" id="shopGrid" dojoType="dojox.grid.DataGrid" selectable="true" style="height: 290px;border:0px solid #777777"
			 store="initStore"  onHeaderCellClick="myGridSort" structure="layout" escapeHTMLInData="false"   
			 noDataMessage='<fmt:message key="grid.noData" />' autoHeight="true">
			</div>
			</form> 

			<jsp:include page="/WEB-INF/jsp/common/includePageInfo.jsp">
				<jsp:param name="gridId" value="shopGrid" />
				<jsp:param name="url" value="/shop/search.do" />
				<jsp:param name="formId" value="searchShop" />
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

