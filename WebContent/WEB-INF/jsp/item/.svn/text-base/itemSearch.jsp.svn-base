<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<%@ page import="com.shebuyit.controller.ItemController" %>
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
	    	setDefSort("itemGrid", "id", "DESC", 9, 1, '<fmt:message key="common.id" />',false);
	    	
		});

	    var gridList = { identifier:'',  items:${dojoGridVO.jsonData} };
		var initStore=new dojo.data.ItemFileWriteStore({data:gridList});
		var layout = [
			{name:'<input type="checkbox" name="all" id="all" onclick="selectAll(this)">', width:2, get:getSelect},
			{id:'id', name:'<fmt:message key="common.id" />', width:5, field:"id"},
			{id:'sku', name:'<fmt:message key="orderItem.sku" />',width:10, get:getSku},
			{id:'orderNumber', name:'<fmt:message key="order.orderNumber" />',width:10, field:"orderNumber"},
			{id:'created_time',name:'<fmt:message key="orderItem.createdTime" />', width:11, field:"created_time"},		
			{id:'price',name:'<fmt:message key="orderItem.price" />', width:6, field:"price"},
			{id:'quantity', name:'<fmt:message key="orderItem.quantity" />',width:6, field:"quantity"},
			{id:'taobao_price',name:'<fmt:message key="orderItem.taobaoPrice" />', width:6, field:"taobao_price"},	
			{id:'profit',name:'<fmt:message key="orderItem.profit" />', width:6, field:"profit"},
			{id:'profitRate',name:'<fmt:message key="orderItem.profitRate" />', width:6, field:"profitRate"},
			{id:'taobao_order_number',name:'<fmt:message key="orderItem.taobaoOrderNumber" />', width:'auto', field:"taobao_order_number"}	
			            
		];	
		
		function getSelect(inRowIndex){
			var grid = dijit.byId("itemGrid");
			if (grid.getItem(inRowIndex)){
				return '<input type="checkbox" value="'+grid.getItem(inRowIndex).id+'" name="orderItemId" />';
			}
		}
		
		
		function getSku(inRowIndex) {
			var grid = dijit.byId("itemGrid");
			var model = grid.getItem(inRowIndex);
			var sku = model.sku;
			var orderItemId = model.id;		
			if(model){
				return "<a href='<c:url value='/item/update.do?orderItemId="+orderItemId+"'/>'>"+sku+"</a>";

			}			
			
		}	
		
		function getOrderId(inRowIndex) {
			var grid = dijit.byId("itemGrid");
			var model = grid.getItem(inRowIndex);
			var orders = model.orders;
			var orderId = orders[0].id;		
			return orderId;
					
		}

		function updateOrderItem(orderItemId) {
			window.location.href= "<c:url value='/item/update.do?orderItemId="+orderItemId+"'/>";						
		}

		function addOrderItem() {
			window.location.href= "<c:url value='/item/add.do'/>";				
		}

		function loadGrid(formId, gridId) {
			cleanGridSort("id", "DESC", 8, 1, '<fmt:message key="common.id" />',false);		
			var form = document.getElementById(formId);
			var url = form.action;				
			url = url + "?method=search";
			loadGridDataAJAX(formId, gridId, url, 10, 1, '<fmt:message key="common.id" />',false);
		} 
		
		function selectAll(obj){
			var ids = document.getElementsByName("orderItemId");
			dojo.forEach(ids, function(item){
				item.checked = obj.checked;
			});
		}	
		
		function deleteOrderItem(){
			showProgressBar();
			var orderItemIds="";
			var _checks=checkMultiValue("itemGrid","orderItemId");
			if(_checks.length<=0){
				alert("<fmt:message key='orderItem.selectOrderTip'/>");
				hideProgressBar();
				return;
			}
			orderItemIds=dojo.toJson(_checks);						
	 		if(confirm("<fmt:message key='common.message.enable'/>")){				     			    
		 		dojo.xhrGet({   
		       		 url: "<c:url value='/item/delete.do?orderItemIds="+orderItemIds+"'/>",  
		       		 handleAs: "json",
		       		 preventCache: true, 
		       		 load: function(response){ 
		       			if(response.returnCode == "000" || response.returnCode==000){	
			       			//document.getElementById("successBox").style.display = "block";
			       			window.location.href= "<c:url value='/item/search.do'/>";
		       			}else{
		       				document.getElementById("errorBoxMesg").innerHTML=response.returnCode;	
		       				document.getElementById("errorBox").style.display = "block";
		       				hideProgressBar();
		       			} 
		       			
		       		 } 		   
		   		 });
			}																 
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
                    <li class="current"><a HREF="<c:url value='/order/search.do'/>"><fmt:message key='navigation.orderManagement'/></a>
						<ul>
							<li>
								<a HREF="<c:url value='/order/search.do'/>"><fmt:message key='navigation.orders'/></a>
							</li>	
							<li class="current">
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
			<h1><fmt:message key="navigation.orderManagement" /> &rarr; <span><fmt:message key="navigation.orderItem" /></span></h1>
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
			
			<form id="searchOrderItem" method="post" action="<c:url value='/item/search.do'/>">	
			<input type="hidden" name="doSearch" value="true">
			<select id="order_search_param" name="<%=ItemController.SEARCH_PARAM %>"  style="font:12px">	 
				<option lang="" value=""><fmt:message key="common.searcyby"/></option>  	       						      
	   			<option lang="" maxLength="15" value="<%=ItemController.SEARCH_PARAM_SKU %>"><fmt:message key="orderItem.sku"/></option>
	   			<option lang="" maxLength="15" value="<%=ItemController.SEARCH_PARAM_ORDERNUMBER %>"><fmt:message key="order.orderNumber"/></option>     
	   			<option lang="" maxLength="15" value="<%=ItemController.SEARCH_PARAM_TAOBAOORDERNUMBER %>"><fmt:message key="orderItem.taobaoOrderNumber"/></option>  
	   		</select>:
	        <input type="text" id="" name="<%=ItemController.SEARCH_PARAM_VALUE %>" size="20"/> &nbsp;&nbsp;
	        	       

	       <fmt:message key='common.period'/><fmt:message key='common.colon'/>	           		       						
			<input type="text" id="datepicker" name="<%=ItemController.SEARCH_PARAM_START %>" size="18"> 
			-
			<input type="text" id="datepicker1" name="<%=ItemController.SEARCH_PARAM_END %>" size="18">
			<br></br>
			<div style="text-align:right;">
	        <input type="button" class="btn" value="<fmt:message key='button.search'/>" onclick="loadGrid('searchOrderItem','itemGrid');"/>           							    
		    <input type="button" class="btn" value="<fmt:message key='button.reset'/>" onclick="this.form.reset();resetQuery();"/>           							
			</div>
			<br></br>
			<div style="text-align:right;">
			<input type="button" class="btn btn-green big" onclick="deleteOrderItem()" value="<fmt:message key="button.delete" />" />
			</div>
			<div class="tundra" id="itemGrid" dojoType="dojox.grid.DataGrid" selectable="true" style="height: 290px;border:0px solid #777777"
			 store="initStore"  onHeaderCellClick="myGridSort" structure="layout" escapeHTMLInData="false"   
			 noDataMessage='<fmt:message key="grid.noData" />' autoHeight="true">
			</div>
			</form> 

			<jsp:include page="/WEB-INF/jsp/common/includePageInfo.jsp">
				<jsp:param name="gridId" value="itemGrid" />
				<jsp:param name="url" value="/item/search.do" />
				<jsp:param name="formId" value="searchOrderItem" />
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

