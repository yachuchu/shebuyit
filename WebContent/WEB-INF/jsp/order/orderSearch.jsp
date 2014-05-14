<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
<%@ page import="com.shebuyit.controller.OrderController" %>
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
	    	setDefSort("orderGrid", "id", "DESC", 12, 1, '<fmt:message key="common.id" />',false);
	    	
		});

	    var gridList = { identifier:'',  items:${dojoGridVO.jsonData} }; 
		var initStore=new dojo.data.ItemFileWriteStore({data:gridList});
		var layout = [
			{name:'<input type="checkbox" name="all" id="all" onclick="selectAll(this)">', width:2, get:getSelect},
			{id:'id', name:'<fmt:message key="common.id" />', width:4, field:"id"},
			{id:'orderNumber', name:'<fmt:message key="order.orderNumber" />',width:'auto', get:getOrderNumber},
			{id:'orderNumber4px', name:'<fmt:message key="order.orderNumber4px" />',width:'auto', field:"orderNumber4px"},
			{id:'site', name:'<fmt:message key="order.site" />',width:5, field:"site"},
			{id:'created_time',name:'<fmt:message key="order.createdTime" />', width:11, field:"created_time"},		
			{id:'totalDue',name:'<fmt:message key="order.totalDue" />', width:4, get:getTotalDue},
			{id:'shipChannel', name:'<fmt:message key="order.shipChannel" />',width:8, field:"shipChannel"},
			{id:'destination',name:'<fmt:message key="order.destination" />', width:8, field:"destination"},	
			{id:'shipWeight',name:'<fmt:message key="order.shipWeight" />', width:3, field:"shipWeight"},
			{id:'shipPrice',name:'<fmt:message key="order.shipPrice" />', width:4, field:"shipPrice"},	
			{id:'shipTime_start',name:'<fmt:message key="order.shipTime_start" />', width:6, field:"shipTime_start"},	
			{id:'shipTime_end',name:'<fmt:message key="order.shipTime_end" />', width:6, field:"shipTime_end"}
				            
		];	
		
		function getSelect(inRowIndex){
			var grid = dijit.byId("orderGrid");
			if (grid.getItem(inRowIndex)){
				return '<input type="checkbox" value="'+grid.getItem(inRowIndex).id+'" name="orderId" />';
			}
		}
		
		
		function getOrderNumber(inRowIndex) {
			var grid = dijit.byId("orderGrid");
			var model = grid.getItem(inRowIndex);
			var orderNumber = model.orderNumber;
			var shipStatus = model.shipStatus;
			var orderId = model.id;		
			if(model){
				if(shipStatus!=null&&shipStatus==1){
					return "<a href='<c:url value='/order/update.do?orderId="+orderId+"'/>'><span style='color:red'>"+orderNumber+"</span></a>";
				}else{
					return "<a href='<c:url value='/order/update.do?orderId="+orderId+"'/>'>"+orderNumber+"</a>";
				}
			}			
			
		}	
		
		function getTotalDue(inRowIndex) {
			var grid = dijit.byId("orderGrid");
			var model = grid.getItem(inRowIndex);
			var totalDue = model.totalDue;
			var dollarRate = model.dollarRate;	
			return accMul(totalDue,dollarRate);
		}
		 
		function getShipPrice(inRowIndex) {
			var grid = dijit.byId("orderGrid");
			var model = grid.getItem(inRowIndex);
			var shipPrice = model.shipPrice;
			var dollarRate = model.dollarRate;				
			return accMul(shipPrice,dollarRate);
		}
		
		function accMul(arg1,arg2) 
		{ 
		var m=0,s1=arg1.toString(),s2=arg2.toString(); 
		try{m+=s1.split(".")[1].length}catch(e){} 
		try{m+=s2.split(".")[1].length}catch(e){} 
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) 
		}  

		function updateOrder(orderId) {
			window.location.href= "<c:url value='/order/update.do?orderId="+orderId+"'/>";						
		}

		function addOrder() {
			window.location.href= "<c:url value='/order/add.do'/>";				
		}

		function loadGrid(formId, gridId) {
			cleanGridSort("id", "DESC", 8, 1, '<fmt:message key="common.id" />',false);		
			var form = document.getElementById(formId);
			var url = form.action;				
			url = url + "?method=search";
			loadGridDataAJAX(formId, gridId, url, 10, 1, '<fmt:message key="common.id" />',false);
		} 
		
		function selectAll(obj){
			var ids = document.getElementsByName("orderId");
			dojo.forEach(ids, function(item){
				item.checked = obj.checked;
			});
		}	
		
		function deleteOrder(){
			showProgressBar();
			var orderIds="";
			var _checks=checkMultiValue("orderGrid","orderId");
			if(_checks.length<=0){
				alert("<fmt:message key='order.selectOrderTip'/>");
				hideProgressBar();
				return;
			}
			orderIds=dojo.toJson(_checks);						
	 		if(confirm("<fmt:message key='common.message.enable'/>")){				     			    
		 		dojo.xhrGet({   
		       		 url: "<c:url value='/order/delete.do?orderIds="+orderIds+"'/>",  
		       		 handleAs: "json",
		       		 preventCache: true, 
		       		 load: function(response){ 
		       			if(response.returnCode == "000" || response.returnCode==000){	
			       			//document.getElementById("successBox").style.display = "block";
			       			window.location.href= "<c:url value='/order/search.do'/>";
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
							<li class="current">
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
			<h1><fmt:message key="navigation.orderManagement" /> &rarr; <span><fmt:message key="navigation.orders" /></span></h1>
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
			
			<form id="searchOrder" method="post" action="<c:url value='/order/search.do'/>">	
			<input type="hidden" name="doSearch" value="true">
	        <select id="order_search_param" name="<%=OrderController.SEARCH_PARAM %>"  style="font:12px">	 
				<option lang="" value=""><fmt:message key="common.searcyby"/></option>  	       						      
	   			<option lang="" maxLength="15" value="<%=OrderController.SEARCH_PARAM_ORDERNUMBER %>"><fmt:message key="order.orderNumber"/></option> 
	   			<option lang="" maxLength="15" value="<%=OrderController.SEARCH_PARAM_ORDERNUMBER4PX %>"><fmt:message key="order.orderNumber4px"/></option> 
	   		</select>: 
	        <input type="text" id="" name="<%=OrderController.SEARCH_PARAM_VALUE %>" size="18"/> &nbsp;&nbsp;
	        	 	
	        <select id="shipChannel" name="<%=OrderController.SEARCH_PARAM_SHIPCHANNEL %>" style="font:12px;width:150px" >
           	  <option lang="" value=""><fmt:message key="order.shipChannel"/></option> 
              <c:forEach items="${shipChannelMap}" var="entity">  
		           <option  value="${entity.key}">${entity.value}</option> 				
			  </c:forEach>			             
            </select>
            &nbsp;&nbsp;	
            
            <select id="shipChannel" name="<%=OrderController.SEARCH_PARAM_DESTINATION %>" style="font:12px;width:150px" >
           	  <option lang="" value=""><fmt:message key="order.destination"/></option> 
              <c:forEach items="${destinationMap}" var="entity">  
		           <option  value="${entity.key}">${entity.value}</option> 				
			  </c:forEach>			             
            </select>
            &nbsp;&nbsp;	
            
            <select id="site" name="<%=OrderController.SEARCH_PARAM_SITE %>" style="font:12px;width:100px" >
           	  <option lang="" value=""><fmt:message key="order.site"/></option> 
              <c:forEach items="${siteMap}" var="entity">  
		           <option  value="${entity.key}">${entity.value}</option> 				
			  </c:forEach>			             
            </select>
            &nbsp;&nbsp;       

	       <fmt:message key='common.period'/><fmt:message key='common.colon'/>	           		       						
			<input type="text" id="datepicker" name="<%=OrderController.SEARCH_PARAM_START %>" size="18"> 
			-
			<input type="text" id="datepicker1" name="<%=OrderController.SEARCH_PARAM_END %>" size="18">
			<br></br>
			<div style="text-align:right;">
	        <input type="button" class="btn" value="<fmt:message key='button.search'/>" onclick="loadGrid('searchOrder','orderGrid');"/>           							    
		    <input type="button" class="btn" value="<fmt:message key='button.reset'/>" onclick="this.form.reset();resetQuery();"/>           							
			</div>
			<br></br>
			<div style="text-align:right;">
			<input type="button" class="btn btn-green big" onclick="addOrder()" value="<fmt:message key="button.add" />" />
			<c:if test="${sessionScope.user.type=='admin'}">
			<input type="button" class="btn btn-green big" onclick="deleteOrder()" value="<fmt:message key="button.delete" />" />
			 </c:if> 
			</div>
			<div class="tundra" id="orderGrid" dojoType="dojox.grid.DataGrid" selectable="true" style="height: 290px;border:0px solid #777777"
			 store="initStore"  onHeaderCellClick="myGridSort" structure="layout" escapeHTMLInData="false"   
			 noDataMessage='<fmt:message key="grid.noData" />' autoHeight="true">
			</div>
			</form> 

			<jsp:include page="/WEB-INF/jsp/common/includePageInfo.jsp">
				<jsp:param name="gridId" value="orderGrid" />
				<jsp:param name="url" value="/order/search.do" />
				<jsp:param name="formId" value="searchOrder" />
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

