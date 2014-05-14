
	<%@ include file="/WEB-INF/jsp/common/common.jsp"%>
	<%@ page import="com.shebuyit.controller.MainController" %>
    <!DOCTYPE html> 
	<html lang="en">
    <head>
<title>Shebuyit System</title>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<script type="text/javascript" src="<c:url value='/common/dojo-1.4.3/dojo/dojo.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/js/deployJava.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ckeditor/ckeditor.js'/>" ></script>
<script type="text/javascript">
<%@ include file="/common/js/common.js" %>
dojo.require("dojo.data.ItemFileWriteStore");
dojo.require("dojo.parser");
dojo.require("dijit.form.ComboBox");
dojo.require("dojo.io.iframe");
	        $(function() {
	    		$( "#datepicker" ).datepicker();
	    		$( "#datepicker1" ).datepicker();
	    		$( "#datepicker2" ).datepicker();
	    		$( "#datepicker3" ).datepicker();
	    	});
	        
	        $(document).ready(function(){
	        	
	        	/* setup navigation, content boxes, etc... */
	        	Administry.setup();
	        	
	        	/* progress bar animations - setting initial values */
	        	Administry.progress("#progress1", 1, 5);
	        	Administry.progress("#progress2", 2, 5);
	        	Administry.progress("#progress3", 2, 5);

	        	/* flot graphs */
	        	var sales = [{
	        		label: 'Total authentications',
	        		data: [[1,2221],[2,0],[3,0],[4,0],[5,0],[6,0],[7,900],[8,0],[9,0],[10,0],[11,0],[12,0]]
	        	},{
	        		label: 'Total failed',
	        		data: [[1, 111],[2,0],[3,0],[4,0],[5,0],[6,0],[7,222],[8,0],[9,0],[10,0],[11,0],[12,0]]
	        	}
	        	];

	        	var plot = $.plot($("#placeholder"), sales, {
	        		bars: { show: true, lineWidth: 1 },
	        		legend: { position: "nw" },
	        		xaxis: { ticks: [[1, "Jan"], [2, "Feb"], [3, "Mar"], [4, "Apr"], [5, "May"], [6, "Jun"], [7, "Jul"], [8, "Aug"], [9, "Sep"], [10, "Oct"], [11, "Nov"], [12, "Dec"]] },
	        		yaxis: { min: 0, max: 3000 },
	        		grid: { color: "#666" },
	        		colors: ["#0a0", "#f00"]			
	            });


	        });
	        
	        function runStockCrawler(shopId,rowIndex) {
				var img = document.getElementById("stockCrawler");
				var shopSku = document.getElementById("shopSku").value;
				var category = document.getElementById("category").value;
				var status;		
				if(img.className == "btn btn-green"){
					status = "enable";
				}if(img.className == "btn btn-red"){
					status = "disable";
				}
				if(status == "enable"){
					dojo.xhrGet({   
			       		 url: "<c:url value='/main/runStockCrawler.do?shopSku="+shopSku+"&category="+category+"'/>",
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
					<li class="current"><a HREF="<c:url value='/main/main.do'/>"><fmt:message key='navigation.home'/></a></li>
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
			<h1>Home</h1>
			<!-- Quick search box -->
			<form action="" method="get"><input class="" type="text" id="q" name="q" /></form>
		</div>
	</div>
	<!-- End of Page title -->
	
	<!-- Page content -->
	<div id="page">
		<!-- Wrapper -->
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
				
					<div class="colgroup leading">
						<div class="column width3 first">
							<h3>Welcome back, <mark>${sessionScope.user.userName}</mark></h3>
							<p>
								You are the <b>${sessionScope.user.type} Group</b>.
							</p>
						</div>
						<div class="column width3">
							<h4>Registration Date</h4>
							<p>
								Your account registered at <b>${sessionScope.user.created_time}</b>
							</p>
						</div>
					</div>
					<c:if test="${sessionScope.user.type=='admin'}">
					<div class="colgroup leading">
						<div class="column width3 first">
							<h4>Shop</h4>
							<hr/>
							<table class="no-style full">
								<tbody>
									<tr>
										<td colspan="2">Period: 
										  <input type="text" id="datepicker" size="6">
										  -
								      <input type="text" id="datepicker1" size="6"> <a href="#" class="btn btn-green">Check</a></td>
									</tr>
									<tr>
										<td width="59%">Total accounts</td>
										<td width="41%" class="ta-right"><span class="code">10</span></td>
									</tr>
									<tr>
									  <td>TaoBao</td>
									  <td class="ta-right"><a href="adminlist.html">4</a></td>
								  </tr>
									<tr>
									  <td>Other</td>
									  <td class="ta-right"><a href="useraccounts.html">6</a></td>
								  </tr>
								</tbody>
								
							</table>
						</div>
						<div class="column width3">
							<h4>Product<a href="#"></a></h4>
							<hr/>
							<table class="no-style full">
								<tbody>
									<tr>
										<td colspan="2">
								      <select id="shopSku" name="shopSku" style="font:12px;width:120px">	
										<option lang="" value=""><fmt:message key="product.shopSku"/></option>  	
										 <c:if test="${not empty shopSkuMap}"> 
									     	<c:forEach var="shopSku" items="${shopSkuMap}"> 
									     	<option lang="" maxLength="15" value="${shopSku.key}">${shopSku.key}  ${shopSku.value}</option>  
									     	</c:forEach> 
										</c:if>      						         			
							   		</select>
							   		&nbsp;&nbsp;	 	
							        <select id="category" name="<%=MainController.SEARCH_PARAM_CATEGORY %>" style="font:12px;width:150px" > 
						           	  <option lang="" value=""><fmt:message key="product.category"/></option> 
						              <c:forEach items="${categoryMap}" var="entity">  
								           <option  value="${entity.key}">${entity.value}</option> 				
									  </c:forEach>			             
						            </select>
								      <a href="#" id="stockCrawler" class="btn btn-green" onclick="runStockCrawler()"><fmt:message key='shop.run' /></a>
								      </td>
								      
									</tr>
									<tr>
										<td width="59%">Total Products</td>
										<td width="41%" class="ta-right"><span class="code">${inStockProduct+outOfStockProduct}</span></td>
									</tr>
									<tr>
										<td>In Stock</td>
										<td class="ta-right"><a href="monitorlogs.html">${inStockProduct}</a></td>
									</tr>
									<tr>
									  <td>Out of Stock</td>
									  <td class="ta-right"><a href="adminlogs.html">${outOfStockProduct}</a></td>
								  </tr>
								</tbody>
							</table>
						</div>
					</div>
				
					<div class="colgroup leading">
						<div class="column width3 first">
							<h4>Status</h4>
							<hr/>
							<table class="no-style full">
								<tbody>
									<tr>
									  <td colspan="3">Total issued: <a href="devicelist.html">272</a></td>
								  </tr>
									<tr>
										<td>Enabled</td>
										<td class="ta-right">50</td>
										<td><div id="progress1" class="progress full progress-green"><span><b></b></span></div></td>
									</tr>
									<tr>
										<td>Disabled</td>
										<td class="ta-right">111</td>
										<td><div id="progress2" class="progress full progress-blue"><span><b></b></span></div></td>
									</tr>
									<tr>
										<td>Others</td>
										<td class="ta-right">111</td>
										<td><div id="progress3" class="progress full progress-red"><span><b></b></span></div></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="column width3">
							<h4>Reports</h4>
							<hr/>
							<p><b>Sales this year</b></p>
							<div id="placeholder" style="height:100px"></div>
						</div>
					</div>
					<div class="clear">&nbsp;</div>
				</c:if>	
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
		<ul>
			<li>
                <a HREF="unblock.html">Unblock</a>
            </li>
            <li>
                <a HREF="sycron.html">Syncronization</a>
            </li>
            <li>
                <a HREF="testauth.html">Test Auth</a>
            </li>
            <li>
                <a HREF="demo.html">Demostration</a>
            </li>
            <li>
                <a HREF="api.html">API Documentation</a>
            </li>

		</ul>
	</footer>
	<!-- End of Animated footer -->
	
	<!-- Scroll to top link -->
	<a href="#" id="totop">^ scroll to top</a>

<!-- Admin template javascript load -->
<script type="text/javascript" SRC="<c:url value='/js/administry.js'/>"></script>
</body>
</html>