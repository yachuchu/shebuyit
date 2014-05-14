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
	var validator = $("#orderform").validate({
		rules: {
			site: {
				required: true
			},
			orderNumber: {
				required: true
			},
			shipChannel: {
				required: true
			},
			destination: {
				required: true
			}
			
		},
		messages: {
			site: {
				required: "<fmt:message key='required.site'/>"
			},
			orderNumber: {
				required: "<fmt:message key='required.orderNumber'/>"
			},
			shipChannel: {
				required: "<fmt:message key='required.shipChannel' />"
			},
			destination: {
				required: "<fmt:message key='required.destination' />"
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

$(function() {
	$( "#datepicker" ).datepicker({dateFormat : 'yy-mm-dd '});
	$( "#datepicker1" ).datepicker({dateFormat : 'yy-mm-dd '});
});

function searchOrder(){
	window.location.href= "<c:url value='/order/search.do'/>";		
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


function addOrderItem(orderId) {
	var returnV = window.showModalDialog("<c:url value='/order/addItem.do?orderId="+orderId+"'/>",window,"dialogWidth:500px;dialogHeight:450px;center:yes;help:no;resizable:no;status:no"); 
	if(returnV != null) 
	{	
		window.location.reload();

	}
}
		
function updateOrderItem(orderItemId) {
	var returnVa = window.showModalDialog("<c:url value='/order/updateItem.do?orderItemId="+orderItemId+"'/>",window,"dialogWidth:500px;dialogHeight:450px;center:yes;help:no;resizable:no;status:no"); 
	if(returnVa != null) 
	{				
		window.location.reload();
	}
}

function deleteOrderItem(orderItemId,orderId){
	    showProgressBar();					
		if(confirm("<fmt:message key='common.message.enable'/>")){				     			    
 		dojo.xhrGet({   
       		 url: "<c:url value='/order/deleteItem.do?orderItemId="+orderItemId+"'/>",  
       		 handleAs: "json",
       		 preventCache: true, 
       		 load: function(response){ 
       			if(response.returnCode == "000" || response.returnCode==000){	
	       			//document.getElementById("successBox").style.display = "block";
	       			window.location.href= "<c:url value='/order/update.do?orderId="+orderId+"'/>";
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

					
					<form id="orderform" method="post" action="<c:url value='/order/save.do'/>">	
							<c:if test="${order!=null}">
					        <p>
					        <label><fmt:message key='order.orderId'/><fmt:message key='common.colon'/></label>
						    <input type="hidden" id="orderId" class="" value="${order.id}" name="orderId"/>
						    ${order.id}						    
						    </p>
						    </c:if>
						    
						    <p>
							  <label  class="required" for="site"><fmt:message key='order.site'/><fmt:message key='common.colon'/></label><br/>
							  <select id="site" name="site" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${siteMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==order.site}">
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
							  <label class="required" for="orderNumber"><fmt:message key='order.orderNumber'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="orderNumber" class="" value="${order.orderNumber}" name="orderNumber"/>
							</p>
							
							<p>
							  <label><fmt:message key='order.orderNumber4px'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="orderNumber4px" class="" value="${order.orderNumber4px}" name="orderNumber4px"/>
							</p>
							
							<p>
							  <label><fmt:message key='order.subtotal'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="subtotal" class="" value="${order.subtotal}" name="subtotal"/>($)
							</p>
							
							<p>
							  <label><fmt:message key='order.discount'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="discount" class="" value="${order.discount}" name="discount"/>($)
							</p>
							
							<p>
							  <label><fmt:message key='order.shipping'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shipping" class="" value="${order.shipping}" name="shipping"/>($)
							</p>
											
							<p>
							  <label  class="required" for="shipChannel"><fmt:message key='order.shipChannel'/><fmt:message key='common.colon'/></label><br/>
							  <select id="shipChannel" name="shipChannel" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${shipChannelMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==order.shipChannel}">
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
							  <label  class="required" for="destination"><fmt:message key='order.destination'/><fmt:message key='common.colon'/></label><br/>
							  <select id="destination" name="destination" class="edit_input" >
				            	  <option value=""><fmt:message key="common.select"/></option>
					              <c:forEach items="${destinationMap}" var="entity">  
					              	   <c:choose>
							               <c:when test="${entity.key==order.destination}">
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
							  <label><fmt:message key='order.city'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="city" class="" value="${order.city}" name="city"/>
							</p>
							
							<p>
							  <label><fmt:message key='order.shipWeight'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shipWeight" class="" value="${order.shipWeight}" name="shipWeight"/>
							</p>
							
							<p>
							  <label><fmt:message key='order.shipPrice'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="shipPrice" class="" value="${order.shipPrice}" name="shipPrice"/>(<fmt:message key='common.rmb'/>)
							</p>
							
							<p>
							  <label><fmt:message key='order.shipTime_start'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="datepicker" name="shipTime_start" size="18" value="${order.shipTime_start}"> 
							</p>
							
							<p>
							  <label><fmt:message key='order.shipTime_end'/><fmt:message key='common.colon'/></label><br/>
							  <input type="text" id="datepicker1" name="shipTime_end" size="18" value="${order.shipTime_end}">
							  <c:choose>
				               <c:when test="${order.shipStatus==1}">
				                  <input type="checkbox" name="shipStatus" value ="1" checked="checked">
				               </c:when>	
				               <c:otherwise>
				                  <input type="checkbox" name="shipStatus" value ="1">
				               </c:otherwise>
				             </c:choose>
							</p>
							
							<c:if test="${order!=null}">
							 <div class="clear">&nbsp;</div>	 
							  <div style="text-align:right;">
								<input type="button" class="btn btn-green big" onclick="addOrderItem(${order.id})" value="<fmt:message key="button.add" />" />					
							  </div>                                                 
							  <table id="report" class="stylized full" style="">
									<thead>
										<tr>
											<th width="10%" class="ta-center"><fmt:message key='orderItem.orderItemId'/></th>
											<th width="25%" class="ta-center"><fmt:message key='orderItem.sku'/></th>
											<th width="10%" class="ta-center"><fmt:message key='orderItem.price'/></th>
										    <th width="10%" class="ta-center"><fmt:message key='orderItem.taobaoPrice'/></th>
											<th width="35%" class="ta-center"><fmt:message key='orderItem.taobaoOrderNumber'/></th>
											<th width="10%" class="ta-center"></th>
										</tr>
									</thead>
									<tbody>
									<c:forEach var="item" items="${orderItemList}">  
										 <tr>
										    <td class="ta-center">${item.id}</td> 
											<td class="ta-center"><a href="#" onclick="updateOrderItem(${item.id})">${item.sku}</a></td>
											<td class="ta-center">${item.price}</td>
										    <td class="ta-center">${item.taobao_price}</td>
											<td class="ta-center">${item.taobao_order_number}</td>
											<td class="ta-right"><input type="button" class="btn btn-green big" onclick="deleteOrderItem('${item.id}',${order.id})" value="<fmt:message key="button.delete" />" /></td>
										</tr>					
							     	</c:forEach> 
									</tbody>
								</table>
							</c:if>															
						    <p class="box">
						    <input type="submit" class="btn btn-green big" value="<fmt:message key='button.submit'/>"/>&nbsp;&nbsp;&nbsp;&nbsp;						    
						    <input type="button" class="btn btn-green big" onclick="searchOrder()" value="<fmt:message key='button.back'/>"/>
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
